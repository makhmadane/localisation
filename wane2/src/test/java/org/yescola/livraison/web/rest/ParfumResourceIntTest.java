package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Parfum;
import org.yescola.livraison.repository.ParfumRepository;
import org.yescola.livraison.repository.search.ParfumSearchRepository;
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
 * Test class for the ParfumResource REST controller.
 *
 * @see ParfumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class ParfumResourceIntTest {

    private static final String DEFAULT_NUMEROPARF = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROPARF = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private ParfumRepository parfumRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.ParfumSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParfumSearchRepository mockParfumSearchRepository;

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

    private MockMvc restParfumMockMvc;

    private Parfum parfum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParfumResource parfumResource = new ParfumResource(parfumRepository, mockParfumSearchRepository);
        this.restParfumMockMvc = MockMvcBuilders.standaloneSetup(parfumResource)
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
    public static Parfum createEntity(EntityManager em) {
        Parfum parfum = new Parfum()
            .numeroparf(DEFAULT_NUMEROPARF)
            .libelle(DEFAULT_LIBELLE)
            .etat(DEFAULT_ETAT);
        return parfum;
    }

    @Before
    public void initTest() {
        parfum = createEntity(em);
    }

    @Test
    @Transactional
    public void createParfum() throws Exception {
        int databaseSizeBeforeCreate = parfumRepository.findAll().size();

        // Create the Parfum
        restParfumMockMvc.perform(post("/api/parfums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parfum)))
            .andExpect(status().isCreated());

        // Validate the Parfum in the database
        List<Parfum> parfumList = parfumRepository.findAll();
        assertThat(parfumList).hasSize(databaseSizeBeforeCreate + 1);
        Parfum testParfum = parfumList.get(parfumList.size() - 1);
        assertThat(testParfum.getNumeroparf()).isEqualTo(DEFAULT_NUMEROPARF);
        assertThat(testParfum.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testParfum.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Parfum in Elasticsearch
        verify(mockParfumSearchRepository, times(1)).save(testParfum);
    }

    @Test
    @Transactional
    public void createParfumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parfumRepository.findAll().size();

        // Create the Parfum with an existing ID
        parfum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParfumMockMvc.perform(post("/api/parfums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parfum)))
            .andExpect(status().isBadRequest());

        // Validate the Parfum in the database
        List<Parfum> parfumList = parfumRepository.findAll();
        assertThat(parfumList).hasSize(databaseSizeBeforeCreate);

        // Validate the Parfum in Elasticsearch
        verify(mockParfumSearchRepository, times(0)).save(parfum);
    }

    @Test
    @Transactional
    public void getAllParfums() throws Exception {
        // Initialize the database
        parfumRepository.saveAndFlush(parfum);

        // Get all the parfumList
        restParfumMockMvc.perform(get("/api/parfums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parfum.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroparf").value(hasItem(DEFAULT_NUMEROPARF.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getParfum() throws Exception {
        // Initialize the database
        parfumRepository.saveAndFlush(parfum);

        // Get the parfum
        restParfumMockMvc.perform(get("/api/parfums/{id}", parfum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parfum.getId().intValue()))
            .andExpect(jsonPath("$.numeroparf").value(DEFAULT_NUMEROPARF.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParfum() throws Exception {
        // Get the parfum
        restParfumMockMvc.perform(get("/api/parfums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParfum() throws Exception {
        // Initialize the database
        parfumRepository.saveAndFlush(parfum);

        int databaseSizeBeforeUpdate = parfumRepository.findAll().size();

        // Update the parfum
        Parfum updatedParfum = parfumRepository.findById(parfum.getId()).get();
        // Disconnect from session so that the updates on updatedParfum are not directly saved in db
        em.detach(updatedParfum);
        updatedParfum
            .numeroparf(UPDATED_NUMEROPARF)
            .libelle(UPDATED_LIBELLE)
            .etat(UPDATED_ETAT);

        restParfumMockMvc.perform(put("/api/parfums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParfum)))
            .andExpect(status().isOk());

        // Validate the Parfum in the database
        List<Parfum> parfumList = parfumRepository.findAll();
        assertThat(parfumList).hasSize(databaseSizeBeforeUpdate);
        Parfum testParfum = parfumList.get(parfumList.size() - 1);
        assertThat(testParfum.getNumeroparf()).isEqualTo(UPDATED_NUMEROPARF);
        assertThat(testParfum.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testParfum.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Parfum in Elasticsearch
        verify(mockParfumSearchRepository, times(1)).save(testParfum);
    }

    @Test
    @Transactional
    public void updateNonExistingParfum() throws Exception {
        int databaseSizeBeforeUpdate = parfumRepository.findAll().size();

        // Create the Parfum

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParfumMockMvc.perform(put("/api/parfums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parfum)))
            .andExpect(status().isBadRequest());

        // Validate the Parfum in the database
        List<Parfum> parfumList = parfumRepository.findAll();
        assertThat(parfumList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Parfum in Elasticsearch
        verify(mockParfumSearchRepository, times(0)).save(parfum);
    }

    @Test
    @Transactional
    public void deleteParfum() throws Exception {
        // Initialize the database
        parfumRepository.saveAndFlush(parfum);

        int databaseSizeBeforeDelete = parfumRepository.findAll().size();

        // Delete the parfum
        restParfumMockMvc.perform(delete("/api/parfums/{id}", parfum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parfum> parfumList = parfumRepository.findAll();
        assertThat(parfumList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Parfum in Elasticsearch
        verify(mockParfumSearchRepository, times(1)).deleteById(parfum.getId());
    }

    @Test
    @Transactional
    public void searchParfum() throws Exception {
        // Initialize the database
        parfumRepository.saveAndFlush(parfum);
        when(mockParfumSearchRepository.search(queryStringQuery("id:" + parfum.getId())))
            .thenReturn(Collections.singletonList(parfum));
        // Search the parfum
        restParfumMockMvc.perform(get("/api/_search/parfums?query=id:" + parfum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parfum.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroparf").value(hasItem(DEFAULT_NUMEROPARF)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parfum.class);
        Parfum parfum1 = new Parfum();
        parfum1.setId(1L);
        Parfum parfum2 = new Parfum();
        parfum2.setId(parfum1.getId());
        assertThat(parfum1).isEqualTo(parfum2);
        parfum2.setId(2L);
        assertThat(parfum1).isNotEqualTo(parfum2);
        parfum1.setId(null);
        assertThat(parfum1).isNotEqualTo(parfum2);
    }
}
