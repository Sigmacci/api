package todo.api.Controllers;

import org.springframework.web.bind.annotation.RestController;

import todo.api.DTOs.UserDto;
import todo.api.Data.UserRepository;
import todo.api.Entities.ToDo;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UsersController {

    @Autowired
    private UserRepository _repository;

    @Autowired
    private ModelMapper modelMapper;

    public UsersController(UserRepository repository) {
        _repository = repository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        var users = _repository.findAll();
        ArrayList<UserDto> userDTOs = new ArrayList<>();
        users.forEach(u -> {
            var temp = modelMapper.map(u, UserDto.class);
            temp.setToDoIds(u.getToDos().stream().map(ToDo::getId).toList());
            userDTOs.add(temp);
        });
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    // @PostMapping("/users")
    // public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
    // return new ResponseEntity<>(_repository.save(user), HttpStatus.CREATED);
    // }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        var user = _repository.findById(id).get();
        var userDTO = modelMapper.map(user, UserDto.class);
        userDTO.setToDoIds(user.getToDos().stream().map(ToDo::getId).toList());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // @PutMapping("/users/{id}")
    // public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
    //     var user = _mapper.toAppUser(userDTO);
    //     return new ResponseEntity<>(_repository.findById(id)
    //             .map(u -> {
    //                 u = user;
    //                 return _repository.save(u);
    //             }).map(_mapper::toUserDTO).get(), HttpStatus.I_AM_A_TEAPOT);
    // }

    // @DeleteMapping("/users/{id}")
    // public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
    //     _repository.deleteById(id);
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

}
