package carsale.models;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */
@Entity
@Table(name = "bodytype")
public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String bodyName;

    @OneToMany (mappedBy = "body", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Car> car;

    public BodyType(String bodyName) {
        this.bodyName = bodyName;
    }

    public BodyType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    @Override
    public String toString() {
        return "BodyType{" +
                "id=" + id +
                ", bodyName='" + bodyName + '\'' +
                '}';
    }
}
