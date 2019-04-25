package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Route;
import org.yescola.livraison.repository.RouteRepository;
import org.yescola.livraison.repository.search.RouteSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the RouteResource REST controller.
 *
 * @see RouteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class RouteResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_JOURNEE = "AAAAAAAAAA";
    private static final String UPDATED_JOURNEE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEDEP_COM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEP_COM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_R_COM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_R_COM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEURE_DEP_COM = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEP_COM = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_FIN_COM = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_FIN_COM = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_DEP_LIV = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEP_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_FIN_LIV = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_FIN_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_JOURNEE_LIV = "AAAAAAAAAA";
    private static final String UPDATED_JOURNEE_LIV = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEDEP_LIV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEP_LIV = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_R_LIV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_R_LIV = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private RouteRepository routeRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.RouteSearchRepositoryMockConfiguration
     */
    @Autowired
    private RouteSearchRepository mockRouteSearchRepository;

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

    private MockMvc restRouteMockMvc;

    private Route route;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RouteResource routeResource = new RouteResource(routeRepository, mockRouteSearchRepository);
        this.restRouteMockMvc = MockMvcBuilders.standaloneSetup(routeResource)
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
    public static Route createEntity(EntityManager em) {
        Route route = new Route()
            .numero(DEFAULT_NUMERO)
            .journee(DEFAULT_JOURNEE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .datedepCom(DEFAULT_DATEDEP_COM)
            .dateRCom(DEFAULT_DATE_R_COM)
            .heureDepCom(DEFAULT_HEURE_DEP_COM)
            .heureFinCom(DEFAULT_HEURE_FIN_COM)
            .heureDepLiv(DEFAULT_HEURE_DEP_LIV)
            .heureFinLiv(DEFAULT_HEURE_FIN_LIV)
            .journeeLiv(DEFAULT_JOURNEE_LIV)
            .datedepLiv(DEFAULT_DATEDEP_LIV)
            .dateRLiv(DEFAULT_DATE_R_LIV)
            .etat(DEFAULT_ETAT);
        return route;
    }

    @Before
    public void initTest() {
        route = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoute() throws Exception {
        int databaseSizeBeforeCreate = routeRepository.findAll().size();

        // Create the Route
        restRouteMockMvc.perform(post("/api/routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isCreated());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate + 1);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRoute.getJournee()).isEqualTo(DEFAULT_JOURNEE);
        assertThat(testRoute.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testRoute.getDatedepCom()).isEqualTo(DEFAULT_DATEDEP_COM);
        assertThat(testRoute.getDateRCom()).isEqualTo(DEFAULT_DATE_R_COM);
        assertThat(testRoute.getHeureDepCom()).isEqualTo(DEFAULT_HEURE_DEP_COM);
        assertThat(testRoute.getHeureFinCom()).isEqualTo(DEFAULT_HEURE_FIN_COM);
        assertThat(testRoute.getHeureDepLiv()).isEqualTo(DEFAULT_HEURE_DEP_LIV);
        assertThat(testRoute.getHeureFinLiv()).isEqualTo(DEFAULT_HEURE_FIN_LIV);
        assertThat(testRoute.getJourneeLiv()).isEqualTo(DEFAULT_JOURNEE_LIV);
        assertThat(testRoute.getDatedepLiv()).isEqualTo(DEFAULT_DATEDEP_LIV);
        assertThat(testRoute.getDateRLiv()).isEqualTo(DEFAULT_DATE_R_LIV);
        assertThat(testRoute.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Route in Elasticsearch
        verify(mockRouteSearchRepository, times(1)).save(testRoute);
    }

    @Test
    @Transactional
    public void createRouteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = routeRepository.findAll().size();

        // Create the Route with an existing ID
        route.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRouteMockMvc.perform(post("/api/routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Route in Elasticsearch
        verify(mockRouteSearchRepository, times(0)).save(route);
    }

    @Test
    @Transactional
    public void getAllRoutes() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get all the routeList
        restRouteMockMvc.perform(get("/api/routes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(route.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].journee").value(hasItem(DEFAULT_JOURNEE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].datedepCom").value(hasItem(DEFAULT_DATEDEP_COM.toString())))
            .andExpect(jsonPath("$.[*].dateRCom").value(hasItem(DEFAULT_DATE_R_COM.toString())))
            .andExpect(jsonPath("$.[*].heureDepCom").value(hasItem(DEFAULT_HEURE_DEP_COM.toString())))
            .andExpect(jsonPath("$.[*].heureFinCom").value(hasItem(DEFAULT_HEURE_FIN_COM.toString())))
            .andExpect(jsonPath("$.[*].heureDepLiv").value(hasItem(DEFAULT_HEURE_DEP_LIV.toString())))
            .andExpect(jsonPath("$.[*].heureFinLiv").value(hasItem(DEFAULT_HEURE_FIN_LIV.toString())))
            .andExpect(jsonPath("$.[*].journeeLiv").value(hasItem(DEFAULT_JOURNEE_LIV.toString())))
            .andExpect(jsonPath("$.[*].datedepLiv").value(hasItem(DEFAULT_DATEDEP_LIV.toString())))
            .andExpect(jsonPath("$.[*].dateRLiv").value(hasItem(DEFAULT_DATE_R_LIV.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get the route
        restRouteMockMvc.perform(get("/api/routes/{id}", route.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(route.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.journee").value(DEFAULT_JOURNEE.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.datedepCom").value(DEFAULT_DATEDEP_COM.toString()))
            .andExpect(jsonPath("$.dateRCom").value(DEFAULT_DATE_R_COM.toString()))
            .andExpect(jsonPath("$.heureDepCom").value(DEFAULT_HEURE_DEP_COM.toString()))
            .andExpect(jsonPath("$.heureFinCom").value(DEFAULT_HEURE_FIN_COM.toString()))
            .andExpect(jsonPath("$.heureDepLiv").value(DEFAULT_HEURE_DEP_LIV.toString()))
            .andExpect(jsonPath("$.heureFinLiv").value(DEFAULT_HEURE_FIN_LIV.toString()))
            .andExpect(jsonPath("$.journeeLiv").value(DEFAULT_JOURNEE_LIV.toString()))
            .andExpect(jsonPath("$.datedepLiv").value(DEFAULT_DATEDEP_LIV.toString()))
            .andExpect(jsonPath("$.dateRLiv").value(DEFAULT_DATE_R_LIV.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoute() throws Exception {
        // Get the route
        restRouteMockMvc.perform(get("/api/routes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route
        Route updatedRoute = routeRepository.findById(route.getId()).get();
        // Disconnect from session so that the updates on updatedRoute are not directly saved in db
        em.detach(updatedRoute);
        updatedRoute
            .numero(UPDATED_NUMERO)
            .journee(UPDATED_JOURNEE)
            .dateCreation(UPDATED_DATE_CREATION)
            .datedepCom(UPDATED_DATEDEP_COM)
            .dateRCom(UPDATED_DATE_R_COM)
            .heureDepCom(UPDATED_HEURE_DEP_COM)
            .heureFinCom(UPDATED_HEURE_FIN_COM)
            .heureDepLiv(UPDATED_HEURE_DEP_LIV)
            .heureFinLiv(UPDATED_HEURE_FIN_LIV)
            .journeeLiv(UPDATED_JOURNEE_LIV)
            .datedepLiv(UPDATED_DATEDEP_LIV)
            .dateRLiv(UPDATED_DATE_R_LIV)
            .etat(UPDATED_ETAT);

        restRouteMockMvc.perform(put("/api/routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoute)))
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRoute.getJournee()).isEqualTo(UPDATED_JOURNEE);
        assertThat(testRoute.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testRoute.getDatedepCom()).isEqualTo(UPDATED_DATEDEP_COM);
        assertThat(testRoute.getDateRCom()).isEqualTo(UPDATED_DATE_R_COM);
        assertThat(testRoute.getHeureDepCom()).isEqualTo(UPDATED_HEURE_DEP_COM);
        assertThat(testRoute.getHeureFinCom()).isEqualTo(UPDATED_HEURE_FIN_COM);
        assertThat(testRoute.getHeureDepLiv()).isEqualTo(UPDATED_HEURE_DEP_LIV);
        assertThat(testRoute.getHeureFinLiv()).isEqualTo(UPDATED_HEURE_FIN_LIV);
        assertThat(testRoute.getJourneeLiv()).isEqualTo(UPDATED_JOURNEE_LIV);
        assertThat(testRoute.getDatedepLiv()).isEqualTo(UPDATED_DATEDEP_LIV);
        assertThat(testRoute.getDateRLiv()).isEqualTo(UPDATED_DATE_R_LIV);
        assertThat(testRoute.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Route in Elasticsearch
        verify(mockRouteSearchRepository, times(1)).save(testRoute);
    }

    @Test
    @Transactional
    public void updateNonExistingRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Create the Route

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRouteMockMvc.perform(put("/api/routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(route)))
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Route in Elasticsearch
        verify(mockRouteSearchRepository, times(0)).save(route);
    }

    @Test
    @Transactional
    public void deleteRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeDelete = routeRepository.findAll().size();

        // Delete the route
        restRouteMockMvc.perform(delete("/api/routes/{id}", route.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Route in Elasticsearch
        verify(mockRouteSearchRepository, times(1)).deleteById(route.getId());
    }

    @Test
    @Transactional
    public void searchRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);
        when(mockRouteSearchRepository.search(queryStringQuery("id:" + route.getId())))
            .thenReturn(Collections.singletonList(route));
        // Search the route
        restRouteMockMvc.perform(get("/api/_search/routes?query=id:" + route.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(route.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].journee").value(hasItem(DEFAULT_JOURNEE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].datedepCom").value(hasItem(DEFAULT_DATEDEP_COM.toString())))
            .andExpect(jsonPath("$.[*].dateRCom").value(hasItem(DEFAULT_DATE_R_COM.toString())))
            .andExpect(jsonPath("$.[*].heureDepCom").value(hasItem(DEFAULT_HEURE_DEP_COM)))
            .andExpect(jsonPath("$.[*].heureFinCom").value(hasItem(DEFAULT_HEURE_FIN_COM)))
            .andExpect(jsonPath("$.[*].heureDepLiv").value(hasItem(DEFAULT_HEURE_DEP_LIV)))
            .andExpect(jsonPath("$.[*].heureFinLiv").value(hasItem(DEFAULT_HEURE_FIN_LIV)))
            .andExpect(jsonPath("$.[*].journeeLiv").value(hasItem(DEFAULT_JOURNEE_LIV)))
            .andExpect(jsonPath("$.[*].datedepLiv").value(hasItem(DEFAULT_DATEDEP_LIV.toString())))
            .andExpect(jsonPath("$.[*].dateRLiv").value(hasItem(DEFAULT_DATE_R_LIV.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Route.class);
        Route route1 = new Route();
        route1.setId(1L);
        Route route2 = new Route();
        route2.setId(route1.getId());
        assertThat(route1).isEqualTo(route2);
        route2.setId(2L);
        assertThat(route1).isNotEqualTo(route2);
        route1.setId(null);
        assertThat(route1).isNotEqualTo(route2);
    }
}
