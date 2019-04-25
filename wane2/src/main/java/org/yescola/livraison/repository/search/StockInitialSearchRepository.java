package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.StockInitial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StockInitial entity.
 */
public interface StockInitialSearchRepository extends ElasticsearchRepository<StockInitial, Long> {
}
