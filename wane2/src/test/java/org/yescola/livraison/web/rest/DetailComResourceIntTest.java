package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.DetailCom;
import org.yescola.livraison.repository.DetailComRepository;
import org.yescola.livraison.repository.search.DetailComSearchRepository;
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
 * Test class for the DetailComResource REST controller.
 *
 * @see DetailComResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class DetailComResourceIntTest {

    private static final String DEFAULT_QTE_COM = "AAAAAAAAAA";
    private static final String UPDATED_QTE_COM = "BBBBBBBBBB";

    private static final String DEFAULT_QTE_LIV = "AAAAAAAAAA";
    private static final String UPDATED_QTE_LIV = "BBBBBBBBBB";

    @Autowired
    private DetailComRepository detailComRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.DetailComSearchRepositoryMockConfiguration
     */
    @Autowired
    private DetailComSearchRepository mockDetailComSearchRepository;

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

    private MockMvc restDetailComMockMvc;

    private DetailCom detailCom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailComResource detailComResource = new DetailComResource(detailComRepository, mockDetailComSearchRepository);
        this.restDetailComMockMvc = MockMvcBuilders.standaloneSetup(detailComResource)
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
    public static DetailCom createEntity(EntityManager em) {
        DetailCom detailCom = new DetailCom()
            .qteCom(DEFAULT_QTE_COM)
            .qteLiv(DEFAULT_QTE_LIV);
        return detailCom;
    }

    @Before
    public void initTest() {
        detailCom = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailCom() throws Exception {
        int databaseSizeBeforeCreate = detailComRepository.findAll().size();

        // Create the DetailCom
        restDetailComMockMvc.perform(post("/api/detail-coms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailCom)))
            .andExpect(status().isCreated());

        // Validate the DetailCom in the database
        List<DetailCom> detailComList = detailComRepository.findAll();
        assertThat(detailComList).hasSize(databaseSizeBeforeCreate + 1);
        DetailCom testDetailCom = detailComList.get(detailComList.size() - 1);
        assertThat(testDetailCom.getQteCom()).isEqualTo(DEFAULT_QTE_COM);
        assertThat(testDetailCom.getQteLiv()).isEqualTo(DEFAULT_QTE_LIV);

        // Validate the DetailCom in Elasticsearch
        verify(mockDetailComSearchRepository, times(1)).save(testDetailCom);
    }

    @Test
    @Transactional
    public void createDetailComWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailComRepository.findAll().size();

        // Create the DetailCom with an existing ID
        detailCom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailComMockMvc.perform(post("/api/detail-coms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailCom)))
            .andExpect(status().isBadRequest());

        // Validate the DetailCom in the database
        List<DetailCom> detailComList = detailComRepository.findAll();
        assertThat(detailComList).hasSize(databaseSizeBeforeCreate);

        // Validate the DetailCom in Elasticsearch
        verify(mockDetailComSearchRepository, times(0)).save(detailCom);
    }

    @Test
    @Transactional
    public void getAllDetailComs() throws Exception {
        // Initialize the database
        detailComRepository.saveAndFlush(detailCom);

        // Get all the detailComList
        restDetailComMockMvc.perform(get("/api/detail-coms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailCom.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteCom").value(hasItem(DEFAULT_QTE_COM.toString())))
            .andExpect(jsonPath("$.[*].qteLiv").value(hasItem(DEFAULT_QTE_LIV.toString())));
    }
    
    @Test
    @Transactional
    public void getDetailCom() throws Exception {
        // Initialize the database
        detailComRepository.saveAndFlush(detailCom);

        // Get the detailCom
        restDetailComMockMvc.perform(get("/api/detail-coms/{id}", detailCom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailCom.getId().intValue()))
            .andExpect(jsonPath("$.qteCom").value(DEFAULT_QTE_COM.toString()))
            .andExpect(jsonPath("$.qteLiv").value(DEFAULT_QTE_LIV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailCom() throws Exception {
        // Get the detailCom
        restDetailComMockMvc.perform(get("/api/detail-coms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailCom() throws Exception {
        // Initialize the database
        detailComRepository.saveAndFlush(detailCom);

        int databaseSizeBeforeUpdate = detailComRepository.findAll().size();

        // Update the detailCom
        DetailCom updatedDetailCom = detailComRepository.findById(detailCom.getId()).get();
        // Disconnect from session so that the updates on updatedDetailCom are not directly saved in db
        em.detach(updatedDetailCom);
        updatedDetailCom
            .qteCom(UPDATED_QTE_COM)
            .qteLiv(UPDATED_QTE_LIV);

        restDetailComMockMvc.perform(put("/api/detail-coms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetailCom)))
            .andExpect(status().isOk());

        // Validate the DetailCom in the database
        List<DetailCom> detailComList = detailComRepository.findAll();
        assertThat(detailComList).hasSize(databaseSizeBeforeUpdate);
        DetailCom testDetailCom = detailComList.get(detailComList.size() - 1);
        assertThat(testDetailCom.getQteCom()).isEqualTo(UPDATED_QTE_COM);
        assertThat(testDetailCom.getQteLiv()).isEqualTo(UPDATED_QTE_LIV);

        // Validate the DetailCom in Elasticsearch
        verify(mockDetailComSearchRepository, times(1)).save(testDetailCom);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailCom() throws Exception {
        int databaseSizeBeforeUpdate = detailComRepository.findAll().size();

        // Create the DetailCom

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailComMockMvc.perform(put("/api/detail-coms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailCom)))
            .andExpect(status().isBadRequest());

        // Validate the DetailCom in the database
        List<DetailCom> detailComList = detailComRepository.findAll();
        assertThat(detailComList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DetailCom in Elasticsearch
        verify(mockDetailComSearchRepository, times(0)).save(detailCom);
    }

    @Test
    @Transactional
    public void deleteDetailCom() throws Exception {
        // Initialize the database
        detailComRepository.saveAndFlush(detailCom);

        int databaseSizeBeforeDelete = detailComRepository.findAll().size();

        // Delete the detailCom
        restDetailComMockMvc.perform(delete("/api/detail-coms/{id}", detailCom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetailCom> detailComList = detailComRepository.findAll();
        assertThat(detailComList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DetailCom in Elasticsearch
        verify(mockDetailComSearchRepository, times(1)).deleteById(detailCom.getId());
    }

    @Test
    @Transactional
    public void searchDetailCom() throws Exception {
        // Initialize the database
        detailComRepository.saveAndFlush(detailCom);
        when(mockDetailComSearchRepository.search(queryStringQuery("id:" + detailCom.getId())))
            .thenReturn(Collections.singletonList(detailCom));
        // Search the detailCom
        restDetailComMockMvc.perform(get("/api/_search/detail-coms?query=id:" + detailCom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailCom.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteCom").value(hasItem(DEFAULT_QTE_COM)))
            .andExpect(jsonPath("$.[*].qteLiv").value(hasItem(DEFAULT_QTE_LIV)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailCom.class);
        DetailCom detailCom1 = new DetailCom();
        detailCom1.setId(1L);
        DetailCom detailCom2 = new DetailCom();
        detailCom2.setId(detailCom1.getId());
        assertThat(detailCom1).isEqualTo(detailCom2);
        detailCom2.setId(2L);
        assertThat(detailCom1).isNotEqualTo(detailCom2);
        detailCom1.setId(null);
        assertThat(detailCom1).isNotEqualTo(detailCom2);
    }
}
