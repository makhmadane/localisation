package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.TypeTransport;
import org.yescola.livraison.repository.TypeTransportRepository;
import org.yescola.livraison.repository.search.TypeTransportSearchRepository;
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
 * Test class for the TypeTransportResource REST controller.
 *
 * @see TypeTransportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class TypeTransportResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeTransportRepository typeTransportRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.TypeTransportSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeTransportSearchRepository mockTypeTransportSearchRepository;

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

    private MockMvc restTypeTransportMockMvc;

    private TypeTransport typeTransport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTransportResource typeTransportResource = new TypeTransportResource(typeTransportRepository, mockTypeTransportSearchRepository);
        this.restTypeTransportMockMvc = MockMvcBuilders.standaloneSetup(typeTransportResource)
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
    public static TypeTransport createEntity(EntityManager em) {
        TypeTransport typeTransport = new TypeTransport()
            .libelle(DEFAULT_LIBELLE);
        return typeTransport;
    }

    @Before
    public void initTest() {
        typeTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTransport() throws Exception {
        int databaseSizeBeforeCreate = typeTransportRepository.findAll().size();

        // Create the TypeTransport
        restTypeTransportMockMvc.perform(post("/api/type-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTransport)))
            .andExpect(status().isCreated());

        // Validate the TypeTransport in the database
        List<TypeTransport> typeTransportList = typeTransportRepository.findAll();
        assertThat(typeTransportList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTransport testTypeTransport = typeTransportList.get(typeTransportList.size() - 1);
        assertThat(testTypeTransport.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the TypeTransport in Elasticsearch
        verify(mockTypeTransportSearchRepository, times(1)).save(testTypeTransport);
    }

    @Test
    @Transactional
    public void createTypeTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTransportRepository.findAll().size();

        // Create the TypeTransport with an existing ID
        typeTransport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTransportMockMvc.perform(post("/api/type-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTransport)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTransport in the database
        List<TypeTransport> typeTransportList = typeTransportRepository.findAll();
        assertThat(typeTransportList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeTransport in Elasticsearch
        verify(mockTypeTransportSearchRepository, times(0)).save(typeTransport);
    }

    @Test
    @Transactional
    public void getAllTypeTransports() throws Exception {
        // Initialize the database
        typeTransportRepository.saveAndFlush(typeTransport);

        // Get all the typeTransportList
        restTypeTransportMockMvc.perform(get("/api/type-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeTransport() throws Exception {
        // Initialize the database
        typeTransportRepository.saveAndFlush(typeTransport);

        // Get the typeTransport
        restTypeTransportMockMvc.perform(get("/api/type-transports/{id}", typeTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeTransport.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeTransport() throws Exception {
        // Get the typeTransport
        restTypeTransportMockMvc.perform(get("/api/type-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTransport() throws Exception {
        // Initialize the database
        typeTransportRepository.saveAndFlush(typeTransport);

        int databaseSizeBeforeUpdate = typeTransportRepository.findAll().size();

        // Update the typeTransport
        TypeTransport updatedTypeTransport = typeTransportRepository.findById(typeTransport.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTransport are not directly saved in db
        em.detach(updatedTypeTransport);
        updatedTypeTransport
            .libelle(UPDATED_LIBELLE);

        restTypeTransportMockMvc.perform(put("/api/type-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeTransport)))
            .andExpect(status().isOk());

        // Validate the TypeTransport in the database
        List<TypeTransport> typeTransportList = typeTransportRepository.findAll();
        assertThat(typeTransportList).hasSize(databaseSizeBeforeUpdate);
        TypeTransport testTypeTransport = typeTransportList.get(typeTransportList.size() - 1);
        assertThat(testTypeTransport.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the TypeTransport in Elasticsearch
        verify(mockTypeTransportSearchRepository, times(1)).save(testTypeTransport);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTransport() throws Exception {
        int databaseSizeBeforeUpdate = typeTransportRepository.findAll().size();

        // Create the TypeTransport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTransportMockMvc.perform(put("/api/type-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTransport)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTransport in the database
        List<TypeTransport> typeTransportList = typeTransportRepository.findAll();
        assertThat(typeTransportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeTransport in Elasticsearch
        verify(mockTypeTransportSearchRepository, times(0)).save(typeTransport);
    }

    @Test
    @Transactional
    public void deleteTypeTransport() throws Exception {
        // Initialize the database
        typeTransportRepository.saveAndFlush(typeTransport);

        int databaseSizeBeforeDelete = typeTransportRepository.findAll().size();

        // Delete the typeTransport
        restTypeTransportMockMvc.perform(delete("/api/type-transports/{id}", typeTransport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeTransport> typeTransportList = typeTransportRepository.findAll();
        assertThat(typeTransportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeTransport in Elasticsearch
        verify(mockTypeTransportSearchRepository, times(1)).deleteById(typeTransport.getId());
    }

    @Test
    @Transactional
    public void searchTypeTransport() throws Exception {
        // Initialize the database
        typeTransportRepository.saveAndFlush(typeTransport);
        when(mockTypeTransportSearchRepository.search(queryStringQuery("id:" + typeTransport.getId())))
            .thenReturn(Collections.singletonList(typeTransport));
        // Search the typeTransport
        restTypeTransportMockMvc.perform(get("/api/_search/type-transports?query=id:" + typeTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTransport.class);
        TypeTransport typeTransport1 = new TypeTransport();
        typeTransport1.setId(1L);
        TypeTransport typeTransport2 = new TypeTransport();
        typeTransport2.setId(typeTransport1.getId());
        assertThat(typeTransport1).isEqualTo(typeTransport2);
        typeTransport2.setId(2L);
        assertThat(typeTransport1).isNotEqualTo(typeTransport2);
        typeTransport1.setId(null);
        assertThat(typeTransport1).isNotEqualTo(typeTransport2);
    }
}
