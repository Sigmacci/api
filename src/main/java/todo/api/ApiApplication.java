package todo.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import todo.api.Data.RoleRepository;
import todo.api.Data.ToDoRepository;
import todo.api.Data.UserRepository;
import todo.api.Entities.AppUser;
import todo.api.Entities.Role;
import todo.api.Entities.ToDo;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ToDoRepository toDoRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role role = new Role();
		role.setName("ADMIN");
		roleRepository.save(role);

		Role role2 = new Role();
		role2.setName("USER");

		AppUser user = new AppUser();
		user.setUsername("Igor");
		user.setPassword(passwordEncoder.encode("hello1"));
		user.setEmail("example1@gmail.com");
		user.getRoles().add(role2);

		AppUser user2 = new AppUser();
		user2.setUsername("Bogdan");
		user2.setPassword(passwordEncoder.encode("hello2"));
		user2.setEmail("example2@gmail.com");
		user2.getRoles().add(role2);

		ToDo toDo = new ToDo();
		toDo.setTitle("a");
		toDo.setDescription("b");
		toDo.setDueDate(new Date());

		user.getToDos().add(toDo);
		user2.getToDos().add(toDo);
		toDo.getUsers().add(user);
		toDo.getUsers().add(user2);

		ToDo toDo2 = new ToDo();
		toDo2.setTitle("b");
		toDo2.setDescription("c");
		toDo2.setDueDate(new Date());
		user.getToDos().add(toDo2);
		toDo2.getUsers().add(user);
		
		toDoRepository.save(toDo);
		toDoRepository.save(toDo2);

		userRepository.save(user);
		userRepository.save(user2);

	}






}
