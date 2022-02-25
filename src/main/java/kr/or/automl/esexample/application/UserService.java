package kr.or.automl.esexample.application;

import kr.or.automl.esexample.domain.user.User;
import kr.or.automl.esexample.domain.user.UserRepository;
import kr.or.automl.esexample.domain.user.search.UserSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSearchRepository userSearchRepository;

    @Transactional
    public Long save(User.Request userRequest) {
        User user = User.from(userRequest);
        User savedUser = userRepository.save(user);
        userSearchRepository.save(user);

        return savedUser.getId();
    }

    @Transactional(readOnly = true)
    public List<User.Response> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(User.Response::from)
                .collect(Collectors.toList());
    }

    public List<User.Response> searchByName(String name, Pageable pageable) {
        return userSearchRepository.searchByName(name, pageable).stream()
                .map(User.Response::from)
                .collect(Collectors.toList());
    }
}
