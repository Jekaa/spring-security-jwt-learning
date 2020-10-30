package ru.gee.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gee.learning.entity.User;
import ru.gee.learning.entity.UserRole;
import ru.gee.learning.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecurityJwtLearningApplication {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void init() {
        List<User> users = Stream.of(
                new User(1L, "admin", "admin", "admin@email.com", UserRole.ROLE_ADMIN),
                new User(2L, "user1", "123", "user1@email.com", UserRole.ROLE_USER),
                new User(3L, "user2", "qwe", "user2@email.com", UserRole.ROLE_USER)
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtLearningApplication.class, args);
    }

}
