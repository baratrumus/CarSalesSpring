package carsale.dao;

import carsale.models.Authorities;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Authorities save(Authorities role) {
        em.persist(role);
        return role;
    }
}
