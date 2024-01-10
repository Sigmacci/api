package todo.api.Data;

import org.springframework.data.jpa.repository.JpaRepository;

import todo.api.Entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
    //List<AppUser> findUsersByToDosId(Integer toDoId);
}
