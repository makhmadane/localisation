package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Secteur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Secteur entity.
 */
public interface SecteurSearchRepository extends ElasticsearchRepository<Secteur, Long> {
}
