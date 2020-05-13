package carsale.dao;

import carsale.models.Roles;
import carsale.models.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager em;

    public Roles save(Roles role) {
        em.persist(role);
        return role;
    }

    public Roles getById(Integer id) {
        Roles role = em.find(Roles.class, id);
        if (role == null) {
            throw new EntityNotFoundException("Can't find Role for ID "
                    + id);
        }
        return role;
    }

    public List<Roles> getAll() {
        return em.createQuery("from Roles").getResultList();
    }


    /*
     List<Post> posts = session.createQuery(
        "select distinct p " +
        "from Post p " +
        "join fetch p.comments c")
    .list();
     */
}
