package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DetailComSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DetailComSearchRepositoryMockConfiguration {

    @MockBean
    private DetailComSearchRepository mockDetailComSearchRepository;

}
