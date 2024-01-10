package todo.api.Data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import todo.api.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
