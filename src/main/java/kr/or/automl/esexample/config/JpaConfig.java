package kr.or.automl.esexample.config;

import kr.or.automl.esexample.domain.file.FileRepository;
import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import kr.or.automl.esexample.domain.user.UserRepository;
import kr.or.automl.esexample.domain.user.search.UserSearchRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = {
        UserRepository.class,
        FileRepository.class
},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        UserSearchRepository.class,
                        FileSearchRepository.class
                }
        )
)
@Configuration
public class JpaConfig {
}
