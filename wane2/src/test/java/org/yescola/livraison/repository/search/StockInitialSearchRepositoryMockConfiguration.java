package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of StockInitialSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StockInitialSearchRepositoryMockConfiguration {

    @MockBean
    private StockInitialSearchRepository mockStockInitialSearchRepository;

}
