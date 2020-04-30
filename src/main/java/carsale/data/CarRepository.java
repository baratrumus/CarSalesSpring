package carsale.data;


import carsale.models.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CarRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Car createCar(Car car) {
        em.persist(car);
        return car;
    }

    public List<Brands> getBrands() {
       return em.createQuery("from Brands").getResultList();
    }

    public List<BodyType> getBodies() {
        return em.createQuery("from BodyType").getResultList();
    }

    public List<Engines> getEngines() {
        return em.createQuery("from Engines").getResultList();
    }

    public List<Models> getModelsByBrandId(int brandId) {
        Query query = em.createQuery("from Models where brand_id = :paramName");
        query.setParameter("paramName", brandId);
        return query.getResultList();
    }

    public BodyType getBodyById(Integer id) {
        return em.find(BodyType.class, id);
    }

    public Brands getBrandById(Integer id) {
        return em.find(Brands.class, id);
    }

    public Models getModelById(Integer id) {
        return em.find(Models.class, id);
    }

    public Engines getEngineById(Integer id) {
        return em.find(Engines.class, id);
    }
}
