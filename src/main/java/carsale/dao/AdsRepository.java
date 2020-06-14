package carsale.dao;

import carsale.models.Ads;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Repository
public class AdsRepository {

    @PersistenceContext
    private EntityManager em;


    public Ads save(Ads ad) {
        if (ad.getId() == null) {
            em.persist(ad);
        } else {
            em.merge(ad);
        }
        return ad;
    }


    public void removeById(int id) {
        Ads ad = em.find(Ads.class, id);
        em.remove(ad);
    }


    public List<Ads> getAll() {
        return em.createQuery("from Ads ad join fetch ad.carDetails cr").getResultList();
    }


    public Ads getById(int id) {
        Ads ad = em.find(Ads.class, id);
        return ad;
    }


}
