package ru.gee.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gee.learning.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
