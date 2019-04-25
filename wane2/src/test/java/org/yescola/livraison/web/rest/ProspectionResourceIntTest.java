package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Prospection;
import org.yescola.livraison.repository.ProspectionRepository;
import org.yescola.livraison.repository.search.ProspectionSearchRepository;
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
 * Test class for the ProspectionResource REST controller.
 *
 * @see ProspectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class ProspectionResourceIntTest {

    private static final LocalDate DEFAULT_DATEDEPART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEPART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATECREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATECREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEUREDEPART = "AAAAAAAAAA";
    private static final String UPDATED_HEUREDEPART = "BBBBBBBBBB";

    private static final String DEFAULT_HEUREARRIVE = "AAAAAAAAAA";
    private static final String UPDATED_HEUREARRIVE = "BBBBBBBBBB";

    private static final String DEFAULT_JOURNEE = "AAAAAAAAAA";
    private static final String UPDATED_JOURNEE = "BBBBBBBBBB";

    private static final String DEFAULT_NBREATTEINT = "AAAAAAAAAA";
    private static final String UPDATED_NBREATTEINT = "BBBBBBBBBB";

    private static final String DEFAULT_NBRPREVUE = "AAAAAAAAAA";
    private static final String UPDATED_NBRPREVUE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private ProspectionRepository prospectionRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.ProspectionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProspectionSearchRepository mockProspectionSearchRepository;

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

    private MockMvc restProspectionMockMvc;

    private Prospection prospection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProspectionResource prospectionResource = new ProspectionResource(prospectionRepository, mockProspectionSearchRepository);
        this.restProspectionMockMvc = MockMvcBuilders.standaloneSetup(prospectionResource)
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
    public static Prospection createEntity(EntityManager em) {
        Prospection prospection = new Prospection()
            .datedepart(DEFAULT_DATEDEPART)
            .datecreation(DEFAULT_DATECREATION)
            .heuredepart(DEFAULT_HEUREDEPART)
            .heurearrive(DEFAULT_HEUREARRIVE)
            .journee(DEFAULT_JOURNEE)
            .nbreatteint(DEFAULT_NBREATTEINT)
            .nbrprevue(DEFAULT_NBRPREVUE)
            .etat(DEFAULT_ETAT);
        return prospection;
    }

    @Before
    public void initTest() {
        prospection = createEntity(em);
    }

    @Test
    @Transactional
    public void createProspection() throws Exception {
        int databaseSizeBeforeCreate = prospectionRepository.findAll().size();

        // Create the Prospection
        restProspectionMockMvc.perform(post("/api/prospections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospection)))
            .andExpect(status().isCreated());

        // Validate the Prospection in the database
        List<Prospection> prospectionList = prospectionRepository.findAll();
        assertThat(prospectionList).hasSize(databaseSizeBeforeCreate + 1);
        Prospection testProspection = prospectionList.get(prospectionList.size() - 1);
        assertThat(testProspection.getDatedepart()).isEqualTo(DEFAULT_DATEDEPART);
        assertThat(testProspection.getDatecreation()).isEqualTo(DEFAULT_DATECREATION);
        assertThat(testProspection.getHeuredepart()).isEqualTo(DEFAULT_HEUREDEPART);
        assertThat(testProspection.getHeurearrive()).isEqualTo(DEFAULT_HEUREARRIVE);
        assertThat(testProspection.getJournee()).isEqualTo(DEFAULT_JOURNEE);
        assertThat(testProspection.getNbreatteint()).isEqualTo(DEFAULT_NBREATTEINT);
        assertThat(testProspection.getNbrprevue()).isEqualTo(DEFAULT_NBRPREVUE);
        assertThat(testProspection.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Prospection in Elasticsearch
        verify(mockProspectionSearchRepository, times(1)).save(testProspection);
    }

    @Test
    @Transactional
    public void createProspectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prospectionRepository.findAll().size();

        // Create the Prospection with an existing ID
        prospection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProspectionMockMvc.perform(post("/api/prospections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospection)))
            .andExpect(status().isBadRequest());

        // Validate the Prospection in the database
        List<Prospection> prospectionList = prospectionRepository.findAll();
        assertThat(prospectionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Prospection in Elasticsearch
        verify(mockProspectionSearchRepository, times(0)).save(prospection);
    }

    @Test
    @Transactional
    public void getAllProspections() throws Exception {
        // Initialize the database
        prospectionRepository.saveAndFlush(prospection);

        // Get all the prospectionList
        restProspectionMockMvc.perform(get("/api/prospections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospection.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedepart").value(hasItem(DEFAULT_DATEDEPART.toString())))
            .andExpect(jsonPath("$.[*].datecreation").value(hasItem(DEFAULT_DATECREATION.toString())))
            .andExpect(jsonPath("$.[*].heuredepart").value(hasItem(DEFAULT_HEUREDEPART.toString())))
            .andExpect(jsonPath("$.[*].heurearrive").value(hasItem(DEFAULT_HEUREARRIVE.toString())))
            .andExpect(jsonPath("$.[*].journee").value(hasItem(DEFAULT_JOURNEE.toString())))
            .andExpect(jsonPath("$.[*].nbreatteint").value(hasItem(DEFAULT_NBREATTEINT.toString())))
            .andExpect(jsonPath("$.[*].nbrprevue").value(hasItem(DEFAULT_NBRPREVUE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getProspection() throws Exception {
        // Initialize the database
        prospectionRepository.saveAndFlush(prospection);

        // Get the prospection
        restProspectionMockMvc.perform(get("/api/prospections/{id}", prospection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prospection.getId().intValue()))
            .andExpect(jsonPath("$.datedepart").value(DEFAULT_DATEDEPART.toString()))
            .andExpect(jsonPath("$.datecreation").value(DEFAULT_DATECREATION.toString()))
            .andExpect(jsonPath("$.heuredepart").value(DEFAULT_HEUREDEPART.toString()))
            .andExpect(jsonPath("$.heurearrive").value(DEFAULT_HEUREARRIVE.toString()))
            .andExpect(jsonPath("$.journee").value(DEFAULT_JOURNEE.toString()))
            .andExpect(jsonPath("$.nbreatteint").value(DEFAULT_NBREATTEINT.toString()))
            .andExpect(jsonPath("$.nbrprevue").value(DEFAULT_NBRPREVUE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProspection() throws Exception {
        // Get the prospection
        restProspectionMockMvc.perform(get("/api/prospections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProspection() throws Exception {
        // Initialize the database
        prospectionRepository.saveAndFlush(prospection);

        int databaseSizeBeforeUpdate = prospectionRepository.findAll().size();

        // Update the prospection
        Prospection updatedProspection = prospectionRepository.findById(prospection.getId()).get();
        // Disconnect from session so that the updates on updatedProspection are not directly saved in db
        em.detach(updatedProspection);
        updatedProspection
            .datedepart(UPDATED_DATEDEPART)
            .datecreation(UPDATED_DATECREATION)
            .heuredepart(UPDATED_HEUREDEPART)
            .heurearrive(UPDATED_HEUREARRIVE)
            .journee(UPDATED_JOURNEE)
            .nbreatteint(UPDATED_NBREATTEINT)
            .nbrprevue(UPDATED_NBRPREVUE)
            .etat(UPDATED_ETAT);

        restProspectionMockMvc.perform(put("/api/prospections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProspection)))
            .andExpect(status().isOk());

        // Validate the Prospection in the database
        List<Prospection> prospectionList = prospectionRepository.findAll();
        assertThat(prospectionList).hasSize(databaseSizeBeforeUpdate);
        Prospection testProspection = prospectionList.get(prospectionList.size() - 1);
        assertThat(testProspection.getDatedepart()).isEqualTo(UPDATED_DATEDEPART);
        assertThat(testProspection.getDatecreation()).isEqualTo(UPDATED_DATECREATION);
        assertThat(testProspection.getHeuredepart()).isEqualTo(UPDATED_HEUREDEPART);
        assertThat(testProspection.getHeurearrive()).isEqualTo(UPDATED_HEUREARRIVE);
        assertThat(testProspection.getJournee()).isEqualTo(UPDATED_JOURNEE);
        assertThat(testProspection.getNbreatteint()).isEqualTo(UPDATED_NBREATTEINT);
        assertThat(testProspection.getNbrprevue()).isEqualTo(UPDATED_NBRPREVUE);
        assertThat(testProspection.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Prospection in Elasticsearch
        verify(mockProspectionSearchRepository, times(1)).save(testProspection);
    }

    @Test
    @Transactional
    public void updateNonExistingProspection() throws Exception {
        int databaseSizeBeforeUpdate = prospectionRepository.findAll().size();

        // Create the Prospection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectionMockMvc.perform(put("/api/prospections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prospection)))
            .andExpect(status().isBadRequest());

        // Validate the Prospection in the database
        List<Prospection> prospectionList = prospectionRepository.findAll();
        assertThat(prospectionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Prospection in Elasticsearch
        verify(mockProspectionSearchRepository, times(0)).save(prospection);
    }

    @Test
    @Transactional
    public void deleteProspection() throws Exception {
        // Initialize the database
        prospectionRepository.saveAndFlush(prospection);

        int databaseSizeBeforeDelete = prospectionRepository.findAll().size();

        // Delete the prospection
        restProspectionMockMvc.perform(delete("/api/prospections/{id}", prospection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prospection> prospectionList = prospectionRepository.findAll();
        assertThat(prospectionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Prospection in Elasticsearch
        verify(mockProspectionSearchRepository, times(1)).deleteById(prospection.getId());
    }

    @Test
    @Transactional
    public void searchProspection() throws Exception {
        // Initialize the database
        prospectionRepository.saveAndFlush(prospection);
        when(mockProspectionSearchRepository.search(queryStringQuery("id:" + prospection.getId())))
            .thenReturn(Collections.singletonList(prospection));
        // Search the prospection
        restProspectionMockMvc.perform(get("/api/_search/prospections?query=id:" + prospection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospection.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedepart").value(hasItem(DEFAULT_DATEDEPART.toString())))
            .andExpect(jsonPath("$.[*].datecreation").value(hasItem(DEFAULT_DATECREATION.toString())))
            .andExpect(jsonPath("$.[*].heuredepart").value(hasItem(DEFAULT_HEUREDEPART)))
            .andExpect(jsonPath("$.[*].heurearrive").value(hasItem(DEFAULT_HEUREARRIVE)))
            .andExpect(jsonPath("$.[*].journee").value(hasItem(DEFAULT_JOURNEE)))
            .andExpect(jsonPath("$.[*].nbreatteint").value(hasItem(DEFAULT_NBREATTEINT)))
            .andExpect(jsonPath("$.[*].nbrprevue").value(hasItem(DEFAULT_NBRPREVUE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prospection.class);
        Prospection prospection1 = new Prospection();
        prospection1.setId(1L);
        Prospection prospection2 = new Prospection();
        prospection2.setId(prospection1.getId());
        assertThat(prospection1).isEqualTo(prospection2);
        prospection2.setId(2L);
        assertThat(prospection1).isNotEqualTo(prospection2);
        prospection1.setId(null);
        assertThat(prospection1).isNotEqualTo(prospection2);
    }
}
