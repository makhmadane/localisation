package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Qualite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Qualite entity.
 */
public interface QualiteSearchRepository extends ElasticsearchRepository<Qualite, Long> {
}
