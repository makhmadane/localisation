package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Depot;
import org.yescola.livraison.repository.DepotRepository;
import org.yescola.livraison.repository.search.DepotSearchRepository;
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
 * Test class for the DepotResource REST controller.
 *
 * @see DepotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class DepotResourceIntTest {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTITUDE = "AAAAAAAAAA";
    private static final String UPDATED_ALTITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private DepotRepository depotRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.DepotSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepotSearchRepository mockDepotSearchRepository;

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

    private MockMvc restDepotMockMvc;

    private Depot depot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepotResource depotResource = new DepotResource(depotRepository, mockDepotSearchRepository);
        this.restDepotMockMvc = MockMvcBuilders.standaloneSetup(depotResource)
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
    public static Depot createEntity(EntityManager em) {
        Depot depot = new Depot()
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .longitude(DEFAULT_LONGITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .etat(DEFAULT_ETAT);
        return depot;
    }

    @Before
    public void initTest() {
        depot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepot() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isCreated());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate + 1);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDepot.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDepot.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDepot.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testDepot.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testDepot.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Depot in Elasticsearch
        verify(mockDepotSearchRepository, times(1)).save(testDepot);
    }

    @Test
    @Transactional
    public void createDepotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot with an existing ID
        depot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate);

        // Validate the Depot in Elasticsearch
        verify(mockDepotSearchRepository, times(0)).save(depot);
    }

    @Test
    @Transactional
    public void getAllDepots() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depot.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepot() throws Exception {
        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Update the depot
        Depot updatedDepot = depotRepository.findById(depot.getId()).get();
        // Disconnect from session so that the updates on updatedDepot are not directly saved in db
        em.detach(updatedDepot);
        updatedDepot
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .longitude(UPDATED_LONGITUDE)
            .altitude(UPDATED_ALTITUDE)
            .etat(UPDATED_ETAT);

        restDepotMockMvc.perform(put("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepot)))
            .andExpect(status().isOk());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDepot.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDepot.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDepot.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testDepot.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testDepot.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Depot in Elasticsearch
        verify(mockDepotSearchRepository, times(1)).save(testDepot);
    }

    @Test
    @Transactional
    public void updateNonExistingDepot() throws Exception {
        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Create the Depot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc.perform(put("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Depot in Elasticsearch
        verify(mockDepotSearchRepository, times(0)).save(depot);
    }

    @Test
    @Transactional
    public void deleteDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        int databaseSizeBeforeDelete = depotRepository.findAll().size();

        // Delete the depot
        restDepotMockMvc.perform(delete("/api/depots/{id}", depot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Depot in Elasticsearch
        verify(mockDepotSearchRepository, times(1)).deleteById(depot.getId());
    }

    @Test
    @Transactional
    public void searchDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);
        when(mockDepotSearchRepository.search(queryStringQuery("id:" + depot.getId())))
            .thenReturn(Collections.singletonList(depot));
        // Search the depot
        restDepotMockMvc.perform(get("/api/_search/depots?query=id:" + depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Depot.class);
        Depot depot1 = new Depot();
        depot1.setId(1L);
        Depot depot2 = new Depot();
        depot2.setId(depot1.getId());
        assertThat(depot1).isEqualTo(depot2);
        depot2.setId(2L);
        assertThat(depot1).isNotEqualTo(depot2);
        depot1.setId(null);
        assertThat(depot1).isNotEqualTo(depot2);
    }
}
