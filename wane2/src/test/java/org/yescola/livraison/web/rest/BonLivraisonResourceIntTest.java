package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.BonLivraison;
import org.yescola.livraison.repository.BonLivraisonRepository;
import org.yescola.livraison.repository.search.BonLivraisonSearchRepository;
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
 * Test class for the BonLivraisonResource REST controller.
 *
 * @see BonLivraisonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class BonLivraisonResourceIntTest {

    private static final LocalDate DEFAULT_DATE_BL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BonLivraisonRepository bonLivraisonRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.BonLivraisonSearchRepositoryMockConfiguration
     */
    @Autowired
    private BonLivraisonSearchRepository mockBonLivraisonSearchRepository;

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

    private MockMvc restBonLivraisonMockMvc;

    private BonLivraison bonLivraison;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BonLivraisonResource bonLivraisonResource = new BonLivraisonResource(bonLivraisonRepository, mockBonLivraisonSearchRepository);
        this.restBonLivraisonMockMvc = MockMvcBuilders.standaloneSetup(bonLivraisonResource)
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
    public static BonLivraison createEntity(EntityManager em) {
        BonLivraison bonLivraison = new BonLivraison()
            .dateBl(DEFAULT_DATE_BL);
        return bonLivraison;
    }

    @Before
    public void initTest() {
        bonLivraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonLivraison() throws Exception {
        int databaseSizeBeforeCreate = bonLivraisonRepository.findAll().size();

        // Create the BonLivraison
        restBonLivraisonMockMvc.perform(post("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonLivraison)))
            .andExpect(status().isCreated());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeCreate + 1);
        BonLivraison testBonLivraison = bonLivraisonList.get(bonLivraisonList.size() - 1);
        assertThat(testBonLivraison.getDateBl()).isEqualTo(DEFAULT_DATE_BL);

        // Validate the BonLivraison in Elasticsearch
        verify(mockBonLivraisonSearchRepository, times(1)).save(testBonLivraison);
    }

    @Test
    @Transactional
    public void createBonLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonLivraisonRepository.findAll().size();

        // Create the BonLivraison with an existing ID
        bonLivraison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonLivraisonMockMvc.perform(post("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeCreate);

        // Validate the BonLivraison in Elasticsearch
        verify(mockBonLivraisonSearchRepository, times(0)).save(bonLivraison);
    }

    @Test
    @Transactional
    public void getAllBonLivraisons() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);

        // Get all the bonLivraisonList
        restBonLivraisonMockMvc.perform(get("/api/bon-livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateBl").value(hasItem(DEFAULT_DATE_BL.toString())));
    }
    
    @Test
    @Transactional
    public void getBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);

        // Get the bonLivraison
        restBonLivraisonMockMvc.perform(get("/api/bon-livraisons/{id}", bonLivraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bonLivraison.getId().intValue()))
            .andExpect(jsonPath("$.dateBl").value(DEFAULT_DATE_BL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBonLivraison() throws Exception {
        // Get the bonLivraison
        restBonLivraisonMockMvc.perform(get("/api/bon-livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);

        int databaseSizeBeforeUpdate = bonLivraisonRepository.findAll().size();

        // Update the bonLivraison
        BonLivraison updatedBonLivraison = bonLivraisonRepository.findById(bonLivraison.getId()).get();
        // Disconnect from session so that the updates on updatedBonLivraison are not directly saved in db
        em.detach(updatedBonLivraison);
        updatedBonLivraison
            .dateBl(UPDATED_DATE_BL);

        restBonLivraisonMockMvc.perform(put("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBonLivraison)))
            .andExpect(status().isOk());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeUpdate);
        BonLivraison testBonLivraison = bonLivraisonList.get(bonLivraisonList.size() - 1);
        assertThat(testBonLivraison.getDateBl()).isEqualTo(UPDATED_DATE_BL);

        // Validate the BonLivraison in Elasticsearch
        verify(mockBonLivraisonSearchRepository, times(1)).save(testBonLivraison);
    }

    @Test
    @Transactional
    public void updateNonExistingBonLivraison() throws Exception {
        int databaseSizeBeforeUpdate = bonLivraisonRepository.findAll().size();

        // Create the BonLivraison

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonLivraisonMockMvc.perform(put("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BonLivraison in Elasticsearch
        verify(mockBonLivraisonSearchRepository, times(0)).save(bonLivraison);
    }

    @Test
    @Transactional
    public void deleteBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);

        int databaseSizeBeforeDelete = bonLivraisonRepository.findAll().size();

        // Delete the bonLivraison
        restBonLivraisonMockMvc.perform(delete("/api/bon-livraisons/{id}", bonLivraison.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BonLivraison in Elasticsearch
        verify(mockBonLivraisonSearchRepository, times(1)).deleteById(bonLivraison.getId());
    }

    @Test
    @Transactional
    public void searchBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);
        when(mockBonLivraisonSearchRepository.search(queryStringQuery("id:" + bonLivraison.getId())))
            .thenReturn(Collections.singletonList(bonLivraison));
        // Search the bonLivraison
        restBonLivraisonMockMvc.perform(get("/api/_search/bon-livraisons?query=id:" + bonLivraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateBl").value(hasItem(DEFAULT_DATE_BL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonLivraison.class);
        BonLivraison bonLivraison1 = new BonLivraison();
        bonLivraison1.setId(1L);
        BonLivraison bonLivraison2 = new BonLivraison();
        bonLivraison2.setId(bonLivraison1.getId());
        assertThat(bonLivraison1).isEqualTo(bonLivraison2);
        bonLivraison2.setId(2L);
        assertThat(bonLivraison1).isNotEqualTo(bonLivraison2);
        bonLivraison1.setId(null);
        assertThat(bonLivraison1).isNotEqualTo(bonLivraison2);
    }
}
