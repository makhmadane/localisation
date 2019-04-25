package org.yescola.livraison.web.rest;

import org.yescola.livraison.YesColaApp;

import org.yescola.livraison.domain.Article;
import org.yescola.livraison.repository.ArticleRepository;
import org.yescola.livraison.repository.search.ArticleSearchRepository;
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
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YesColaApp.class)
public class ArticleResourceIntTest {

    private static final String DEFAULT_NOMARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_NOMARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMEROARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_QTE_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_QTE_STOCK = "BBBBBBBBBB";

    private static final String DEFAULT_QTE_SEUIL = "AAAAAAAAAA";
    private static final String UPDATED_QTE_SEUIL = "BBBBBBBBBB";

    private static final String DEFAULT_PACK = "AAAAAAAAAA";
    private static final String UPDATED_PACK = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX = "AAAAAAAAAA";
    private static final String UPDATED_PRIX = "BBBBBBBBBB";

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * This repository is mocked in the org.yescola.livraison.repository.search test package.
     *
     * @see org.yescola.livraison.repository.search.ArticleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArticleSearchRepository mockArticleSearchRepository;

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

    private MockMvc restArticleMockMvc;

    private Article article;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleResource articleResource = new ArticleResource(articleRepository, mockArticleSearchRepository);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
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
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .nomarticle(DEFAULT_NOMARTICLE)
            .numeroarticle(DEFAULT_NUMEROARTICLE)
            .qteStock(DEFAULT_QTE_STOCK)
            .qteSeuil(DEFAULT_QTE_SEUIL)
            .pack(DEFAULT_PACK)
            .etat(DEFAULT_ETAT)
            .prix(DEFAULT_PRIX);
        return article;
    }

    @Before
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getNomarticle()).isEqualTo(DEFAULT_NOMARTICLE);
        assertThat(testArticle.getNumeroarticle()).isEqualTo(DEFAULT_NUMEROARTICLE);
        assertThat(testArticle.getQteStock()).isEqualTo(DEFAULT_QTE_STOCK);
        assertThat(testArticle.getQteSeuil()).isEqualTo(DEFAULT_QTE_SEUIL);
        assertThat(testArticle.getPack()).isEqualTo(DEFAULT_PACK);
        assertThat(testArticle.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testArticle.getPrix()).isEqualTo(DEFAULT_PRIX);

        // Validate the Article in Elasticsearch
        verify(mockArticleSearchRepository, times(1)).save(testArticle);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Article in Elasticsearch
        verify(mockArticleSearchRepository, times(0)).save(article);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomarticle").value(hasItem(DEFAULT_NOMARTICLE.toString())))
            .andExpect(jsonPath("$.[*].numeroarticle").value(hasItem(DEFAULT_NUMEROARTICLE.toString())))
            .andExpect(jsonPath("$.[*].qteStock").value(hasItem(DEFAULT_QTE_STOCK.toString())))
            .andExpect(jsonPath("$.[*].qteSeuil").value(hasItem(DEFAULT_QTE_SEUIL.toString())))
            .andExpect(jsonPath("$.[*].pack").value(hasItem(DEFAULT_PACK.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.toString())));
    }
    
    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.nomarticle").value(DEFAULT_NOMARTICLE.toString()))
            .andExpect(jsonPath("$.numeroarticle").value(DEFAULT_NUMEROARTICLE.toString()))
            .andExpect(jsonPath("$.qteStock").value(DEFAULT_QTE_STOCK.toString()))
            .andExpect(jsonPath("$.qteSeuil").value(DEFAULT_QTE_SEUIL.toString()))
            .andExpect(jsonPath("$.pack").value(DEFAULT_PACK.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).get();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .nomarticle(UPDATED_NOMARTICLE)
            .numeroarticle(UPDATED_NUMEROARTICLE)
            .qteStock(UPDATED_QTE_STOCK)
            .qteSeuil(UPDATED_QTE_SEUIL)
            .pack(UPDATED_PACK)
            .etat(UPDATED_ETAT)
            .prix(UPDATED_PRIX);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getNomarticle()).isEqualTo(UPDATED_NOMARTICLE);
        assertThat(testArticle.getNumeroarticle()).isEqualTo(UPDATED_NUMEROARTICLE);
        assertThat(testArticle.getQteStock()).isEqualTo(UPDATED_QTE_STOCK);
        assertThat(testArticle.getQteSeuil()).isEqualTo(UPDATED_QTE_SEUIL);
        assertThat(testArticle.getPack()).isEqualTo(UPDATED_PACK);
        assertThat(testArticle.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testArticle.getPrix()).isEqualTo(UPDATED_PRIX);

        // Validate the Article in Elasticsearch
        verify(mockArticleSearchRepository, times(1)).save(testArticle);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Create the Article

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Article in Elasticsearch
        verify(mockArticleSearchRepository, times(0)).save(article);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Delete the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Article in Elasticsearch
        verify(mockArticleSearchRepository, times(1)).deleteById(article.getId());
    }

    @Test
    @Transactional
    public void searchArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);
        when(mockArticleSearchRepository.search(queryStringQuery("id:" + article.getId())))
            .thenReturn(Collections.singletonList(article));
        // Search the article
        restArticleMockMvc.perform(get("/api/_search/articles?query=id:" + article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomarticle").value(hasItem(DEFAULT_NOMARTICLE)))
            .andExpect(jsonPath("$.[*].numeroarticle").value(hasItem(DEFAULT_NUMEROARTICLE)))
            .andExpect(jsonPath("$.[*].qteStock").value(hasItem(DEFAULT_QTE_STOCK)))
            .andExpect(jsonPath("$.[*].qteSeuil").value(hasItem(DEFAULT_QTE_SEUIL)))
            .andExpect(jsonPath("$.[*].pack").value(hasItem(DEFAULT_PACK)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = new Article();
        article1.setId(1L);
        Article article2 = new Article();
        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);
        article2.setId(2L);
        assertThat(article1).isNotEqualTo(article2);
        article1.setId(null);
        assertThat(article1).isNotEqualTo(article2);
    }
}
