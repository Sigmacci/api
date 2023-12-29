package todo.api.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import todo.api.DTOs.ToDoCreateDTO;
import todo.api.DTOs.ToDoDTO;
import todo.api.Data.ToDoRepository;
import todo.api.Data.UserRepository;
import todo.api.Entities.AppUser;
import todo.api.Entities.ToDo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ToDoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    public ToDoController(UserRepository userRepository, ToDoRepository toDoRepository) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ToDoDTO>> getToDos() {
        var toDos = toDoRepository.findAll();
        ArrayList<ToDoDTO> toDoDTOs = new ArrayList<>();
        toDos.forEach(t -> {
            var temp = modelMapper.map(t, ToDoDTO.class);
            temp.setUsersId(t.getUsers().stream().map(AppUser::getId).toList());
            toDoDTOs.add(temp);
        });
        return new ResponseEntity<>(toDoDTOs, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<List<ToDoDTO>> getToDosByUserId(@PathVariable(value = "userId") Integer userId) {
        var user = userRepository.findById(userId).orElse(null);
        ArrayList<ToDoDTO> toDoDtos = new ArrayList<>();
        user.getToDos().forEach(t -> {
            var temp = modelMapper.map(t, ToDoDTO.class);
            temp.setUsersId(t.getUsers().stream().map(AppUser::getId).toList());
            toDoDtos.add(temp);
        });
        return new ResponseEntity<>(toDoDtos, HttpStatus.OK);
    }

    // @GetMapping("/users/{userId}/todos/{toDoId}")
    // public ResponseEntity<ToDoDTO> getToDoByUserId(@PathVariable(value =
    // "userId") Integer userId,
    // @PathVariable(value = "toDoId") Integer toDoId) {

    // var toDo = _userRepository.findById(userId).orElse(null).getToDos()
    // .stream().map(_mapper::toDoToDTO).filter(t -> t.getId() == toDoId)
    // .findFirst().orElse(null);
    // if (toDo == null)
    // return new ResponseEntity<>(toDo, HttpStatus.NOT_FOUND);
    // return new ResponseEntity<>(toDo, HttpStatus.OK);
    // }

    // @GetMapping("/users/{userId}/todos/{toDoId}/users")
    // public ResponseEntity<List<AppUser>> getUsersByToDoId(@PathVariable(value =
    // "userId") Integer userId,
    // @PathVariable(value = "toDoId") Integer toDoId) {
    // return new ResponseEntity<>(new
    // ArrayList<>(_toDoRepository.findById(toDoId).orElse(null).getUsers()),
    // HttpStatus.OK);
    // }

    @PostMapping("/users/{userId}/todos")
    public ResponseEntity<ToDoDTO> createToDo(@PathVariable(value = "userId") Integer userId,
            @RequestBody ToDoCreateDTO toDoCreateDTO) {
        ToDo toDo = new ToDo();
        toDo = modelMapper.map(toDoCreateDTO, ToDo.class);
        var user = userRepository.findById(userId).orElse(null);
        toDo.getUsers().add(user);
        user.getToDos().add(toDo);

        userRepository.save(user);
        // _toDoRepository.save(toDo);

        var toDoDTO = modelMapper.map(toDo, ToDoDTO.class);
        toDoDTO.setUsersId(toDo.getUsers().stream().map(AppUser::getId).toList());
        return new ResponseEntity<>(toDoDTO, HttpStatus.CREATED);
    }

    // @DeleteMapping("/todos/{todoId}")
    @Transactional
    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable(value = "id") Integer id) {
        var toDo = toDoRepository.findById(id).orElse(null);
        if (toDo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        toDo.getUsers().forEach(u -> {
            u.getToDos().remove(toDo);
        });
        toDoRepository.delete(toDo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}