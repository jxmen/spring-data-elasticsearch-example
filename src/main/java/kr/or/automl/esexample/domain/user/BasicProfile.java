package kr.or.automl.esexample.domain.user;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class BasicProfile {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    protected BasicProfile() {
    }

    public BasicProfile(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
