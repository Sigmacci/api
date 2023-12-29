package todo.api.Entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@RequestMapping("/api")
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer _id;

    @Column(name = "username")
    private String _username;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "_users")
    private Set<ToDo> _toDos = new HashSet<>();

    public AppUser() {
    }

    public AppUser(String username) {
        _username = username;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public Set<ToDo> getToDos() {
        return _toDos;
    }

    public void setToDos(Set<ToDo> toDos) {
        _toDos = toDos;
    }

    public void addToDo(ToDo toDo) {
        _toDos.add(toDo);
    }

    @Override
    public String toString() {
        return "AppUser [id=" + _id + ", username=" + _username + ", toDos=" + _toDos.stream().map(ToDo::getId).toList()
                + "]";
    }

    // @Override
    // public int hashCode() {
    // return Objects.hash(_id, _username, _toDos);
    // }

}
