package carsale.models;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Entity
@Table(name = "models")
public class Models {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String modelName;

    @ManyToOne (optional = true, cascade = CascadeType.ALL)
    @JoinColumn (name = "brand_id")
    private Brands brand;

    @OneToMany (mappedBy = "model", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Car> car;

    public Models(String modelName, Brands brand) {
        this.modelName = modelName;
        this.brand = brand;
    }

    public Models() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "Models{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
