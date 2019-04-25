package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.BonLivraison;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BonLivraison entity.
 */
public interface BonLivraisonSearchRepository extends ElasticsearchRepository<BonLivraison, Long> {
}
