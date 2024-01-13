package todo.api.DTOs;

import java.util.List;

public class UserDto {
    
    private Integer id;
    private String username;
    private String email;
    private List<Integer> toDoIds;

    public UserDto() {
    }

    public UserDto(Integer id, String username, String email, List<Integer> toDoIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.toDoIds = toDoIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getToDoIds() {
        return toDoIds;
    }

    public void setToDoIds(List<Integer> toDoIds) {
        this.toDoIds = toDoIds;
    }

}
