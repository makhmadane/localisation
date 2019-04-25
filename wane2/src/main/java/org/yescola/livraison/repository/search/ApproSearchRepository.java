package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Appro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Appro entity.
 */
public interface ApproSearchRepository extends ElasticsearchRepository<Appro, Long> {
}
