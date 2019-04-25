package org.yescola.livraison.repository.search;

import org.yescola.livraison.domain.DetailCom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetailCom entity.
 */
public interface DetailComSearchRepository extends ElasticsearchRepository<DetailCom, Long> {
}
