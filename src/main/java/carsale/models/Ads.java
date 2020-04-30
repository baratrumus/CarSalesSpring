package carsale.models;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;


/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Entity
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //класс Ads владеет классом Users
    //@ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    //при удалении объявл. юзер не удаляется
    @ManyToOne ()
    @JoinColumn (name = "user_id")
    private Users userId;

    //при удалении объявл. car удаляется
    @OneToOne (orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn (name = "car_id")
    private Car carDetails;

    @Column(name = "descr")
    private String descr;

    @Column(name = "created")
    @Basic
    private Timestamp created;

    @Column(name = "sold")
    private Boolean sold;

    @Lob
    @Column(name = "fileimage", columnDefinition = "bytea")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] fileimage;

    @Column(name = "price")
    private String price;

    @Transient
    boolean photoExists;


    public Ads() {
    }

    public Ads(Users userId, Car carDetails, String descr, Timestamp created, Boolean sold, byte[] fileimage, String price) {
        this.userId = userId;
        this.carDetails = carDetails;
        this.descr = descr;
        this.created = created;
        this.sold = sold;
        this.fileimage = fileimage;
        this.price = price;
    }

    public Ads(Users userId, Car carDetails) {
        this.userId = userId;
        this.carDetails = carDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public byte[] getPhoto() {
        return fileimage;
    }

    public void setPhoto(byte[] fileimage) {
        this.fileimage = fileimage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Car getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(Car carDetails) {
        this.carDetails = carDetails;
    }

    public LocalDateTime getDateTime() {
        return getCreated().toLocalDateTime().withNano(0);
    }

    public String getPhotoBase64() {
        String imgString  = Base64.getEncoder().encodeToString(fileimage);
        return imgString;
    }

    public boolean isPhotoExists() {
        return photoExists;
    }

    public void setPhotoExists(boolean photoExists) {
        this.photoExists = photoExists;
    }


    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", userId=" + userId +
                ", carDetails=" + carDetails +
                ", descr='" + descr + '\'' +
                ", created=" + created +
                ", sold=" + sold +
                ", price=" + price +
                '}';
    }
}