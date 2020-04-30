package carsale.data;

import carsale.models.Ads;
import carsale.models.Brands;
import carsale.models.Car;
import carsale.models.Users;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AdsTest {
    private static final DbTmp STORAGE = DbHibernate.getInstance();

    @Test
    @Ignore
    public void adAdd() {
        Users user = new Users("tu", "tu");
        Brands br = STORAGE.getBrandById(1);
        Car car = new Car(STORAGE.getBrandById(1), STORAGE.getModelById(1), STORAGE.getBodyById(1), STORAGE.getEngineById(1), "f", "f");
        Ads ad = new Ads(user, car);
        boolean i = STORAGE.addNewItem(ad);
        List<Ads> li = STORAGE.getAllItems();
        assertThat(ad, is(this.STORAGE.getAdById(ad.getId())));
    }
}
