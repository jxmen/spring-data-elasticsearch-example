package kr.or.automl.esexample.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Document(indexName = "users") // ES DB 테이블 명시
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BasicProfile basicProfile;

    public static User from(Request userRequest) {
        return new User(
                userRequest.name,
                userRequest.description
        );
    }

    public User(String name, String description) {
        this(null, new BasicProfile(name, description));
    }

    @PersistenceConstructor // 이걸 통해 Document를 Aggregate로 재구성할 수 있게 됨.
    public User(Long id, BasicProfile basicProfile) {
        this.id = id;
        this.basicProfile = basicProfile;
    }

    @Getter
    public static final class Request {
        private final String name;
        private final String description;

        public Request(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    @Getter
    public static class Response {
        private final Long id;
        private final String name;
        private final String description;

        public Response(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public static Response from(User user) {
            return new Response(
                    user.getId(),
                    user.basicProfile.getName(),
                    user.basicProfile.getDescription()
            );
        }
    }
}
