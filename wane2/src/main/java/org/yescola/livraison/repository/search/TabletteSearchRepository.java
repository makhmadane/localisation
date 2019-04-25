package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.Tablette;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tablette entity.
 */
public interface TabletteSearchRepository extends ElasticsearchRepository<Tablette, Long> {
}
