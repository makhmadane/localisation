package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.TypeRglment;
import org.yescola.livraison.repository.TypeRglmentRepository;
import org.yescola.livraison.repository.search.TypeRglmentSearchRepository;
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
 * Test class for the TypeRglmentResource REST controller.
 *
 * @see TypeRglmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class TypeRglmentResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private TypeRglmentRepository typeRglmentRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.TypeRglmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeRglmentSearchRepository mockTypeRglmentSearchRepository;

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

    private MockMvc restTypeRglmentMockMvc;

    private TypeRglment typeRglment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeRglmentResource typeRglmentResource = new TypeRglmentResource(typeRglmentRepository, mockTypeRglmentSearchRepository);
        this.restTypeRglmentMockMvc = MockMvcBuilders.standaloneSetup(typeRglmentResource)
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
    public static TypeRglment createEntity(EntityManager em) {
        TypeRglment typeRglment = new TypeRglment()
            .libelle(DEFAULT_LIBELLE)
            .etat(DEFAULT_ETAT);
        return typeRglment;
    }

    @Before
    public void initTest() {
        typeRglment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeRglment() throws Exception {
        int databaseSizeBeforeCreate = typeRglmentRepository.findAll().size();

        // Create the TypeRglment
        restTypeRglmentMockMvc.perform(post("/api/type-rglments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRglment)))
            .andExpect(status().isCreated());

        // Validate the TypeRglment in the database
        List<TypeRglment> typeRglmentList = typeRglmentRepository.findAll();
        assertThat(typeRglmentList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRglment testTypeRglment = typeRglmentList.get(typeRglmentList.size() - 1);
        assertThat(testTypeRglment.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeRglment.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the TypeRglment in Elasticsearch
        verify(mockTypeRglmentSearchRepository, times(1)).save(testTypeRglment);
    }

    @Test
    @Transactional
    public void createTypeRglmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRglmentRepository.findAll().size();

        // Create the TypeRglment with an existing ID
        typeRglment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRglmentMockMvc.perform(post("/api/type-rglments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRglment)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRglment in the database
        List<TypeRglment> typeRglmentList = typeRglmentRepository.findAll();
        assertThat(typeRglmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeRglment in Elasticsearch
        verify(mockTypeRglmentSearchRepository, times(0)).save(typeRglment);
    }

    @Test
    @Transactional
    public void getAllTypeRglments() throws Exception {
        // Initialize the database
        typeRglmentRepository.saveAndFlush(typeRglment);

        // Get all the typeRglmentList
        restTypeRglmentMockMvc.perform(get("/api/type-rglments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRglment.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeRglment() throws Exception {
        // Initialize the database
        typeRglmentRepository.saveAndFlush(typeRglment);

        // Get the typeRglment
        restTypeRglmentMockMvc.perform(get("/api/type-rglments/{id}", typeRglment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeRglment.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeRglment() throws Exception {
        // Get the typeRglment
        restTypeRglmentMockMvc.perform(get("/api/type-rglments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeRglment() throws Exception {
        // Initialize the database
        typeRglmentRepository.saveAndFlush(typeRglment);

        int databaseSizeBeforeUpdate = typeRglmentRepository.findAll().size();

        // Update the typeRglment
        TypeRglment updatedTypeRglment = typeRglmentRepository.findById(typeRglment.getId()).get();
        // Disconnect from session so that the updates on updatedTypeRglment are not directly saved in db
        em.detach(updatedTypeRglment);
        updatedTypeRglment
            .libelle(UPDATED_LIBELLE)
            .etat(UPDATED_ETAT);

        restTypeRglmentMockMvc.perform(put("/api/type-rglments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeRglment)))
            .andExpect(status().isOk());

        // Validate the TypeRglment in the database
        List<TypeRglment> typeRglmentList = typeRglmentRepository.findAll();
        assertThat(typeRglmentList).hasSize(databaseSizeBeforeUpdate);
        TypeRglment testTypeRglment = typeRglmentList.get(typeRglmentList.size() - 1);
        assertThat(testTypeRglment.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeRglment.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the TypeRglment in Elasticsearch
        verify(mockTypeRglmentSearchRepository, times(1)).save(testTypeRglment);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeRglment() throws Exception {
        int databaseSizeBeforeUpdate = typeRglmentRepository.findAll().size();

        // Create the TypeRglment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRglmentMockMvc.perform(put("/api/type-rglments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRglment)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRglment in the database
        List<TypeRglment> typeRglmentList = typeRglmentRepository.findAll();
        assertThat(typeRglmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeRglment in Elasticsearch
        verify(mockTypeRglmentSearchRepository, times(0)).save(typeRglment);
    }

    @Test
    @Transactional
    public void deleteTypeRglment() throws Exception {
        // Initialize the database
        typeRglmentRepository.saveAndFlush(typeRglment);

        int databaseSizeBeforeDelete = typeRglmentRepository.findAll().size();

        // Delete the typeRglment
        restTypeRglmentMockMvc.perform(delete("/api/type-rglments/{id}", typeRglment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeRglment> typeRglmentList = typeRglmentRepository.findAll();
        assertThat(typeRglmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeRglment in Elasticsearch
        verify(mockTypeRglmentSearchRepository, times(1)).deleteById(typeRglment.getId());
    }

    @Test
    @Transactional
    public void searchTypeRglment() throws Exception {
        // Initialize the database
        typeRglmentRepository.saveAndFlush(typeRglment);
        when(mockTypeRglmentSearchRepository.search(queryStringQuery("id:" + typeRglment.getId())))
            .thenReturn(Collections.singletonList(typeRglment));
        // Search the typeRglment
        restTypeRglmentMockMvc.perform(get("/api/_search/type-rglments?query=id:" + typeRglment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRglment.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRglment.class);
        TypeRglment typeRglment1 = new TypeRglment();
        typeRglment1.setId(1L);
        TypeRglment typeRglment2 = new TypeRglment();
        typeRglment2.setId(typeRglment1.getId());
        assertThat(typeRglment1).isEqualTo(typeRglment2);
        typeRglment2.setId(2L);
        assertThat(typeRglment1).isNotEqualTo(typeRglment2);
        typeRglment1.setId(null);
        assertThat(typeRglment1).isNotEqualTo(typeRglment2);
    }
}
