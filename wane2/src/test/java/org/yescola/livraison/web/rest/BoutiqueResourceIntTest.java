package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Boutique;
import org.yescola.livraison.repository.BoutiqueRepository;
import org.yescola.livraison.repository.search.BoutiqueSearchRepository;
import org.yescola.livraison.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.yescola.livraison.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.yescola.livraison.domain.enumeration.Civilite;
/**
 * Test class for the BoutiqueResource REST controller.
 *
 * @see BoutiqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class BoutiqueResourceIntTest {

    private static final String DEFAULT_NOM_BOUTIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_BOUTIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Civilite DEFAULT_CIVILITE = Civilite.M;
    private static final Civilite UPDATED_CIVILITE = Civilite.Mme;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELPROPRIETAIRE = "AAAAAAAAAA";
    private static final String UPDATED_TELPROPRIETAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTITUDE = "AAAAAAAAAA";
    private static final String UPDATED_ALTITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODICITE = "AAAAAAAAAA";
    private static final String UPDATED_PERIODICITE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DERNIER_COM = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DERNIER_COM = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DERNIER_LIV = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DERNIER_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_SOLDE = "AAAAAAAAAA";
    private static final String UPDATED_SOLDE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMANDE_EN_COURS = "AAAAAAAAAA";
    private static final String UPDATED_COMMANDE_EN_COURS = "BBBBBBBBBB";

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @Mock
    private BoutiqueRepository boutiqueRepositoryMock;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.BoutiqueSearchRepositoryMockConfiguration
     */
    @Autowired
    private BoutiqueSearchRepository mockBoutiqueSearchRepository;

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

    private MockMvc restBoutiqueMockMvc;

    private Boutique boutique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoutiqueResource boutiqueResource = new BoutiqueResource(boutiqueRepository, mockBoutiqueSearchRepository);
        this.restBoutiqueMockMvc = MockMvcBuilders.standaloneSetup(boutiqueResource)
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
    public static Boutique createEntity(EntityManager em) {
        Boutique boutique = new Boutique()
            .nomBoutique(DEFAULT_NOM_BOUTIQUE)
            .prenom(DEFAULT_PRENOM)
            .civilite(DEFAULT_CIVILITE)
            .nom(DEFAULT_NOM)
            .telephone(DEFAULT_TELEPHONE)
            .telproprietaire(DEFAULT_TELPROPRIETAIRE)
            .longitude(DEFAULT_LONGITUDE)
            .altitude(DEFAULT_ALTITUDE)
            .etat(DEFAULT_ETAT)
            .periodicite(DEFAULT_PERIODICITE)
            .dateDernierCom(DEFAULT_DATE_DERNIER_COM)
            .dateDernierLiv(DEFAULT_DATE_DERNIER_LIV)
            .solde(DEFAULT_SOLDE)
            .commandeEnCours(DEFAULT_COMMANDE_EN_COURS);
        return boutique;
    }

    @Before
    public void initTest() {
        boutique = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoutique() throws Exception {
        int databaseSizeBeforeCreate = boutiqueRepository.findAll().size();

        // Create the Boutique
        restBoutiqueMockMvc.perform(post("/api/boutiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boutique)))
            .andExpect(status().isCreated());

        // Validate the Boutique in the database
        List<Boutique> boutiqueList = boutiqueRepository.findAll();
        assertThat(boutiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Boutique testBoutique = boutiqueList.get(boutiqueList.size() - 1);
        assertThat(testBoutique.getNomBoutique()).isEqualTo(DEFAULT_NOM_BOUTIQUE);
        assertThat(testBoutique.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testBoutique.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testBoutique.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testBoutique.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testBoutique.getTelproprietaire()).isEqualTo(DEFAULT_TELPROPRIETAIRE);
        assertThat(testBoutique.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testBoutique.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testBoutique.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testBoutique.getPeriodicite()).isEqualTo(DEFAULT_PERIODICITE);
        assertThat(testBoutique.getDateDernierCom()).isEqualTo(DEFAULT_DATE_DERNIER_COM);
        assertThat(testBoutique.getDateDernierLiv()).isEqualTo(DEFAULT_DATE_DERNIER_LIV);
        assertThat(testBoutique.getSolde()).isEqualTo(DEFAULT_SOLDE);
        assertThat(testBoutique.getCommandeEnCours()).isEqualTo(DEFAULT_COMMANDE_EN_COURS);

        // Validate the Boutique in Elasticsearch
        verify(mockBoutiqueSearchRepository, times(1)).save(testBoutique);
    }

    @Test
    @Transactional
    public void createBoutiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boutiqueRepository.findAll().size();

        // Create the Boutique with an existing ID
        boutique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoutiqueMockMvc.perform(post("/api/boutiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boutique)))
            .andExpect(status().isBadRequest());

        // Validate the Boutique in the database
        List<Boutique> boutiqueList = boutiqueRepository.findAll();
        assertThat(boutiqueList).hasSize(databaseSizeBeforeCreate);

        // Validate the Boutique in Elasticsearch
        verify(mockBoutiqueSearchRepository, times(0)).save(boutique);
    }

    @Test
    @Transactional
    public void getAllBoutiques() throws Exception {
        // Initialize the database
        boutiqueRepository.saveAndFlush(boutique);

        // Get all the boutiqueList
        restBoutiqueMockMvc.perform(get("/api/boutiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boutique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomBoutique").value(hasItem(DEFAULT_NOM_BOUTIQUE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].telproprietaire").value(hasItem(DEFAULT_TELPROPRIETAIRE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE.toString())))
            .andExpect(jsonPath("$.[*].dateDernierCom").value(hasItem(DEFAULT_DATE_DERNIER_COM.toString())))
            .andExpect(jsonPath("$.[*].dateDernierLiv").value(hasItem(DEFAULT_DATE_DERNIER_LIV.toString())))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.toString())))
            .andExpect(jsonPath("$.[*].commandeEnCours").value(hasItem(DEFAULT_COMMANDE_EN_COURS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBoutiquesWithEagerRelationshipsIsEnabled() throws Exception {
        BoutiqueResource boutiqueResource = new BoutiqueResource(boutiqueRepositoryMock, mockBoutiqueSearchRepository);
        when(boutiqueRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBoutiqueMockMvc = MockMvcBuilders.standaloneSetup(boutiqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBoutiqueMockMvc.perform(get("/api/boutiques?eagerload=true"))
        .andExpect(status().isOk());

        verify(boutiqueRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBoutiquesWithEagerRelationshipsIsNotEnabled() throws Exception {
        BoutiqueResource boutiqueResource = new BoutiqueResource(boutiqueRepositoryMock, mockBoutiqueSearchRepository);
            when(boutiqueRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBoutiqueMockMvc = MockMvcBuilders.standaloneSetup(boutiqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBoutiqueMockMvc.perform(get("/api/boutiques?eagerload=true"))
        .andExpect(status().isOk());

            verify(boutiqueRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBoutique() throws Exception {
        // Initialize the database
        boutiqueRepository.saveAndFlush(boutique);

        // Get the boutique
        restBoutiqueMockMvc.perform(get("/api/boutiques/{id}", boutique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boutique.getId().intValue()))
            .andExpect(jsonPath("$.nomBoutique").value(DEFAULT_NOM_BOUTIQUE.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.telproprietaire").value(DEFAULT_TELPROPRIETAIRE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.periodicite").value(DEFAULT_PERIODICITE.toString()))
            .andExpect(jsonPath("$.dateDernierCom").value(DEFAULT_DATE_DERNIER_COM.toString()))
            .andExpect(jsonPath("$.dateDernierLiv").value(DEFAULT_DATE_DERNIER_LIV.toString()))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.toString()))
            .andExpect(jsonPath("$.commandeEnCours").value(DEFAULT_COMMANDE_EN_COURS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoutique() throws Exception {
        // Get the boutique
        restBoutiqueMockMvc.perform(get("/api/boutiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoutique() throws Exception {
        // Initialize the database
        boutiqueRepository.saveAndFlush(boutique);

        int databaseSizeBeforeUpdate = boutiqueRepository.findAll().size();

        // Update the boutique
        Boutique updatedBoutique = boutiqueRepository.findById(boutique.getId()).get();
        // Disconnect from session so that the updates on updatedBoutique are not directly saved in db
        em.detach(updatedBoutique);
        updatedBoutique
            .nomBoutique(UPDATED_NOM_BOUTIQUE)
            .prenom(UPDATED_PRENOM)
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .telephone(UPDATED_TELEPHONE)
            .telproprietaire(UPDATED_TELPROPRIETAIRE)
            .longitude(UPDATED_LONGITUDE)
            .altitude(UPDATED_ALTITUDE)
            .etat(UPDATED_ETAT)
            .periodicite(UPDATED_PERIODICITE)
            .dateDernierCom(UPDATED_DATE_DERNIER_COM)
            .dateDernierLiv(UPDATED_DATE_DERNIER_LIV)
            .solde(UPDATED_SOLDE)
            .commandeEnCours(UPDATED_COMMANDE_EN_COURS);

        restBoutiqueMockMvc.perform(put("/api/boutiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoutique)))
            .andExpect(status().isOk());

        // Validate the Boutique in the database
        List<Boutique> boutiqueList = boutiqueRepository.findAll();
        assertThat(boutiqueList).hasSize(databaseSizeBeforeUpdate);
        Boutique testBoutique = boutiqueList.get(boutiqueList.size() - 1);
        assertThat(testBoutique.getNomBoutique()).isEqualTo(UPDATED_NOM_BOUTIQUE);
        assertThat(testBoutique.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testBoutique.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testBoutique.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBoutique.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testBoutique.getTelproprietaire()).isEqualTo(UPDATED_TELPROPRIETAIRE);
        assertThat(testBoutique.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testBoutique.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testBoutique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testBoutique.getPeriodicite()).isEqualTo(UPDATED_PERIODICITE);
        assertThat(testBoutique.getDateDernierCom()).isEqualTo(UPDATED_DATE_DERNIER_COM);
        assertThat(testBoutique.getDateDernierLiv()).isEqualTo(UPDATED_DATE_DERNIER_LIV);
        assertThat(testBoutique.getSolde()).isEqualTo(UPDATED_SOLDE);
        assertThat(testBoutique.getCommandeEnCours()).isEqualTo(UPDATED_COMMANDE_EN_COURS);

        // Validate the Boutique in Elasticsearch
        verify(mockBoutiqueSearchRepository, times(1)).save(testBoutique);
    }

    @Test
    @Transactional
    public void updateNonExistingBoutique() throws Exception {
        int databaseSizeBeforeUpdate = boutiqueRepository.findAll().size();

        // Create the Boutique

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoutiqueMockMvc.perform(put("/api/boutiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boutique)))
            .andExpect(status().isBadRequest());

        // Validate the Boutique in the database
        List<Boutique> boutiqueList = boutiqueRepository.findAll();
        assertThat(boutiqueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Boutique in Elasticsearch
        verify(mockBoutiqueSearchRepository, times(0)).save(boutique);
    }

    @Test
    @Transactional
    public void deleteBoutique() throws Exception {
        // Initialize the database
        boutiqueRepository.saveAndFlush(boutique);

        int databaseSizeBeforeDelete = boutiqueRepository.findAll().size();

        // Delete the boutique
        restBoutiqueMockMvc.perform(delete("/api/boutiques/{id}", boutique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Boutique> boutiqueList = boutiqueRepository.findAll();
        assertThat(boutiqueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Boutique in Elasticsearch
        verify(mockBoutiqueSearchRepository, times(1)).deleteById(boutique.getId());
    }

    @Test
    @Transactional
    public void searchBoutique() throws Exception {
        // Initialize the database
        boutiqueRepository.saveAndFlush(boutique);
        when(mockBoutiqueSearchRepository.search(queryStringQuery("id:" + boutique.getId())))
            .thenReturn(Collections.singletonList(boutique));
        // Search the boutique
        restBoutiqueMockMvc.perform(get("/api/_search/boutiques?query=id:" + boutique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boutique.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomBoutique").value(hasItem(DEFAULT_NOM_BOUTIQUE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].telproprietaire").value(hasItem(DEFAULT_TELPROPRIETAIRE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE)))
            .andExpect(jsonPath("$.[*].dateDernierCom").value(hasItem(DEFAULT_DATE_DERNIER_COM)))
            .andExpect(jsonPath("$.[*].dateDernierLiv").value(hasItem(DEFAULT_DATE_DERNIER_LIV)))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE)))
            .andExpect(jsonPath("$.[*].commandeEnCours").value(hasItem(DEFAULT_COMMANDE_EN_COURS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boutique.class);
        Boutique boutique1 = new Boutique();
        boutique1.setId(1L);
        Boutique boutique2 = new Boutique();
        boutique2.setId(boutique1.getId());
        assertThat(boutique1).isEqualTo(boutique2);
        boutique2.setId(2L);
        assertThat(boutique1).isNotEqualTo(boutique2);
        boutique1.setId(null);
        assertThat(boutique1).isNotEqualTo(boutique2);
    }
}
