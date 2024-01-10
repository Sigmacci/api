package todo.api.Controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import todo.api.DTOs.ToDoCreateDto;
import todo.api.DTOs.ToDoDto;
import todo.api.DTOs.UserDto;
import todo.api.Data.ToDoRepository;
import todo.api.Data.UserRepository;
import todo.api.Entities.AppUser;
import todo.api.Entities.ToDo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ToDoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ToDoController(UserRepository userRepository, ToDoRepository toDoRepository) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ToDoDto>> getToDos() {
        var toDos = toDoRepository.findAll();
        ArrayList<ToDoDto> toDoDTOs = new ArrayList<>();
        toDos.forEach(t -> {
            var temp = modelMapper.map(t, ToDoDto.class);
            temp.setUsersId(t.getUsers().stream().map(AppUser::getId).toList());
            toDoDTOs.add(temp);
        });
        return new ResponseEntity<>(toDoDTOs, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<List<ToDoDto>> getToDosByUserId(@PathVariable(value = "userId") Integer userId) {
        var user = userRepository.findById(userId).orElse(null);
        ArrayList<ToDoDto> toDoDtos = new ArrayList<>();
        user.getToDos().forEach(t -> {
            var temp = modelMapper.map(t, ToDoDto.class);
            temp.setUsersId(t.getUsers().stream().map(AppUser::getId).toList());
            toDoDtos.add(temp);
        });
        return new ResponseEntity<>(toDoDtos, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/todos/{toDoId}")
    public ResponseEntity<ToDoDto> getToDoByUserId(@PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "toDoId") Integer toDoId) {

        var user = userRepository.findById(userId).orElse(null);
        var toDo = user.getToDos().stream().filter(t -> t.getId() == toDoId).findFirst().orElse(null);
        var toDoDTO = modelMapper.map(toDo, ToDoDto.class);
        toDoDTO.setUsersId(toDo.getUsers().stream().map(AppUser::getId).toList());
        return new ResponseEntity<>(toDoDTO, HttpStatus.OK);
    }

    @GetMapping("/todos/{id}/users")
    public ResponseEntity<List<UserDto>> getUsersByToDoId(@PathVariable(value = "id") Integer id) {
        var toDo = toDoRepository.findById(id).orElse(null);
        if (toDo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var users = toDo.getUsers();
        ArrayList<UserDto> userDTOs = new ArrayList<>();
        users.forEach(u -> {
            var temp = modelMapper.map(u, UserDto.class);
            temp.setToDoIds(u.getToDos().stream().map(ToDo::getId).toList());
            userDTOs.add(temp);
        });
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/todos")
    public ResponseEntity<ToDoDto> createToDo(@PathVariable(value = "userId") Integer userId,
            @RequestBody ToDoCreateDto toDoCreateDTO) {
        ToDo toDo = new ToDo();
        toDo = modelMapper.map(toDoCreateDTO, ToDo.class);
        var user = userRepository.findById(userId).orElse(null);
        toDo.getUsers().add(user);
        user.getToDos().add(toDo);

        userRepository.save(user);
        // _toDoRepository.save(toDo);

        var toDoDTO = modelMapper.map(toDo, ToDoDto.class);
        toDoDTO.setUsersId(toDo.getUsers().stream().map(AppUser::getId).toList());
        return new ResponseEntity<>(toDoDTO, HttpStatus.CREATED);
    }

    @PutMapping("users/{userId}/todos/{toDoId}")
    public ResponseEntity<ToDoDto> editToDo(@PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "toDoId") Integer toDoId, @RequestBody ToDoDto toDoDTO) {
        var toDo = toDoRepository.findById(toDoId).orElse(null);
        if (toDo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (toDo.getUsers().stream().noneMatch(u -> u.getId() == userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        toDo = modelMapper.map(toDoDTO, ToDo.class);
        Set<AppUser> users = new HashSet<>();
        toDoDTO.getUsersId().forEach(u -> {
            var temp = userRepository.findById(u).orElse(null);
            users.add(temp);
        });
        toDo.setUsers(users);
        toDoRepository.save(toDo); 
        return new ResponseEntity<>(toDoDTO, HttpStatus.OK);
    }

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