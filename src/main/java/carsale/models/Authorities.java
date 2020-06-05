package carsale.models;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;


/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */
@Entity
@Table(name = "authorities")
public class Authorities implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "authority", nullable = false, length = 100)
    private String authority;

    //класс Authorities владеет классом Users.
    @ManyToOne (optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn (name = "username")
    private Users user;

    public Authorities() {
    }

    public Authorities(String authority, Users user) {
        this.authority = authority;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", user=" + user +
                '}';
    }
}
