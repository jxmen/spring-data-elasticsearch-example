package kr.or.automl.esexample.config;

import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import kr.or.automl.esexample.domain.user.search.UserSearchRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(
        basePackageClasses = {
                UserSearchRepository.class,
                FileSearchRepository.class
        }
)
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedToLocalhost()
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
