package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TabletteSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TabletteSearchRepositoryMockConfiguration {

    @MockBean
    private TabletteSearchRepository mockTabletteSearchRepository;

}
