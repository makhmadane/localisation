package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ReglementSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReglementSearchRepositoryMockConfiguration {

    @MockBean
    private ReglementSearchRepository mockReglementSearchRepository;

}
