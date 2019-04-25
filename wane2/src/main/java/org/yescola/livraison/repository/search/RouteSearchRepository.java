package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Route;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Route entity.
 */
public interface RouteSearchRepository extends ElasticsearchRepository<Route, Long> {
}
