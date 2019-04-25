package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Reglement;
import org.yescola.livraison.repository.ReglementRepository;
import org.yescola.livraison.repository.search.ReglementSearchRepository;
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
 * Test class for the ReglementResource REST controller.
 *
 * @see ReglementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class ReglementResourceIntTest {

    private static final LocalDate DEFAULT_DATE_REG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REG = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MONTANT_PAYER = "AAAAAAAAAA";
    private static final String UPDATED_MONTANT_PAYER = "BBBBBBBBBB";

    @Autowired
    private ReglementRepository reglementRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.ReglementSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReglementSearchRepository mockReglementSearchRepository;

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

    private MockMvc restReglementMockMvc;

    private Reglement reglement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReglementResource reglementResource = new ReglementResource(reglementRepository, mockReglementSearchRepository);
        this.restReglementMockMvc = MockMvcBuilders.standaloneSetup(reglementResource)
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
    public static Reglement createEntity(EntityManager em) {
        Reglement reglement = new Reglement()
            .dateReg(DEFAULT_DATE_REG)
            .montantPayer(DEFAULT_MONTANT_PAYER);
        return reglement;
    }

    @Before
    public void initTest() {
        reglement = createEntity(em);
    }

    @Test
    @Transactional
    public void createReglement() throws Exception {
        int databaseSizeBeforeCreate = reglementRepository.findAll().size();

        // Create the Reglement
        restReglementMockMvc.perform(post("/api/reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglement)))
            .andExpect(status().isCreated());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeCreate + 1);
        Reglement testReglement = reglementList.get(reglementList.size() - 1);
        assertThat(testReglement.getDateReg()).isEqualTo(DEFAULT_DATE_REG);
        assertThat(testReglement.getMontantPayer()).isEqualTo(DEFAULT_MONTANT_PAYER);

        // Validate the Reglement in Elasticsearch
        verify(mockReglementSearchRepository, times(1)).save(testReglement);
    }

    @Test
    @Transactional
    public void createReglementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reglementRepository.findAll().size();

        // Create the Reglement with an existing ID
        reglement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReglementMockMvc.perform(post("/api/reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglement)))
            .andExpect(status().isBadRequest());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeCreate);

        // Validate the Reglement in Elasticsearch
        verify(mockReglementSearchRepository, times(0)).save(reglement);
    }

    @Test
    @Transactional
    public void getAllReglements() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        // Get all the reglementList
        restReglementMockMvc.perform(get("/api/reglements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reglement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateReg").value(hasItem(DEFAULT_DATE_REG.toString())))
            .andExpect(jsonPath("$.[*].montantPayer").value(hasItem(DEFAULT_MONTANT_PAYER.toString())));
    }
    
    @Test
    @Transactional
    public void getReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        // Get the reglement
        restReglementMockMvc.perform(get("/api/reglements/{id}", reglement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reglement.getId().intValue()))
            .andExpect(jsonPath("$.dateReg").value(DEFAULT_DATE_REG.toString()))
            .andExpect(jsonPath("$.montantPayer").value(DEFAULT_MONTANT_PAYER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReglement() throws Exception {
        // Get the reglement
        restReglementMockMvc.perform(get("/api/reglements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        int databaseSizeBeforeUpdate = reglementRepository.findAll().size();

        // Update the reglement
        Reglement updatedReglement = reglementRepository.findById(reglement.getId()).get();
        // Disconnect from session so that the updates on updatedReglement are not directly saved in db
        em.detach(updatedReglement);
        updatedReglement
            .dateReg(UPDATED_DATE_REG)
            .montantPayer(UPDATED_MONTANT_PAYER);

        restReglementMockMvc.perform(put("/api/reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReglement)))
            .andExpect(status().isOk());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeUpdate);
        Reglement testReglement = reglementList.get(reglementList.size() - 1);
        assertThat(testReglement.getDateReg()).isEqualTo(UPDATED_DATE_REG);
        assertThat(testReglement.getMontantPayer()).isEqualTo(UPDATED_MONTANT_PAYER);

        // Validate the Reglement in Elasticsearch
        verify(mockReglementSearchRepository, times(1)).save(testReglement);
    }

    @Test
    @Transactional
    public void updateNonExistingReglement() throws Exception {
        int databaseSizeBeforeUpdate = reglementRepository.findAll().size();

        // Create the Reglement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReglementMockMvc.perform(put("/api/reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reglement)))
            .andExpect(status().isBadRequest());

        // Validate the Reglement in the database
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Reglement in Elasticsearch
        verify(mockReglementSearchRepository, times(0)).save(reglement);
    }

    @Test
    @Transactional
    public void deleteReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);

        int databaseSizeBeforeDelete = reglementRepository.findAll().size();

        // Delete the reglement
        restReglementMockMvc.perform(delete("/api/reglements/{id}", reglement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reglement> reglementList = reglementRepository.findAll();
        assertThat(reglementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Reglement in Elasticsearch
        verify(mockReglementSearchRepository, times(1)).deleteById(reglement.getId());
    }

    @Test
    @Transactional
    public void searchReglement() throws Exception {
        // Initialize the database
        reglementRepository.saveAndFlush(reglement);
        when(mockReglementSearchRepository.search(queryStringQuery("id:" + reglement.getId())))
            .thenReturn(Collections.singletonList(reglement));
        // Search the reglement
        restReglementMockMvc.perform(get("/api/_search/reglements?query=id:" + reglement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reglement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateReg").value(hasItem(DEFAULT_DATE_REG.toString())))
            .andExpect(jsonPath("$.[*].montantPayer").value(hasItem(DEFAULT_MONTANT_PAYER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reglement.class);
        Reglement reglement1 = new Reglement();
        reglement1.setId(1L);
        Reglement reglement2 = new Reglement();
        reglement2.setId(reglement1.getId());
        assertThat(reglement1).isEqualTo(reglement2);
        reglement2.setId(2L);
        assertThat(reglement1).isNotEqualTo(reglement2);
        reglement1.setId(null);
        assertThat(reglement1).isNotEqualTo(reglement2);
    }
}
