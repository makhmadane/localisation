package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.MoyenTransport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MoyenTransport entity.
 */
public interface MoyenTransportSearchRepository extends ElasticsearchRepository<MoyenTransport, Long> {
}
