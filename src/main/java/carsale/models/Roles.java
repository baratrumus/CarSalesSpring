package carsale.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "id")
    private Integer id;

    @JsonProperty("RoleName")
    @Column(name = "role_name")
    private String roleName;

    @OneToMany (mappedBy = "role", fetch = FetchType.EAGER)
    private Set<Users> users;


    public Roles() {
    }


    public Roles(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Roles(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                '}';
    }
}
