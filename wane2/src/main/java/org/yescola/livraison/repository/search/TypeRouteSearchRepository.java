package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.TypeRoute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeRoute entity.
 */
public interface TypeRouteSearchRepository extends ElasticsearchRepository<TypeRoute, Long> {
}
