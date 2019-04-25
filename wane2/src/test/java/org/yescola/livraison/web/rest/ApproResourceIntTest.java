package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Appro;
import org.yescola.livraison.repository.ApproRepository;
import org.yescola.livraison.repository.search.ApproSearchRepository;
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
 * Test class for the ApproResource REST controller.
 *
 * @see ApproResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class ApproResourceIntTest {

    private static final String DEFAULT_QTE_LIV = "AAAAAAAAAA";
    private static final String UPDATED_QTE_LIV = "BBBBBBBBBB";

    @Autowired
    private ApproRepository approRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.ApproSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApproSearchRepository mockApproSearchRepository;

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

    private MockMvc restApproMockMvc;

    private Appro appro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApproResource approResource = new ApproResource(approRepository, mockApproSearchRepository);
        this.restApproMockMvc = MockMvcBuilders.standaloneSetup(approResource)
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
    public static Appro createEntity(EntityManager em) {
        Appro appro = new Appro()
            .qteLiv(DEFAULT_QTE_LIV);
        return appro;
    }

    @Before
    public void initTest() {
        appro = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppro() throws Exception {
        int databaseSizeBeforeCreate = approRepository.findAll().size();

        // Create the Appro
        restApproMockMvc.perform(post("/api/appros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appro)))
            .andExpect(status().isCreated());

        // Validate the Appro in the database
        List<Appro> approList = approRepository.findAll();
        assertThat(approList).hasSize(databaseSizeBeforeCreate + 1);
        Appro testAppro = approList.get(approList.size() - 1);
        assertThat(testAppro.getQteLiv()).isEqualTo(DEFAULT_QTE_LIV);

        // Validate the Appro in Elasticsearch
        verify(mockApproSearchRepository, times(1)).save(testAppro);
    }

    @Test
    @Transactional
    public void createApproWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = approRepository.findAll().size();

        // Create the Appro with an existing ID
        appro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproMockMvc.perform(post("/api/appros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appro)))
            .andExpect(status().isBadRequest());

        // Validate the Appro in the database
        List<Appro> approList = approRepository.findAll();
        assertThat(approList).hasSize(databaseSizeBeforeCreate);

        // Validate the Appro in Elasticsearch
        verify(mockApproSearchRepository, times(0)).save(appro);
    }

    @Test
    @Transactional
    public void getAllAppros() throws Exception {
        // Initialize the database
        approRepository.saveAndFlush(appro);

        // Get all the approList
        restApproMockMvc.perform(get("/api/appros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appro.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteLiv").value(hasItem(DEFAULT_QTE_LIV.toString())));
    }
    
    @Test
    @Transactional
    public void getAppro() throws Exception {
        // Initialize the database
        approRepository.saveAndFlush(appro);

        // Get the appro
        restApproMockMvc.perform(get("/api/appros/{id}", appro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appro.getId().intValue()))
            .andExpect(jsonPath("$.qteLiv").value(DEFAULT_QTE_LIV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppro() throws Exception {
        // Get the appro
        restApproMockMvc.perform(get("/api/appros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppro() throws Exception {
        // Initialize the database
        approRepository.saveAndFlush(appro);

        int databaseSizeBeforeUpdate = approRepository.findAll().size();

        // Update the appro
        Appro updatedAppro = approRepository.findById(appro.getId()).get();
        // Disconnect from session so that the updates on updatedAppro are not directly saved in db
        em.detach(updatedAppro);
        updatedAppro
            .qteLiv(UPDATED_QTE_LIV);

        restApproMockMvc.perform(put("/api/appros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppro)))
            .andExpect(status().isOk());

        // Validate the Appro in the database
        List<Appro> approList = approRepository.findAll();
        assertThat(approList).hasSize(databaseSizeBeforeUpdate);
        Appro testAppro = approList.get(approList.size() - 1);
        assertThat(testAppro.getQteLiv()).isEqualTo(UPDATED_QTE_LIV);

        // Validate the Appro in Elasticsearch
        verify(mockApproSearchRepository, times(1)).save(testAppro);
    }

    @Test
    @Transactional
    public void updateNonExistingAppro() throws Exception {
        int databaseSizeBeforeUpdate = approRepository.findAll().size();

        // Create the Appro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproMockMvc.perform(put("/api/appros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appro)))
            .andExpect(status().isBadRequest());

        // Validate the Appro in the database
        List<Appro> approList = approRepository.findAll();
        assertThat(approList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Appro in Elasticsearch
        verify(mockApproSearchRepository, times(0)).save(appro);
    }

    @Test
    @Transactional
    public void deleteAppro() throws Exception {
        // Initialize the database
        approRepository.saveAndFlush(appro);

        int databaseSizeBeforeDelete = approRepository.findAll().size();

        // Delete the appro
        restApproMockMvc.perform(delete("/api/appros/{id}", appro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Appro> approList = approRepository.findAll();
        assertThat(approList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Appro in Elasticsearch
        verify(mockApproSearchRepository, times(1)).deleteById(appro.getId());
    }

    @Test
    @Transactional
    public void searchAppro() throws Exception {
        // Initialize the database
        approRepository.saveAndFlush(appro);
        when(mockApproSearchRepository.search(queryStringQuery("id:" + appro.getId())))
            .thenReturn(Collections.singletonList(appro));
        // Search the appro
        restApproMockMvc.perform(get("/api/_search/appros?query=id:" + appro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appro.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteLiv").value(hasItem(DEFAULT_QTE_LIV)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appro.class);
        Appro appro1 = new Appro();
        appro1.setId(1L);
        Appro appro2 = new Appro();
        appro2.setId(appro1.getId());
        assertThat(appro1).isEqualTo(appro2);
        appro2.setId(2L);
        assertThat(appro1).isNotEqualTo(appro2);
        appro1.setId(null);
        assertThat(appro1).isNotEqualTo(appro2);
    }
}
