package todo.api.Helpers;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class UserInfoResponse {
    
    private Integer id;
    private String username;
    private List<String> roles;

    public UserInfoResponse(Integer id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public List<String> getRoles() {
        return roles;
    }

}
