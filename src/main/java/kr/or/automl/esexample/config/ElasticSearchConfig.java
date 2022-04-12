package kr.or.automl.esexample.config;

import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import kr.or.automl.esexample.domain.user.search.UserSearchRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
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
    private final String hostname;
    private final String port;

    public ElasticSearchConfig(
            @Value("${elasticsearch.hostname}") String hostname,
            @Value("${elasticsearch.port}") String port
    ) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        String hostAndPort = String.format("%s:%s", hostname, port);

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
