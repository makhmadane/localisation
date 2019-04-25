package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Qualite;
import org.yescola.livraison.repository.QualiteRepository;
import org.yescola.livraison.repository.search.QualiteSearchRepository;
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
 * Test class for the QualiteResource REST controller.
 *
 * @see QualiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class QualiteResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private QualiteRepository qualiteRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.QualiteSearchRepositoryMockConfiguration
     */
    @Autowired
    private QualiteSearchRepository mockQualiteSearchRepository;

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

    private MockMvc restQualiteMockMvc;

    private Qualite qualite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QualiteResource qualiteResource = new QualiteResource(qualiteRepository, mockQualiteSearchRepository);
        this.restQualiteMockMvc = MockMvcBuilders.standaloneSetup(qualiteResource)
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
    public static Qualite createEntity(EntityManager em) {
        Qualite qualite = new Qualite()
            .libelle(DEFAULT_LIBELLE)
            .etat(DEFAULT_ETAT);
        return qualite;
    }

    @Before
    public void initTest() {
        qualite = createEntity(em);
    }

    @Test
    @Transactional
    public void createQualite() throws Exception {
        int databaseSizeBeforeCreate = qualiteRepository.findAll().size();

        // Create the Qualite
        restQualiteMockMvc.perform(post("/api/qualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualite)))
            .andExpect(status().isCreated());

        // Validate the Qualite in the database
        List<Qualite> qualiteList = qualiteRepository.findAll();
        assertThat(qualiteList).hasSize(databaseSizeBeforeCreate + 1);
        Qualite testQualite = qualiteList.get(qualiteList.size() - 1);
        assertThat(testQualite.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testQualite.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Qualite in Elasticsearch
        verify(mockQualiteSearchRepository, times(1)).save(testQualite);
    }

    @Test
    @Transactional
    public void createQualiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qualiteRepository.findAll().size();

        // Create the Qualite with an existing ID
        qualite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualiteMockMvc.perform(post("/api/qualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualite)))
            .andExpect(status().isBadRequest());

        // Validate the Qualite in the database
        List<Qualite> qualiteList = qualiteRepository.findAll();
        assertThat(qualiteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Qualite in Elasticsearch
        verify(mockQualiteSearchRepository, times(0)).save(qualite);
    }

    @Test
    @Transactional
    public void getAllQualites() throws Exception {
        // Initialize the database
        qualiteRepository.saveAndFlush(qualite);

        // Get all the qualiteList
        restQualiteMockMvc.perform(get("/api/qualites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getQualite() throws Exception {
        // Initialize the database
        qualiteRepository.saveAndFlush(qualite);

        // Get the qualite
        restQualiteMockMvc.perform(get("/api/qualites/{id}", qualite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(qualite.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQualite() throws Exception {
        // Get the qualite
        restQualiteMockMvc.perform(get("/api/qualites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQualite() throws Exception {
        // Initialize the database
        qualiteRepository.saveAndFlush(qualite);

        int databaseSizeBeforeUpdate = qualiteRepository.findAll().size();

        // Update the qualite
        Qualite updatedQualite = qualiteRepository.findById(qualite.getId()).get();
        // Disconnect from session so that the updates on updatedQualite are not directly saved in db
        em.detach(updatedQualite);
        updatedQualite
            .libelle(UPDATED_LIBELLE)
            .etat(UPDATED_ETAT);

        restQualiteMockMvc.perform(put("/api/qualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQualite)))
            .andExpect(status().isOk());

        // Validate the Qualite in the database
        List<Qualite> qualiteList = qualiteRepository.findAll();
        assertThat(qualiteList).hasSize(databaseSizeBeforeUpdate);
        Qualite testQualite = qualiteList.get(qualiteList.size() - 1);
        assertThat(testQualite.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testQualite.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Qualite in Elasticsearch
        verify(mockQualiteSearchRepository, times(1)).save(testQualite);
    }

    @Test
    @Transactional
    public void updateNonExistingQualite() throws Exception {
        int databaseSizeBeforeUpdate = qualiteRepository.findAll().size();

        // Create the Qualite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualiteMockMvc.perform(put("/api/qualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualite)))
            .andExpect(status().isBadRequest());

        // Validate the Qualite in the database
        List<Qualite> qualiteList = qualiteRepository.findAll();
        assertThat(qualiteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Qualite in Elasticsearch
        verify(mockQualiteSearchRepository, times(0)).save(qualite);
    }

    @Test
    @Transactional
    public void deleteQualite() throws Exception {
        // Initialize the database
        qualiteRepository.saveAndFlush(qualite);

        int databaseSizeBeforeDelete = qualiteRepository.findAll().size();

        // Delete the qualite
        restQualiteMockMvc.perform(delete("/api/qualites/{id}", qualite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Qualite> qualiteList = qualiteRepository.findAll();
        assertThat(qualiteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Qualite in Elasticsearch
        verify(mockQualiteSearchRepository, times(1)).deleteById(qualite.getId());
    }

    @Test
    @Transactional
    public void searchQualite() throws Exception {
        // Initialize the database
        qualiteRepository.saveAndFlush(qualite);
        when(mockQualiteSearchRepository.search(queryStringQuery("id:" + qualite.getId())))
            .thenReturn(Collections.singletonList(qualite));
        // Search the qualite
        restQualiteMockMvc.perform(get("/api/_search/qualites?query=id:" + qualite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualite.class);
        Qualite qualite1 = new Qualite();
        qualite1.setId(1L);
        Qualite qualite2 = new Qualite();
        qualite2.setId(qualite1.getId());
        assertThat(qualite1).isEqualTo(qualite2);
        qualite2.setId(2L);
        assertThat(qualite1).isNotEqualTo(qualite2);
        qualite1.setId(null);
        assertThat(qualite1).isNotEqualTo(qualite2);
    }
}
