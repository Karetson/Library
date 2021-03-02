package pl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapters.mysql.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByFirstNameOrLastName(String firstName, String lastName);
    List<User> findByRole(String role);
    Boolean existsByEmail(String email);
}
