package todo.api.DTOs;

import java.util.Date;
import java.util.List;

public class ToDoDto {
    
    private Integer id;
    private String title;
    private String description;
    private Boolean finished;
    private Date dueDate;
    private List<Integer> usersId;

    public ToDoDto() {}

    public ToDoDto(Integer id, String title, String description, Boolean finished, Date dueDate, List<Integer> usersId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.dueDate = dueDate;
        this.usersId = usersId;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getFinished() {
        return finished;
    }
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public List<Integer> getUsersId() {
        return usersId;
    }
    public void setUsersId(List<Integer> users) {
        this.usersId = users;
    }

    @Override
    public String toString() {
        return "ToDoDTO [title=" + title + ", description=" + description + ", finished=" + finished + ", dueDate="
                + dueDate + ", userIds=" + usersId + "]";
    }
    

}
