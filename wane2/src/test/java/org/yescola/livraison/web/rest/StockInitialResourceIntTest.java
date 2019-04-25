package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.StockInitial;
import org.yescola.livraison.repository.StockInitialRepository;
import org.yescola.livraison.repository.search.StockInitialSearchRepository;
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
 * Test class for the StockInitialResource REST controller.
 *
 * @see StockInitialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class StockInitialResourceIntTest {

    private static final String DEFAULT_QTE_STOCK_INIT = "AAAAAAAAAA";
    private static final String UPDATED_QTE_STOCK_INIT = "BBBBBBBBBB";

    private static final String DEFAULT_QTE_STOCK_ENCOURS = "AAAAAAAAAA";
    private static final String UPDATED_QTE_STOCK_ENCOURS = "BBBBBBBBBB";

    @Autowired
    private StockInitialRepository stockInitialRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.StockInitialSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockInitialSearchRepository mockStockInitialSearchRepository;

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

    private MockMvc restStockInitialMockMvc;

    private StockInitial stockInitial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockInitialResource stockInitialResource = new StockInitialResource(stockInitialRepository, mockStockInitialSearchRepository);
        this.restStockInitialMockMvc = MockMvcBuilders.standaloneSetup(stockInitialResource)
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
    public static StockInitial createEntity(EntityManager em) {
        StockInitial stockInitial = new StockInitial()
            .qteStockInit(DEFAULT_QTE_STOCK_INIT)
            .qteStockEncours(DEFAULT_QTE_STOCK_ENCOURS);
        return stockInitial;
    }

    @Before
    public void initTest() {
        stockInitial = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockInitial() throws Exception {
        int databaseSizeBeforeCreate = stockInitialRepository.findAll().size();

        // Create the StockInitial
        restStockInitialMockMvc.perform(post("/api/stock-initials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInitial)))
            .andExpect(status().isCreated());

        // Validate the StockInitial in the database
        List<StockInitial> stockInitialList = stockInitialRepository.findAll();
        assertThat(stockInitialList).hasSize(databaseSizeBeforeCreate + 1);
        StockInitial testStockInitial = stockInitialList.get(stockInitialList.size() - 1);
        assertThat(testStockInitial.getQteStockInit()).isEqualTo(DEFAULT_QTE_STOCK_INIT);
        assertThat(testStockInitial.getQteStockEncours()).isEqualTo(DEFAULT_QTE_STOCK_ENCOURS);

        // Validate the StockInitial in Elasticsearch
        verify(mockStockInitialSearchRepository, times(1)).save(testStockInitial);
    }

    @Test
    @Transactional
    public void createStockInitialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockInitialRepository.findAll().size();

        // Create the StockInitial with an existing ID
        stockInitial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockInitialMockMvc.perform(post("/api/stock-initials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInitial)))
            .andExpect(status().isBadRequest());

        // Validate the StockInitial in the database
        List<StockInitial> stockInitialList = stockInitialRepository.findAll();
        assertThat(stockInitialList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockInitial in Elasticsearch
        verify(mockStockInitialSearchRepository, times(0)).save(stockInitial);
    }

    @Test
    @Transactional
    public void getAllStockInitials() throws Exception {
        // Initialize the database
        stockInitialRepository.saveAndFlush(stockInitial);

        // Get all the stockInitialList
        restStockInitialMockMvc.perform(get("/api/stock-initials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInitial.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteStockInit").value(hasItem(DEFAULT_QTE_STOCK_INIT.toString())))
            .andExpect(jsonPath("$.[*].qteStockEncours").value(hasItem(DEFAULT_QTE_STOCK_ENCOURS.toString())));
    }
    
    @Test
    @Transactional
    public void getStockInitial() throws Exception {
        // Initialize the database
        stockInitialRepository.saveAndFlush(stockInitial);

        // Get the stockInitial
        restStockInitialMockMvc.perform(get("/api/stock-initials/{id}", stockInitial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockInitial.getId().intValue()))
            .andExpect(jsonPath("$.qteStockInit").value(DEFAULT_QTE_STOCK_INIT.toString()))
            .andExpect(jsonPath("$.qteStockEncours").value(DEFAULT_QTE_STOCK_ENCOURS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockInitial() throws Exception {
        // Get the stockInitial
        restStockInitialMockMvc.perform(get("/api/stock-initials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockInitial() throws Exception {
        // Initialize the database
        stockInitialRepository.saveAndFlush(stockInitial);

        int databaseSizeBeforeUpdate = stockInitialRepository.findAll().size();

        // Update the stockInitial
        StockInitial updatedStockInitial = stockInitialRepository.findById(stockInitial.getId()).get();
        // Disconnect from session so that the updates on updatedStockInitial are not directly saved in db
        em.detach(updatedStockInitial);
        updatedStockInitial
            .qteStockInit(UPDATED_QTE_STOCK_INIT)
            .qteStockEncours(UPDATED_QTE_STOCK_ENCOURS);

        restStockInitialMockMvc.perform(put("/api/stock-initials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockInitial)))
            .andExpect(status().isOk());

        // Validate the StockInitial in the database
        List<StockInitial> stockInitialList = stockInitialRepository.findAll();
        assertThat(stockInitialList).hasSize(databaseSizeBeforeUpdate);
        StockInitial testStockInitial = stockInitialList.get(stockInitialList.size() - 1);
        assertThat(testStockInitial.getQteStockInit()).isEqualTo(UPDATED_QTE_STOCK_INIT);
        assertThat(testStockInitial.getQteStockEncours()).isEqualTo(UPDATED_QTE_STOCK_ENCOURS);

        // Validate the StockInitial in Elasticsearch
        verify(mockStockInitialSearchRepository, times(1)).save(testStockInitial);
    }

    @Test
    @Transactional
    public void updateNonExistingStockInitial() throws Exception {
        int databaseSizeBeforeUpdate = stockInitialRepository.findAll().size();

        // Create the StockInitial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockInitialMockMvc.perform(put("/api/stock-initials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInitial)))
            .andExpect(status().isBadRequest());

        // Validate the StockInitial in the database
        List<StockInitial> stockInitialList = stockInitialRepository.findAll();
        assertThat(stockInitialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockInitial in Elasticsearch
        verify(mockStockInitialSearchRepository, times(0)).save(stockInitial);
    }

    @Test
    @Transactional
    public void deleteStockInitial() throws Exception {
        // Initialize the database
        stockInitialRepository.saveAndFlush(stockInitial);

        int databaseSizeBeforeDelete = stockInitialRepository.findAll().size();

        // Delete the stockInitial
        restStockInitialMockMvc.perform(delete("/api/stock-initials/{id}", stockInitial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockInitial> stockInitialList = stockInitialRepository.findAll();
        assertThat(stockInitialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockInitial in Elasticsearch
        verify(mockStockInitialSearchRepository, times(1)).deleteById(stockInitial.getId());
    }

    @Test
    @Transactional
    public void searchStockInitial() throws Exception {
        // Initialize the database
        stockInitialRepository.saveAndFlush(stockInitial);
        when(mockStockInitialSearchRepository.search(queryStringQuery("id:" + stockInitial.getId())))
            .thenReturn(Collections.singletonList(stockInitial));
        // Search the stockInitial
        restStockInitialMockMvc.perform(get("/api/_search/stock-initials?query=id:" + stockInitial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInitial.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteStockInit").value(hasItem(DEFAULT_QTE_STOCK_INIT)))
            .andExpect(jsonPath("$.[*].qteStockEncours").value(hasItem(DEFAULT_QTE_STOCK_ENCOURS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockInitial.class);
        StockInitial stockInitial1 = new StockInitial();
        stockInitial1.setId(1L);
        StockInitial stockInitial2 = new StockInitial();
        stockInitial2.setId(stockInitial1.getId());
        assertThat(stockInitial1).isEqualTo(stockInitial2);
        stockInitial2.setId(2L);
        assertThat(stockInitial1).isNotEqualTo(stockInitial2);
        stockInitial1.setId(null);
        assertThat(stockInitial1).isNotEqualTo(stockInitial2);
    }
}
