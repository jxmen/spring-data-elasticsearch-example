package kr.or.automl.esexample;

import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import kr.or.automl.esexample.domain.user.search.UserSearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
                UserSearchRepository.class,
                FileSearchRepository.class
        }
))
@SpringBootApplication
public class EsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsExampleApplication.class, args);
    }

}
