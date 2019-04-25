package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Article entity.
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
}
