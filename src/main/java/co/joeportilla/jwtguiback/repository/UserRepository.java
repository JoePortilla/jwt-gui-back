package co.joeportilla.jwtguiback.repository;

import co.joeportilla.jwtguiback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {
    // Method which looks for a user by a login
    Optional<User> findByLogin(String login);
}
