package carsale.dao;


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
    public Car save(Car car) {
        if (car.getId() == null) {
            em.persist(car);
        } else {
            em.merge(car);
        }
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

    public BodyType getBodyById(int id) {
        return em.find(BodyType.class, id);
    }

    public Brands getBrandById(int id) {
        return em.find(Brands.class, id);
    }

    public Models getModelById(int id) {
        return em.find(Models.class, id);
    }

    public Engines getEngineById(int id) {
        return em.find(Engines.class, id);
    }
}
