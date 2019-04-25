package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.DetailRoute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetailRoute entity.
 */
public interface DetailRouteSearchRepository extends ElasticsearchRepository<DetailRoute, Long> {
}
