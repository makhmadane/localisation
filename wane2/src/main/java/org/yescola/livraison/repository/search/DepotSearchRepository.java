package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Depot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Depot entity.
 */
public interface DepotSearchRepository extends ElasticsearchRepository<Depot, Long> {
}
