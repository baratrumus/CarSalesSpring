package carsale.models;


import javax.persistence.*;
import java.util.Set;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */
@Entity
@Table(name = "brands")
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String brandName;

    @OneToMany (mappedBy = "brand", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Car> car;

    @OneToMany (mappedBy = "brand", fetch = FetchType.LAZY)
    private Set<Models> models;

    public Brands(String brandName) {
        this.brandName = brandName;
    }

    public Brands() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
