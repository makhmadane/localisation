package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Parfum;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Parfum entity.
 */
public interface ParfumSearchRepository extends ElasticsearchRepository<Parfum, Long> {
}
