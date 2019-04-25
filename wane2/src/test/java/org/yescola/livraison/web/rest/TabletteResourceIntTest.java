package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Tablette;
import org.yescola.livraison.repository.TabletteRepository;
import org.yescola.livraison.repository.search.TabletteSearchRepository;
import org.yescola.livraison.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static org.yescola.livraison.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TabletteResource REST controller.
 *
 * @see TabletteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class TabletteResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SERV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SERV = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_NUMEROPUCE = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROPUCE = "BBBBBBBBBB";

    @Autowired
    private TabletteRepository tabletteRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.TabletteSearchRepositoryMockConfiguration
     */
    @Autowired
    private TabletteSearchRepository mockTabletteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTabletteMockMvc;

    private Tablette tablette;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TabletteResource tabletteResource = new TabletteResource(tabletteRepository, mockTabletteSearchRepository);
        this.restTabletteMockMvc = MockMvcBuilders.standaloneSetup(tabletteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tablette createEntity(EntityManager em) {
        Tablette tablette = new Tablette()
            .numero(DEFAULT_NUMERO)
            .dateServ(DEFAULT_DATE_SERV)
            .etat(DEFAULT_ETAT)
            .numeropuce(DEFAULT_NUMEROPUCE);
        return tablette;
    }

    @Before
    public void initTest() {
        tablette = createEntity(em);
    }

    @Test
    @Transactional
    public void createTablette() throws Exception {
        int databaseSizeBeforeCreate = tabletteRepository.findAll().size();

        // Create the Tablette
        restTabletteMockMvc.perform(post("/api/tablettes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablette)))
            .andExpect(status().isCreated());

        // Validate the Tablette in the database
        List<Tablette> tabletteList = tabletteRepository.findAll();
        assertThat(tabletteList).hasSize(databaseSizeBeforeCreate + 1);
        Tablette testTablette = tabletteList.get(tabletteList.size() - 1);
        assertThat(testTablette.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTablette.getDateServ()).isEqualTo(DEFAULT_DATE_SERV);
        assertThat(testTablette.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testTablette.getNumeropuce()).isEqualTo(DEFAULT_NUMEROPUCE);

        // Validate the Tablette in Elasticsearch
        verify(mockTabletteSearchRepository, times(1)).save(testTablette);
    }

    @Test
    @Transactional
    public void createTabletteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tabletteRepository.findAll().size();

        // Create the Tablette with an existing ID
        tablette.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabletteMockMvc.perform(post("/api/tablettes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablette)))
            .andExpect(status().isBadRequest());

        // Validate the Tablette in the database
        List<Tablette> tabletteList = tabletteRepository.findAll();
        assertThat(tabletteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tablette in Elasticsearch
        verify(mockTabletteSearchRepository, times(0)).save(tablette);
    }

    @Test
    @Transactional
    public void getAllTablettes() throws Exception {
        // Initialize the database
        tabletteRepository.saveAndFlush(tablette);

        // Get all the tabletteList
        restTabletteMockMvc.perform(get("/api/tablettes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tablette.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].dateServ").value(hasItem(DEFAULT_DATE_SERV.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].numeropuce").value(hasItem(DEFAULT_NUMEROPUCE.toString())));
    }
    
    @Test
    @Transactional
    public void getTablette() throws Exception {
        // Initialize the database
        tabletteRepository.saveAndFlush(tablette);

        // Get the tablette
        restTabletteMockMvc.perform(get("/api/tablettes/{id}", tablette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tablette.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.dateServ").value(DEFAULT_DATE_SERV.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.numeropuce").value(DEFAULT_NUMEROPUCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTablette() throws Exception {
        // Get the tablette
        restTabletteMockMvc.perform(get("/api/tablettes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTablette() throws Exception {
        // Initialize the database
        tabletteRepository.saveAndFlush(tablette);

        int databaseSizeBeforeUpdate = tabletteRepository.findAll().size();

        // Update the tablette
        Tablette updatedTablette = tabletteRepository.findById(tablette.getId()).get();
        // Disconnect from session so that the updates on updatedTablette are not directly saved in db
        em.detach(updatedTablette);
        updatedTablette
            .numero(UPDATED_NUMERO)
            .dateServ(UPDATED_DATE_SERV)
            .etat(UPDATED_ETAT)
            .numeropuce(UPDATED_NUMEROPUCE);

        restTabletteMockMvc.perform(put("/api/tablettes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTablette)))
            .andExpect(status().isOk());

        // Validate the Tablette in the database
        List<Tablette> tabletteList = tabletteRepository.findAll();
        assertThat(tabletteList).hasSize(databaseSizeBeforeUpdate);
        Tablette testTablette = tabletteList.get(tabletteList.size() - 1);
        assertThat(testTablette.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTablette.getDateServ()).isEqualTo(UPDATED_DATE_SERV);
        assertThat(testTablette.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testTablette.getNumeropuce()).isEqualTo(UPDATED_NUMEROPUCE);

        // Validate the Tablette in Elasticsearch
        verify(mockTabletteSearchRepository, times(1)).save(testTablette);
    }

    @Test
    @Transactional
    public void updateNonExistingTablette() throws Exception {
        int databaseSizeBeforeUpdate = tabletteRepository.findAll().size();

        // Create the Tablette

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabletteMockMvc.perform(put("/api/tablettes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablette)))
            .andExpect(status().isBadRequest());

        // Validate the Tablette in the database
        List<Tablette> tabletteList = tabletteRepository.findAll();
        assertThat(tabletteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tablette in Elasticsearch
        verify(mockTabletteSearchRepository, times(0)).save(tablette);
    }

    @Test
    @Transactional
    public void deleteTablette() throws Exception {
        // Initialize the database
        tabletteRepository.saveAndFlush(tablette);

        int databaseSizeBeforeDelete = tabletteRepository.findAll().size();

        // Delete the tablette
        restTabletteMockMvc.perform(delete("/api/tablettes/{id}", tablette.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tablette> tabletteList = tabletteRepository.findAll();
        assertThat(tabletteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tablette in Elasticsearch
        verify(mockTabletteSearchRepository, times(1)).deleteById(tablette.getId());
    }

    @Test
    @Transactional
    public void searchTablette() throws Exception {
        // Initialize the database
        tabletteRepository.saveAndFlush(tablette);
        when(mockTabletteSearchRepository.search(queryStringQuery("id:" + tablette.getId())))
            .thenReturn(Collections.singletonList(tablette));
        // Search the tablette
        restTabletteMockMvc.perform(get("/api/_search/tablettes?query=id:" + tablette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tablette.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].dateServ").value(hasItem(DEFAULT_DATE_SERV.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].numeropuce").value(hasItem(DEFAULT_NUMEROPUCE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tablette.class);
        Tablette tablette1 = new Tablette();
        tablette1.setId(1L);
        Tablette tablette2 = new Tablette();
        tablette2.setId(tablette1.getId());
        assertThat(tablette1).isEqualTo(tablette2);
        tablette2.setId(2L);
        assertThat(tablette1).isNotEqualTo(tablette2);
        tablette1.setId(null);
        assertThat(tablette1).isNotEqualTo(tablette2);
    }
}
