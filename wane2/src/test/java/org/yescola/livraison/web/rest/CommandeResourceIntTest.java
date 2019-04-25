package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Commande;
import org.yescola.livraison.repository.CommandeRepository;
import org.yescola.livraison.repository.search.CommandeSearchRepository;
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
 * Test class for the CommandeResource REST controller.
 *
 * @see CommandeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class CommandeResourceIntTest {

    private static final String DEFAULT_NUM_COM = "AAAAAAAAAA";
    private static final String UPDATED_NUM_COM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_COM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_MONTANT_LIV = "AAAAAAAAAA";
    private static final String UPDATED_MONTANT_LIV = "BBBBBBBBBB";

    private static final String DEFAULT_MONTANT_REST = "AAAAAAAAAA";
    private static final String UPDATED_MONTANT_REST = "BBBBBBBBBB";

    @Autowired
    private CommandeRepository commandeRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.CommandeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommandeSearchRepository mockCommandeSearchRepository;

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

    private MockMvc restCommandeMockMvc;

    private Commande commande;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandeResource commandeResource = new CommandeResource(commandeRepository, mockCommandeSearchRepository);
        this.restCommandeMockMvc = MockMvcBuilders.standaloneSetup(commandeResource)
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
    public static Commande createEntity(EntityManager em) {
        Commande commande = new Commande()
            .numCom(DEFAULT_NUM_COM)
            .dateCom(DEFAULT_DATE_COM)
            .etat(DEFAULT_ETAT)
            .montantLiv(DEFAULT_MONTANT_LIV)
            .montantRest(DEFAULT_MONTANT_REST);
        return commande;
    }

    @Before
    public void initTest() {
        commande = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommande() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isCreated());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate + 1);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getNumCom()).isEqualTo(DEFAULT_NUM_COM);
        assertThat(testCommande.getDateCom()).isEqualTo(DEFAULT_DATE_COM);
        assertThat(testCommande.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testCommande.getMontantLiv()).isEqualTo(DEFAULT_MONTANT_LIV);
        assertThat(testCommande.getMontantRest()).isEqualTo(DEFAULT_MONTANT_REST);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(1)).save(testCommande);
    }

    @Test
    @Transactional
    public void createCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande with an existing ID
        commande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(0)).save(commande);
    }

    @Test
    @Transactional
    public void getAllCommandes() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get all the commandeList
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].numCom").value(hasItem(DEFAULT_NUM_COM.toString())))
            .andExpect(jsonPath("$.[*].dateCom").value(hasItem(DEFAULT_DATE_COM.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].montantLiv").value(hasItem(DEFAULT_MONTANT_LIV.toString())))
            .andExpect(jsonPath("$.[*].montantRest").value(hasItem(DEFAULT_MONTANT_REST.toString())));
    }
    
    @Test
    @Transactional
    public void getCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commande.getId().intValue()))
            .andExpect(jsonPath("$.numCom").value(DEFAULT_NUM_COM.toString()))
            .andExpect(jsonPath("$.dateCom").value(DEFAULT_DATE_COM.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.montantLiv").value(DEFAULT_MONTANT_LIV.toString()))
            .andExpect(jsonPath("$.montantRest").value(DEFAULT_MONTANT_REST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommande() throws Exception {
        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Update the commande
        Commande updatedCommande = commandeRepository.findById(commande.getId()).get();
        // Disconnect from session so that the updates on updatedCommande are not directly saved in db
        em.detach(updatedCommande);
        updatedCommande
            .numCom(UPDATED_NUM_COM)
            .dateCom(UPDATED_DATE_COM)
            .etat(UPDATED_ETAT)
            .montantLiv(UPDATED_MONTANT_LIV)
            .montantRest(UPDATED_MONTANT_REST);

        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommande)))
            .andExpect(status().isOk());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getNumCom()).isEqualTo(UPDATED_NUM_COM);
        assertThat(testCommande.getDateCom()).isEqualTo(UPDATED_DATE_COM);
        assertThat(testCommande.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testCommande.getMontantLiv()).isEqualTo(UPDATED_MONTANT_LIV);
        assertThat(testCommande.getMontantRest()).isEqualTo(UPDATED_MONTANT_REST);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(1)).save(testCommande);
    }

    @Test
    @Transactional
    public void updateNonExistingCommande() throws Exception {
        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Create the Commande

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(0)).save(commande);
    }

    @Test
    @Transactional
    public void deleteCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);

        int databaseSizeBeforeDelete = commandeRepository.findAll().size();

        // Delete the commande
        restCommandeMockMvc.perform(delete("/api/commandes/{id}", commande.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Commande in Elasticsearch
        verify(mockCommandeSearchRepository, times(1)).deleteById(commande.getId());
    }

    @Test
    @Transactional
    public void searchCommande() throws Exception {
        // Initialize the database
        commandeRepository.saveAndFlush(commande);
        when(mockCommandeSearchRepository.search(queryStringQuery("id:" + commande.getId())))
            .thenReturn(Collections.singletonList(commande));
        // Search the commande
        restCommandeMockMvc.perform(get("/api/_search/commandes?query=id:" + commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId().intValue())))
            .andExpect(jsonPath("$.[*].numCom").value(hasItem(DEFAULT_NUM_COM)))
            .andExpect(jsonPath("$.[*].dateCom").value(hasItem(DEFAULT_DATE_COM.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].montantLiv").value(hasItem(DEFAULT_MONTANT_LIV)))
            .andExpect(jsonPath("$.[*].montantRest").value(hasItem(DEFAULT_MONTANT_REST)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commande.class);
        Commande commande1 = new Commande();
        commande1.setId(1L);
        Commande commande2 = new Commande();
        commande2.setId(commande1.getId());
        assertThat(commande1).isEqualTo(commande2);
        commande2.setId(2L);
        assertThat(commande1).isNotEqualTo(commande2);
        commande1.setId(null);
        assertThat(commande1).isNotEqualTo(commande2);
    }
}
