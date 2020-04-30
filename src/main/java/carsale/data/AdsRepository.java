package carsale.data;

import carsale.models.Ads;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdsRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ads createAd(Ads ads) {
        em.persist(ads);
        return ads;
    }

    @Transactional
    public void removeAd(Ads ad) {
        em.remove(ad);
    }

    @Transactional
    public Ads updateAd(Ads ads) {
        return em.merge(ads);
    }

    public List<Ads> getAllAds() {
        return em.createQuery("from Ads").getResultList();
    }

    public Ads getAdById(int id) {
        Ads ad = em.find(Ads.class, id);
        return ad;
    }


}
