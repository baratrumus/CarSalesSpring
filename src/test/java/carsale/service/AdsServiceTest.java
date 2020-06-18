package carsale.service;

import carsale.config.ApplicationConfig;
import carsale.models.Ads;
import carsale.models.Car;
import carsale.models.Users;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.sql.Timestamp;
import java.util.List;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
public class AdsServiceTest {

    @Autowired
    AdsService adsService;
    @Autowired
    CarsService carsService;

    byte[] byteArr = new byte[0];
    Timestamp dateNow = new Timestamp(System.currentTimeMillis());
    Users user;
    Car car;
    Ads ad;

    @Before
    public void init() {
        user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        user.setId(2);
        car = new Car(carsService.getBrandById(3),
                carsService.getModelById(8),
                carsService.getBodyById(1),
                carsService.getEngineById(1),
                "1988", "red", 2000);
        carsService.create(car);
        ad = new Ads(user, car, "descr", dateNow, byteArr, 999);
        adsService.save(ad);
    }

    private void destroy() {
        adsService.removeById(ad.getId());
    }


    @Test
    public void whenAdsSavedItsInBase() {
        List<Ads> result = adsService.getAll();
        assertTrue(result.contains(ad));
        destroy();
    }

    @Test
    public void removeById() {
        int adId = ad.getId();
        adsService.removeById(adId);
        Ads baseAd = adsService.getById(adId);
        assertNull(baseAd);
    }


    @Test
    public void getAll() {
        List<Ads> ads = adsService.getAll();
        assertEquals(ads.size(), 6);
        destroy();
    }


    @Test
    public void getById() {
        int adId = ad.getId();
        Ads resAd =  adsService.getById(adId);
        assertEquals(ad, resAd);
        destroy();
    }

}