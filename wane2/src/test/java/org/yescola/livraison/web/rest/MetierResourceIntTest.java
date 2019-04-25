package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Metier;
import org.yescola.livraison.repository.MetierRepository;
import org.yescola.livraison.repository.search.MetierSearchRepository;
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
 * Test class for the MetierResource REST controller.
 *
 * @see MetierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class MetierResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETATMETIER = "AAAAAAAAAA";
    private static final String UPDATED_ETATMETIER = "BBBBBBBBBB";

    @Autowired
    private MetierRepository metierRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.MetierSearchRepositoryMockConfiguration
     */
    @Autowired
    private MetierSearchRepository mockMetierSearchRepository;

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

    private MockMvc restMetierMockMvc;

    private Metier metier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MetierResource metierResource = new MetierResource(metierRepository, mockMetierSearchRepository);
        this.restMetierMockMvc = MockMvcBuilders.standaloneSetup(metierResource)
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
    public static Metier createEntity(EntityManager em) {
        Metier metier = new Metier()
            .libelle(DEFAULT_LIBELLE)
            .etatmetier(DEFAULT_ETATMETIER);
        return metier;
    }

    @Before
    public void initTest() {
        metier = createEntity(em);
    }

    @Test
    @Transactional
    public void createMetier() throws Exception {
        int databaseSizeBeforeCreate = metierRepository.findAll().size();

        // Create the Metier
        restMetierMockMvc.perform(post("/api/metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metier)))
            .andExpect(status().isCreated());

        // Validate the Metier in the database
        List<Metier> metierList = metierRepository.findAll();
        assertThat(metierList).hasSize(databaseSizeBeforeCreate + 1);
        Metier testMetier = metierList.get(metierList.size() - 1);
        assertThat(testMetier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMetier.getEtatmetier()).isEqualTo(DEFAULT_ETATMETIER);

        // Validate the Metier in Elasticsearch
        verify(mockMetierSearchRepository, times(1)).save(testMetier);
    }

    @Test
    @Transactional
    public void createMetierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metierRepository.findAll().size();

        // Create the Metier with an existing ID
        metier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetierMockMvc.perform(post("/api/metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metier)))
            .andExpect(status().isBadRequest());

        // Validate the Metier in the database
        List<Metier> metierList = metierRepository.findAll();
        assertThat(metierList).hasSize(databaseSizeBeforeCreate);

        // Validate the Metier in Elasticsearch
        verify(mockMetierSearchRepository, times(0)).save(metier);
    }

    @Test
    @Transactional
    public void getAllMetiers() throws Exception {
        // Initialize the database
        metierRepository.saveAndFlush(metier);

        // Get all the metierList
        restMetierMockMvc.perform(get("/api/metiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].etatmetier").value(hasItem(DEFAULT_ETATMETIER.toString())));
    }
    
    @Test
    @Transactional
    public void getMetier() throws Exception {
        // Initialize the database
        metierRepository.saveAndFlush(metier);

        // Get the metier
        restMetierMockMvc.perform(get("/api/metiers/{id}", metier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(metier.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.etatmetier").value(DEFAULT_ETATMETIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMetier() throws Exception {
        // Get the metier
        restMetierMockMvc.perform(get("/api/metiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMetier() throws Exception {
        // Initialize the database
        metierRepository.saveAndFlush(metier);

        int databaseSizeBeforeUpdate = metierRepository.findAll().size();

        // Update the metier
        Metier updatedMetier = metierRepository.findById(metier.getId()).get();
        // Disconnect from session so that the updates on updatedMetier are not directly saved in db
        em.detach(updatedMetier);
        updatedMetier
            .libelle(UPDATED_LIBELLE)
            .etatmetier(UPDATED_ETATMETIER);

        restMetierMockMvc.perform(put("/api/metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMetier)))
            .andExpect(status().isOk());

        // Validate the Metier in the database
        List<Metier> metierList = metierRepository.findAll();
        assertThat(metierList).hasSize(databaseSizeBeforeUpdate);
        Metier testMetier = metierList.get(metierList.size() - 1);
        assertThat(testMetier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMetier.getEtatmetier()).isEqualTo(UPDATED_ETATMETIER);

        // Validate the Metier in Elasticsearch
        verify(mockMetierSearchRepository, times(1)).save(testMetier);
    }

    @Test
    @Transactional
    public void updateNonExistingMetier() throws Exception {
        int databaseSizeBeforeUpdate = metierRepository.findAll().size();

        // Create the Metier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetierMockMvc.perform(put("/api/metiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metier)))
            .andExpect(status().isBadRequest());

        // Validate the Metier in the database
        List<Metier> metierList = metierRepository.findAll();
        assertThat(metierList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Metier in Elasticsearch
        verify(mockMetierSearchRepository, times(0)).save(metier);
    }

    @Test
    @Transactional
    public void deleteMetier() throws Exception {
        // Initialize the database
        metierRepository.saveAndFlush(metier);

        int databaseSizeBeforeDelete = metierRepository.findAll().size();

        // Delete the metier
        restMetierMockMvc.perform(delete("/api/metiers/{id}", metier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Metier> metierList = metierRepository.findAll();
        assertThat(metierList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Metier in Elasticsearch
        verify(mockMetierSearchRepository, times(1)).deleteById(metier.getId());
    }

    @Test
    @Transactional
    public void searchMetier() throws Exception {
        // Initialize the database
        metierRepository.saveAndFlush(metier);
        when(mockMetierSearchRepository.search(queryStringQuery("id:" + metier.getId())))
            .thenReturn(Collections.singletonList(metier));
        // Search the metier
        restMetierMockMvc.perform(get("/api/_search/metiers?query=id:" + metier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].etatmetier").value(hasItem(DEFAULT_ETATMETIER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Metier.class);
        Metier metier1 = new Metier();
        metier1.setId(1L);
        Metier metier2 = new Metier();
        metier2.setId(metier1.getId());
        assertThat(metier1).isEqualTo(metier2);
        metier2.setId(2L);
        assertThat(metier1).isNotEqualTo(metier2);
        metier1.setId(null);
        assertThat(metier1).isNotEqualTo(metier2);
    }
}
