package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BoutiqueSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BoutiqueSearchRepositoryMockConfiguration {

    @MockBean
    private BoutiqueSearchRepository mockBoutiqueSearchRepository;

}
