package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Commune;
import org.yescola.livraison.repository.CommuneRepository;
import org.yescola.livraison.repository.search.CommuneSearchRepository;
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
 * Test class for the CommuneResource REST controller.
 *
 * @see CommuneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class CommuneResourceIntTest {

    private static final String DEFAULT_NOM_COM = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COM = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUTDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUTDE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTITUDE = "AAAAAAAAAA";
    private static final String UPDATED_ALTITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private CommuneRepository communeRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.CommuneSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommuneSearchRepository mockCommuneSearchRepository;

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

    private MockMvc restCommuneMockMvc;

    private Commune commune;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommuneResource communeResource = new CommuneResource(communeRepository, mockCommuneSearchRepository);
        this.restCommuneMockMvc = MockMvcBuilders.standaloneSetup(communeResource)
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
    public static Commune createEntity(EntityManager em) {
        Commune commune = new Commune()
            .nomCom(DEFAULT_NOM_COM)
            .longitutde(DEFAULT_LONGITUTDE)
            .altitude(DEFAULT_ALTITUDE)
            .etat(DEFAULT_ETAT);
        return commune;
    }

    @Before
    public void initTest() {
        commune = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommune() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // Create the Commune
        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isCreated());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate + 1);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getNomCom()).isEqualTo(DEFAULT_NOM_COM);
        assertThat(testCommune.getLongitutde()).isEqualTo(DEFAULT_LONGITUTDE);
        assertThat(testCommune.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testCommune.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Commune in Elasticsearch
        verify(mockCommuneSearchRepository, times(1)).save(testCommune);
    }

    @Test
    @Transactional
    public void createCommuneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // Create the Commune with an existing ID
        commune.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Commune in Elasticsearch
        verify(mockCommuneSearchRepository, times(0)).save(commune);
    }

    @Test
    @Transactional
    public void getAllCommunes() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList
        restCommuneMockMvc.perform(get("/api/communes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCom").value(hasItem(DEFAULT_NOM_COM.toString())))
            .andExpect(jsonPath("$.[*].longitutde").value(hasItem(DEFAULT_LONGITUTDE.toString())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get the commune
        restCommuneMockMvc.perform(get("/api/communes/{id}", commune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commune.getId().intValue()))
            .andExpect(jsonPath("$.nomCom").value(DEFAULT_NOM_COM.toString()))
            .andExpect(jsonPath("$.longitutde").value(DEFAULT_LONGITUTDE.toString()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommune() throws Exception {
        // Get the commune
        restCommuneMockMvc.perform(get("/api/communes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune
        Commune updatedCommune = communeRepository.findById(commune.getId()).get();
        // Disconnect from session so that the updates on updatedCommune are not directly saved in db
        em.detach(updatedCommune);
        updatedCommune
            .nomCom(UPDATED_NOM_COM)
            .longitutde(UPDATED_LONGITUTDE)
            .altitude(UPDATED_ALTITUDE)
            .etat(UPDATED_ETAT);

        restCommuneMockMvc.perform(put("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommune)))
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getNomCom()).isEqualTo(UPDATED_NOM_COM);
        assertThat(testCommune.getLongitutde()).isEqualTo(UPDATED_LONGITUTDE);
        assertThat(testCommune.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testCommune.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Commune in Elasticsearch
        verify(mockCommuneSearchRepository, times(1)).save(testCommune);
    }

    @Test
    @Transactional
    public void updateNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Create the Commune

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommuneMockMvc.perform(put("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Commune in Elasticsearch
        verify(mockCommuneSearchRepository, times(0)).save(commune);
    }

    @Test
    @Transactional
    public void deleteCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeDelete = communeRepository.findAll().size();

        // Delete the commune
        restCommuneMockMvc.perform(delete("/api/communes/{id}", commune.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Commune in Elasticsearch
        verify(mockCommuneSearchRepository, times(1)).deleteById(commune.getId());
    }

    @Test
    @Transactional
    public void searchCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);
        when(mockCommuneSearchRepository.search(queryStringQuery("id:" + commune.getId())))
            .thenReturn(Collections.singletonList(commune));
        // Search the commune
        restCommuneMockMvc.perform(get("/api/_search/communes?query=id:" + commune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCom").value(hasItem(DEFAULT_NOM_COM)))
            .andExpect(jsonPath("$.[*].longitutde").value(hasItem(DEFAULT_LONGITUTDE)))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commune.class);
        Commune commune1 = new Commune();
        commune1.setId(1L);
        Commune commune2 = new Commune();
        commune2.setId(commune1.getId());
        assertThat(commune1).isEqualTo(commune2);
        commune2.setId(2L);
        assertThat(commune1).isNotEqualTo(commune2);
        commune1.setId(null);
        assertThat(commune1).isNotEqualTo(commune2);
    }
}
