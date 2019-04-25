package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.MoyenTransport;
import org.yescola.livraison.repository.MoyenTransportRepository;
import org.yescola.livraison.repository.search.MoyenTransportSearchRepository;
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
 * Test class for the MoyenTransportResource REST controller.
 *
 * @see MoyenTransportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class MoyenTransportResourceIntTest {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_LIST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LIST = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_VITESSE = "AAAAAAAAAA";
    private static final String UPDATED_VITESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CAPACITE = "AAAAAAAAAA";
    private static final String UPDATED_CAPACITE = "BBBBBBBBBB";

    @Autowired
    private MoyenTransportRepository moyenTransportRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.MoyenTransportSearchRepositoryMockConfiguration
     */
    @Autowired
    private MoyenTransportSearchRepository mockMoyenTransportSearchRepository;

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

    private MockMvc restMoyenTransportMockMvc;

    private MoyenTransport moyenTransport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoyenTransportResource moyenTransportResource = new MoyenTransportResource(moyenTransportRepository, mockMoyenTransportSearchRepository);
        this.restMoyenTransportMockMvc = MockMvcBuilders.standaloneSetup(moyenTransportResource)
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
    public static MoyenTransport createEntity(EntityManager em) {
        MoyenTransport moyenTransport = new MoyenTransport()
            .matricule(DEFAULT_MATRICULE)
            .dateList(DEFAULT_DATE_LIST)
            .etat(DEFAULT_ETAT)
            .vitesse(DEFAULT_VITESSE)
            .capacite(DEFAULT_CAPACITE);
        return moyenTransport;
    }

    @Before
    public void initTest() {
        moyenTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoyenTransport() throws Exception {
        int databaseSizeBeforeCreate = moyenTransportRepository.findAll().size();

        // Create the MoyenTransport
        restMoyenTransportMockMvc.perform(post("/api/moyen-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenTransport)))
            .andExpect(status().isCreated());

        // Validate the MoyenTransport in the database
        List<MoyenTransport> moyenTransportList = moyenTransportRepository.findAll();
        assertThat(moyenTransportList).hasSize(databaseSizeBeforeCreate + 1);
        MoyenTransport testMoyenTransport = moyenTransportList.get(moyenTransportList.size() - 1);
        assertThat(testMoyenTransport.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testMoyenTransport.getDateList()).isEqualTo(DEFAULT_DATE_LIST);
        assertThat(testMoyenTransport.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testMoyenTransport.getVitesse()).isEqualTo(DEFAULT_VITESSE);
        assertThat(testMoyenTransport.getCapacite()).isEqualTo(DEFAULT_CAPACITE);

        // Validate the MoyenTransport in Elasticsearch
        verify(mockMoyenTransportSearchRepository, times(1)).save(testMoyenTransport);
    }

    @Test
    @Transactional
    public void createMoyenTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moyenTransportRepository.findAll().size();

        // Create the MoyenTransport with an existing ID
        moyenTransport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoyenTransportMockMvc.perform(post("/api/moyen-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenTransport)))
            .andExpect(status().isBadRequest());

        // Validate the MoyenTransport in the database
        List<MoyenTransport> moyenTransportList = moyenTransportRepository.findAll();
        assertThat(moyenTransportList).hasSize(databaseSizeBeforeCreate);

        // Validate the MoyenTransport in Elasticsearch
        verify(mockMoyenTransportSearchRepository, times(0)).save(moyenTransport);
    }

    @Test
    @Transactional
    public void getAllMoyenTransports() throws Exception {
        // Initialize the database
        moyenTransportRepository.saveAndFlush(moyenTransport);

        // Get all the moyenTransportList
        restMoyenTransportMockMvc.perform(get("/api/moyen-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moyenTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())))
            .andExpect(jsonPath("$.[*].dateList").value(hasItem(DEFAULT_DATE_LIST.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].vitesse").value(hasItem(DEFAULT_VITESSE.toString())))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE.toString())));
    }
    
    @Test
    @Transactional
    public void getMoyenTransport() throws Exception {
        // Initialize the database
        moyenTransportRepository.saveAndFlush(moyenTransport);

        // Get the moyenTransport
        restMoyenTransportMockMvc.perform(get("/api/moyen-transports/{id}", moyenTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moyenTransport.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()))
            .andExpect(jsonPath("$.dateList").value(DEFAULT_DATE_LIST.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.vitesse").value(DEFAULT_VITESSE.toString()))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMoyenTransport() throws Exception {
        // Get the moyenTransport
        restMoyenTransportMockMvc.perform(get("/api/moyen-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoyenTransport() throws Exception {
        // Initialize the database
        moyenTransportRepository.saveAndFlush(moyenTransport);

        int databaseSizeBeforeUpdate = moyenTransportRepository.findAll().size();

        // Update the moyenTransport
        MoyenTransport updatedMoyenTransport = moyenTransportRepository.findById(moyenTransport.getId()).get();
        // Disconnect from session so that the updates on updatedMoyenTransport are not directly saved in db
        em.detach(updatedMoyenTransport);
        updatedMoyenTransport
            .matricule(UPDATED_MATRICULE)
            .dateList(UPDATED_DATE_LIST)
            .etat(UPDATED_ETAT)
            .vitesse(UPDATED_VITESSE)
            .capacite(UPDATED_CAPACITE);

        restMoyenTransportMockMvc.perform(put("/api/moyen-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoyenTransport)))
            .andExpect(status().isOk());

        // Validate the MoyenTransport in the database
        List<MoyenTransport> moyenTransportList = moyenTransportRepository.findAll();
        assertThat(moyenTransportList).hasSize(databaseSizeBeforeUpdate);
        MoyenTransport testMoyenTransport = moyenTransportList.get(moyenTransportList.size() - 1);
        assertThat(testMoyenTransport.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testMoyenTransport.getDateList()).isEqualTo(UPDATED_DATE_LIST);
        assertThat(testMoyenTransport.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testMoyenTransport.getVitesse()).isEqualTo(UPDATED_VITESSE);
        assertThat(testMoyenTransport.getCapacite()).isEqualTo(UPDATED_CAPACITE);

        // Validate the MoyenTransport in Elasticsearch
        verify(mockMoyenTransportSearchRepository, times(1)).save(testMoyenTransport);
    }

    @Test
    @Transactional
    public void updateNonExistingMoyenTransport() throws Exception {
        int databaseSizeBeforeUpdate = moyenTransportRepository.findAll().size();

        // Create the MoyenTransport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoyenTransportMockMvc.perform(put("/api/moyen-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenTransport)))
            .andExpect(status().isBadRequest());

        // Validate the MoyenTransport in the database
        List<MoyenTransport> moyenTransportList = moyenTransportRepository.findAll();
        assertThat(moyenTransportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MoyenTransport in Elasticsearch
        verify(mockMoyenTransportSearchRepository, times(0)).save(moyenTransport);
    }

    @Test
    @Transactional
    public void deleteMoyenTransport() throws Exception {
        // Initialize the database
        moyenTransportRepository.saveAndFlush(moyenTransport);

        int databaseSizeBeforeDelete = moyenTransportRepository.findAll().size();

        // Delete the moyenTransport
        restMoyenTransportMockMvc.perform(delete("/api/moyen-transports/{id}", moyenTransport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoyenTransport> moyenTransportList = moyenTransportRepository.findAll();
        assertThat(moyenTransportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MoyenTransport in Elasticsearch
        verify(mockMoyenTransportSearchRepository, times(1)).deleteById(moyenTransport.getId());
    }

    @Test
    @Transactional
    public void searchMoyenTransport() throws Exception {
        // Initialize the database
        moyenTransportRepository.saveAndFlush(moyenTransport);
        when(mockMoyenTransportSearchRepository.search(queryStringQuery("id:" + moyenTransport.getId())))
            .thenReturn(Collections.singletonList(moyenTransport));
        // Search the moyenTransport
        restMoyenTransportMockMvc.perform(get("/api/_search/moyen-transports?query=id:" + moyenTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moyenTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].dateList").value(hasItem(DEFAULT_DATE_LIST.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].vitesse").value(hasItem(DEFAULT_VITESSE)))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoyenTransport.class);
        MoyenTransport moyenTransport1 = new MoyenTransport();
        moyenTransport1.setId(1L);
        MoyenTransport moyenTransport2 = new MoyenTransport();
        moyenTransport2.setId(moyenTransport1.getId());
        assertThat(moyenTransport1).isEqualTo(moyenTransport2);
        moyenTransport2.setId(2L);
        assertThat(moyenTransport1).isNotEqualTo(moyenTransport2);
        moyenTransport1.setId(null);
        assertThat(moyenTransport1).isNotEqualTo(moyenTransport2);
    }
}
