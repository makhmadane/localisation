package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Boutique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Boutique entity.
 */
public interface BoutiqueSearchRepository extends ElasticsearchRepository<Boutique, Long> {
}
