package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DetailRouteSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DetailRouteSearchRepositoryMockConfiguration {

    @MockBean
    private DetailRouteSearchRepository mockDetailRouteSearchRepository;

}
