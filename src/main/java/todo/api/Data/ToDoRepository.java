package todo.api.Data;

import org.springframework.data.jpa.repository.JpaRepository;

import todo.api.Entities.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {
    // List<ToDo> findToDosByUsersId(Integer userId);
}
