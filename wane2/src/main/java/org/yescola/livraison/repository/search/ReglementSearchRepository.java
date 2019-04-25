package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Reglement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reglement entity.
 */
public interface ReglementSearchRepository extends ElasticsearchRepository<Reglement, Long> {
}
