package todo.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import todo.api.Data.ToDoRepository;
import todo.api.Data.UserRepository;
import todo.api.Entities.AppUser;
import todo.api.Entities.ToDo;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ToDoRepository toDoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AppUser user = new AppUser();
		user.setUsername("Igor");

		AppUser user2 = new AppUser();
		user2.setUsername("Bogdan");

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
