package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.DetailRoute;
import org.yescola.livraison.repository.DetailRouteRepository;
import org.yescola.livraison.repository.search.DetailRouteSearchRepository;
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
 * Test class for the DetailRouteResource REST controller.
 *
 * @see DetailRouteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class DetailRouteResourceIntTest {

    private static final String DEFAULT_HEURE_A_COM = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_A_COM = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_F_COM = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_F_COM = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_A_LIV = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_A_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_F_LIV = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_F_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_DISTANCE_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCE_DEPOT = "BBBBBBBBBB";

    @Autowired
    private DetailRouteRepository detailRouteRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.DetailRouteSearchRepositoryMockConfiguration
     */
    @Autowired
    private DetailRouteSearchRepository mockDetailRouteSearchRepository;

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

    private MockMvc restDetailRouteMockMvc;

    private DetailRoute detailRoute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailRouteResource detailRouteResource = new DetailRouteResource(detailRouteRepository, mockDetailRouteSearchRepository);
        this.restDetailRouteMockMvc = MockMvcBuilders.standaloneSetup(detailRouteResource)
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
    public static DetailRoute createEntity(EntityManager em) {
        DetailRoute detailRoute = new DetailRoute()
            .heureACom(DEFAULT_HEURE_A_COM)
            .heureFCom(DEFAULT_HEURE_F_COM)
            .heureALiv(DEFAULT_HEURE_A_LIV)
            .heureFLiv(DEFAULT_HEURE_F_LIV)
            .distanceDepot(DEFAULT_DISTANCE_DEPOT);
        return detailRoute;
    }

    @Before
    public void initTest() {
        detailRoute = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailRoute() throws Exception {
        int databaseSizeBeforeCreate = detailRouteRepository.findAll().size();

        // Create the DetailRoute
        restDetailRouteMockMvc.perform(post("/api/detail-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailRoute)))
            .andExpect(status().isCreated());

        // Validate the DetailRoute in the database
        List<DetailRoute> detailRouteList = detailRouteRepository.findAll();
        assertThat(detailRouteList).hasSize(databaseSizeBeforeCreate + 1);
        DetailRoute testDetailRoute = detailRouteList.get(detailRouteList.size() - 1);
        assertThat(testDetailRoute.getHeureACom()).isEqualTo(DEFAULT_HEURE_A_COM);
        assertThat(testDetailRoute.getHeureFCom()).isEqualTo(DEFAULT_HEURE_F_COM);
        assertThat(testDetailRoute.getHeureALiv()).isEqualTo(DEFAULT_HEURE_A_LIV);
        assertThat(testDetailRoute.getHeureFLiv()).isEqualTo(DEFAULT_HEURE_F_LIV);
        assertThat(testDetailRoute.getDistanceDepot()).isEqualTo(DEFAULT_DISTANCE_DEPOT);

        // Validate the DetailRoute in Elasticsearch
        verify(mockDetailRouteSearchRepository, times(1)).save(testDetailRoute);
    }

    @Test
    @Transactional
    public void createDetailRouteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailRouteRepository.findAll().size();

        // Create the DetailRoute with an existing ID
        detailRoute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailRouteMockMvc.perform(post("/api/detail-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailRoute)))
            .andExpect(status().isBadRequest());

        // Validate the DetailRoute in the database
        List<DetailRoute> detailRouteList = detailRouteRepository.findAll();
        assertThat(detailRouteList).hasSize(databaseSizeBeforeCreate);

        // Validate the DetailRoute in Elasticsearch
        verify(mockDetailRouteSearchRepository, times(0)).save(detailRoute);
    }

    @Test
    @Transactional
    public void getAllDetailRoutes() throws Exception {
        // Initialize the database
        detailRouteRepository.saveAndFlush(detailRoute);

        // Get all the detailRouteList
        restDetailRouteMockMvc.perform(get("/api/detail-routes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].heureACom").value(hasItem(DEFAULT_HEURE_A_COM.toString())))
            .andExpect(jsonPath("$.[*].heureFCom").value(hasItem(DEFAULT_HEURE_F_COM.toString())))
            .andExpect(jsonPath("$.[*].heureALiv").value(hasItem(DEFAULT_HEURE_A_LIV.toString())))
            .andExpect(jsonPath("$.[*].heureFLiv").value(hasItem(DEFAULT_HEURE_F_LIV.toString())))
            .andExpect(jsonPath("$.[*].distanceDepot").value(hasItem(DEFAULT_DISTANCE_DEPOT.toString())));
    }
    
    @Test
    @Transactional
    public void getDetailRoute() throws Exception {
        // Initialize the database
        detailRouteRepository.saveAndFlush(detailRoute);

        // Get the detailRoute
        restDetailRouteMockMvc.perform(get("/api/detail-routes/{id}", detailRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailRoute.getId().intValue()))
            .andExpect(jsonPath("$.heureACom").value(DEFAULT_HEURE_A_COM.toString()))
            .andExpect(jsonPath("$.heureFCom").value(DEFAULT_HEURE_F_COM.toString()))
            .andExpect(jsonPath("$.heureALiv").value(DEFAULT_HEURE_A_LIV.toString()))
            .andExpect(jsonPath("$.heureFLiv").value(DEFAULT_HEURE_F_LIV.toString()))
            .andExpect(jsonPath("$.distanceDepot").value(DEFAULT_DISTANCE_DEPOT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailRoute() throws Exception {
        // Get the detailRoute
        restDetailRouteMockMvc.perform(get("/api/detail-routes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailRoute() throws Exception {
        // Initialize the database
        detailRouteRepository.saveAndFlush(detailRoute);

        int databaseSizeBeforeUpdate = detailRouteRepository.findAll().size();

        // Update the detailRoute
        DetailRoute updatedDetailRoute = detailRouteRepository.findById(detailRoute.getId()).get();
        // Disconnect from session so that the updates on updatedDetailRoute are not directly saved in db
        em.detach(updatedDetailRoute);
        updatedDetailRoute
            .heureACom(UPDATED_HEURE_A_COM)
            .heureFCom(UPDATED_HEURE_F_COM)
            .heureALiv(UPDATED_HEURE_A_LIV)
            .heureFLiv(UPDATED_HEURE_F_LIV)
            .distanceDepot(UPDATED_DISTANCE_DEPOT);

        restDetailRouteMockMvc.perform(put("/api/detail-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetailRoute)))
            .andExpect(status().isOk());

        // Validate the DetailRoute in the database
        List<DetailRoute> detailRouteList = detailRouteRepository.findAll();
        assertThat(detailRouteList).hasSize(databaseSizeBeforeUpdate);
        DetailRoute testDetailRoute = detailRouteList.get(detailRouteList.size() - 1);
        assertThat(testDetailRoute.getHeureACom()).isEqualTo(UPDATED_HEURE_A_COM);
        assertThat(testDetailRoute.getHeureFCom()).isEqualTo(UPDATED_HEURE_F_COM);
        assertThat(testDetailRoute.getHeureALiv()).isEqualTo(UPDATED_HEURE_A_LIV);
        assertThat(testDetailRoute.getHeureFLiv()).isEqualTo(UPDATED_HEURE_F_LIV);
        assertThat(testDetailRoute.getDistanceDepot()).isEqualTo(UPDATED_DISTANCE_DEPOT);

        // Validate the DetailRoute in Elasticsearch
        verify(mockDetailRouteSearchRepository, times(1)).save(testDetailRoute);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailRoute() throws Exception {
        int databaseSizeBeforeUpdate = detailRouteRepository.findAll().size();

        // Create the DetailRoute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailRouteMockMvc.perform(put("/api/detail-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailRoute)))
            .andExpect(status().isBadRequest());

        // Validate the DetailRoute in the database
        List<DetailRoute> detailRouteList = detailRouteRepository.findAll();
        assertThat(detailRouteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DetailRoute in Elasticsearch
        verify(mockDetailRouteSearchRepository, times(0)).save(detailRoute);
    }

    @Test
    @Transactional
    public void deleteDetailRoute() throws Exception {
        // Initialize the database
        detailRouteRepository.saveAndFlush(detailRoute);

        int databaseSizeBeforeDelete = detailRouteRepository.findAll().size();

        // Delete the detailRoute
        restDetailRouteMockMvc.perform(delete("/api/detail-routes/{id}", detailRoute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetailRoute> detailRouteList = detailRouteRepository.findAll();
        assertThat(detailRouteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DetailRoute in Elasticsearch
        verify(mockDetailRouteSearchRepository, times(1)).deleteById(detailRoute.getId());
    }

    @Test
    @Transactional
    public void searchDetailRoute() throws Exception {
        // Initialize the database
        detailRouteRepository.saveAndFlush(detailRoute);
        when(mockDetailRouteSearchRepository.search(queryStringQuery("id:" + detailRoute.getId())))
            .thenReturn(Collections.singletonList(detailRoute));
        // Search the detailRoute
        restDetailRouteMockMvc.perform(get("/api/_search/detail-routes?query=id:" + detailRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].heureACom").value(hasItem(DEFAULT_HEURE_A_COM)))
            .andExpect(jsonPath("$.[*].heureFCom").value(hasItem(DEFAULT_HEURE_F_COM)))
            .andExpect(jsonPath("$.[*].heureALiv").value(hasItem(DEFAULT_HEURE_A_LIV)))
            .andExpect(jsonPath("$.[*].heureFLiv").value(hasItem(DEFAULT_HEURE_F_LIV)))
            .andExpect(jsonPath("$.[*].distanceDepot").value(hasItem(DEFAULT_DISTANCE_DEPOT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailRoute.class);
        DetailRoute detailRoute1 = new DetailRoute();
        detailRoute1.setId(1L);
        DetailRoute detailRoute2 = new DetailRoute();
        detailRoute2.setId(detailRoute1.getId());
        assertThat(detailRoute1).isEqualTo(detailRoute2);
        detailRoute2.setId(2L);
        assertThat(detailRoute1).isNotEqualTo(detailRoute2);
        detailRoute1.setId(null);
        assertThat(detailRoute1).isNotEqualTo(detailRoute2);
    }
}
