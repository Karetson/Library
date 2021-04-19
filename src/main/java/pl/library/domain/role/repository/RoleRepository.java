package pl.library.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.adapters.mysql.model.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
