package org.yescola.livraison.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of QualiteSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class QualiteSearchRepositoryMockConfiguration {

    @MockBean
    private QualiteSearchRepository mockQualiteSearchRepository;

}
