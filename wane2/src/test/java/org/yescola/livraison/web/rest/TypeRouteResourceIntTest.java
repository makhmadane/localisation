package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.TypeRoute;
import org.yescola.livraison.repository.TypeRouteRepository;
import org.yescola.livraison.repository.search.TypeRouteSearchRepository;
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
 * Test class for the TypeRouteResource REST controller.
 *
 * @see TypeRouteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class TypeRouteResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private TypeRouteRepository typeRouteRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.TypeRouteSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeRouteSearchRepository mockTypeRouteSearchRepository;

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

    private MockMvc restTypeRouteMockMvc;

    private TypeRoute typeRoute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeRouteResource typeRouteResource = new TypeRouteResource(typeRouteRepository, mockTypeRouteSearchRepository);
        this.restTypeRouteMockMvc = MockMvcBuilders.standaloneSetup(typeRouteResource)
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
    public static TypeRoute createEntity(EntityManager em) {
        TypeRoute typeRoute = new TypeRoute()
            .libelle(DEFAULT_LIBELLE)
            .etat(DEFAULT_ETAT);
        return typeRoute;
    }

    @Before
    public void initTest() {
        typeRoute = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeRoute() throws Exception {
        int databaseSizeBeforeCreate = typeRouteRepository.findAll().size();

        // Create the TypeRoute
        restTypeRouteMockMvc.perform(post("/api/type-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRoute)))
            .andExpect(status().isCreated());

        // Validate the TypeRoute in the database
        List<TypeRoute> typeRouteList = typeRouteRepository.findAll();
        assertThat(typeRouteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRoute testTypeRoute = typeRouteList.get(typeRouteList.size() - 1);
        assertThat(testTypeRoute.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeRoute.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the TypeRoute in Elasticsearch
        verify(mockTypeRouteSearchRepository, times(1)).save(testTypeRoute);
    }

    @Test
    @Transactional
    public void createTypeRouteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRouteRepository.findAll().size();

        // Create the TypeRoute with an existing ID
        typeRoute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRouteMockMvc.perform(post("/api/type-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRoute)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRoute in the database
        List<TypeRoute> typeRouteList = typeRouteRepository.findAll();
        assertThat(typeRouteList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeRoute in Elasticsearch
        verify(mockTypeRouteSearchRepository, times(0)).save(typeRoute);
    }

    @Test
    @Transactional
    public void getAllTypeRoutes() throws Exception {
        // Initialize the database
        typeRouteRepository.saveAndFlush(typeRoute);

        // Get all the typeRouteList
        restTypeRouteMockMvc.perform(get("/api/type-routes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeRoute() throws Exception {
        // Initialize the database
        typeRouteRepository.saveAndFlush(typeRoute);

        // Get the typeRoute
        restTypeRouteMockMvc.perform(get("/api/type-routes/{id}", typeRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeRoute.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeRoute() throws Exception {
        // Get the typeRoute
        restTypeRouteMockMvc.perform(get("/api/type-routes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeRoute() throws Exception {
        // Initialize the database
        typeRouteRepository.saveAndFlush(typeRoute);

        int databaseSizeBeforeUpdate = typeRouteRepository.findAll().size();

        // Update the typeRoute
        TypeRoute updatedTypeRoute = typeRouteRepository.findById(typeRoute.getId()).get();
        // Disconnect from session so that the updates on updatedTypeRoute are not directly saved in db
        em.detach(updatedTypeRoute);
        updatedTypeRoute
            .libelle(UPDATED_LIBELLE)
            .etat(UPDATED_ETAT);

        restTypeRouteMockMvc.perform(put("/api/type-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeRoute)))
            .andExpect(status().isOk());

        // Validate the TypeRoute in the database
        List<TypeRoute> typeRouteList = typeRouteRepository.findAll();
        assertThat(typeRouteList).hasSize(databaseSizeBeforeUpdate);
        TypeRoute testTypeRoute = typeRouteList.get(typeRouteList.size() - 1);
        assertThat(testTypeRoute.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeRoute.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the TypeRoute in Elasticsearch
        verify(mockTypeRouteSearchRepository, times(1)).save(testTypeRoute);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeRoute() throws Exception {
        int databaseSizeBeforeUpdate = typeRouteRepository.findAll().size();

        // Create the TypeRoute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRouteMockMvc.perform(put("/api/type-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRoute)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRoute in the database
        List<TypeRoute> typeRouteList = typeRouteRepository.findAll();
        assertThat(typeRouteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeRoute in Elasticsearch
        verify(mockTypeRouteSearchRepository, times(0)).save(typeRoute);
    }

    @Test
    @Transactional
    public void deleteTypeRoute() throws Exception {
        // Initialize the database
        typeRouteRepository.saveAndFlush(typeRoute);

        int databaseSizeBeforeDelete = typeRouteRepository.findAll().size();

        // Delete the typeRoute
        restTypeRouteMockMvc.perform(delete("/api/type-routes/{id}", typeRoute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeRoute> typeRouteList = typeRouteRepository.findAll();
        assertThat(typeRouteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeRoute in Elasticsearch
        verify(mockTypeRouteSearchRepository, times(1)).deleteById(typeRoute.getId());
    }

    @Test
    @Transactional
    public void searchTypeRoute() throws Exception {
        // Initialize the database
        typeRouteRepository.saveAndFlush(typeRoute);
        when(mockTypeRouteSearchRepository.search(queryStringQuery("id:" + typeRoute.getId())))
            .thenReturn(Collections.singletonList(typeRoute));
        // Search the typeRoute
        restTypeRouteMockMvc.perform(get("/api/_search/type-routes?query=id:" + typeRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRoute.class);
        TypeRoute typeRoute1 = new TypeRoute();
        typeRoute1.setId(1L);
        TypeRoute typeRoute2 = new TypeRoute();
        typeRoute2.setId(typeRoute1.getId());
        assertThat(typeRoute1).isEqualTo(typeRoute2);
        typeRoute2.setId(2L);
        assertThat(typeRoute1).isNotEqualTo(typeRoute2);
        typeRoute1.setId(null);
        assertThat(typeRoute1).isNotEqualTo(typeRoute2);
    }
}
