package carsale.dao;

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
    public Ads save(Ads ad) {
        if (ad.getId() == null) {
            em.persist(ad);
        } else {
            em.merge(ad);
        }
        return ad;
    }

    @Transactional
    public void removeById(int id) {
        Ads ad = em.find(Ads.class, id);
        em.remove(ad);
    }


    public List<Ads> getAll() {
        return em.createQuery("from Ads").getResultList();
    }

    public Ads getById(int id) {
        Ads ad = em.find(Ads.class, id);
        return ad;
    }


}
