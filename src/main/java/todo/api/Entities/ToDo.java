package todo.api.Entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer _id;

    @Column(name = "title")
    private String _title;

    @Column(name = "description")
    private String _description;

    @Column(name = "finished")
    private Boolean _finished = false;

    @Column(name = "duedate")
    private Date _dueDate;

    // @ManyToMany(mappedBy = "_toDos")
    // @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
    // CascadeType.MERGE })
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "users_todos", joinColumns = @JoinColumn(name = "todo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<AppUser> _users = new HashSet<>();

    public ToDo() {
    }

    public ToDo(String title, String description, Date dueDate, AppUser user) {
        _title = title;
        _description = description;
        _dueDate = dueDate;
        _users.add(user);
        _finished = false;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public Boolean isFinished() {
        return _finished;
    }

    public void setFinished(Boolean finished) {
        _finished = finished;
    }

    public Date getDueDate() {
        return _dueDate;
    }

    public void setDueDate(Date dueDate) {
        _dueDate = dueDate;
    }

    public Set<AppUser> getUsers() {
        return _users;
    }

    public void setUsers(Set<AppUser> users) {
        _users = users;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_id == null) ? 0 : _id.hashCode());
        result = prime * result + ((_title == null) ? 0 : _title.hashCode());
        result = prime * result + ((_description == null) ? 0 : _description.hashCode());
        result = prime * result + ((_finished == null) ? 0 : _finished.hashCode());
        result = prime * result + ((_dueDate == null) ? 0 : _dueDate.hashCode());
        result = prime * result + ((_users == null) ? 0 : _users.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ToDo{"
                + "id=" + _id
                + ", title=\'" + _title
                + "\', description=\'" + _description
                + "\', finished=" + _finished
                + ", dueDate=" + _dueDate
                + ", users=" + _users.stream().map(AppUser::getId).toList()
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ToDo other = (ToDo) obj;
        if (_id == null) {
            if (other._id != null)
                return false;
        } else if (!_id.equals(other._id))
            return false;
        if (_title == null) {
            if (other._title != null)
                return false;
        } else if (!_title.equals(other._title))
            return false;
        if (_description == null) {
            if (other._description != null)
                return false;
        } else if (!_description.equals(other._description))
            return false;
        if (_finished == null) {
            if (other._finished != null)
                return false;
        } else if (!_finished.equals(other._finished))
            return false;
        if (_dueDate == null) {
            if (other._dueDate != null)
                return false;
        } else if (!_dueDate.equals(other._dueDate))
            return false;
        if (_users == null) {
            if (other._users != null)
                return false;
        } else if (!_users.equals(other._users))
            return false;
        return true;
    }
}
