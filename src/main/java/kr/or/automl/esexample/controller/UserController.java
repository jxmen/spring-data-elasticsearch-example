package kr.or.automl.esexample.controller;

import kr.or.automl.esexample.application.UserService;
import kr.or.automl.esexample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> save(
            @Valid @RequestBody User.Request userRequest
    ) {
        Long id = userService.save(userRequest);
        URI uri = URI.create(String.valueOf(id));

        return ResponseEntity.created(uri)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<User.Response>> getUsers() {
        List<User.Response> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<User.Response>> search(
            @PathVariable String name,
            Pageable pageable
    ) {
        List<User.Response> userResponses = userService.searchByName(name, pageable);

        return ResponseEntity.ok(userResponses);
    }
}
