package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Commune;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Commune entity.
 */
public interface CommuneSearchRepository extends ElasticsearchRepository<Commune, Long> {
}
