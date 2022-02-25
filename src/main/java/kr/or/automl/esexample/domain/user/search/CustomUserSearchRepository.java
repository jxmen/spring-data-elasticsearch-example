package kr.or.automl.esexample.domain.user.search;

import kr.or.automl.esexample.domain.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomUserSearchRepository {
    List<User> searchByName(String name, Pageable pageable);
}
