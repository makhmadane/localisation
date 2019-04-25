package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.TypeRglment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeRglment entity.
 */
public interface TypeRglmentSearchRepository extends ElasticsearchRepository<TypeRglment, Long> {
}
