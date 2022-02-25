package kr.or.automl.esexample.domain.user.search;

import kr.or.automl.esexample.domain.user.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserSearchRepository extends ElasticsearchRepository<User, Long>, CustomUserSearchRepository {

    List<User> findByBasicProfile_NameContains(String name);
}
