package todo.api.DTOs;

import java.util.List;

public class UserDTO {
    
    private Integer id;
    private String username;
    private List<Integer> toDoIds;

    public UserDTO(Integer id, String username, List<Integer> toDoIds) {
        this.id = id;
        this.username = username;
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

    public List<Integer> getToDoIds() {
        return toDoIds;
    }

    public void setToDoIds(List<Integer> toDoIds) {
        this.toDoIds = toDoIds;
    }

}
