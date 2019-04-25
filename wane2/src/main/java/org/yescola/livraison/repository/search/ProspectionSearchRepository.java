package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Prospection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Prospection entity.
 */
public interface ProspectionSearchRepository extends ElasticsearchRepository<Prospection, Long> {
}
