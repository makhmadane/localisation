package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BonLivraisonSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BonLivraisonSearchRepositoryMockConfiguration {

    @MockBean
    private BonLivraisonSearchRepository mockBonLivraisonSearchRepository;

}
