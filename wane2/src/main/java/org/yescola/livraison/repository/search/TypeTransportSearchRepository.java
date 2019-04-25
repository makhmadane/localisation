package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.TypeTransport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeTransport entity.
 */
public interface TypeTransportSearchRepository extends ElasticsearchRepository<TypeTransport, Long> {
}
