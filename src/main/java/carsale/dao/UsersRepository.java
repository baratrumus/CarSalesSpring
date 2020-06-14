package carsale.dao;

import carsale.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

//Exception will be translated into subclasses of Spring's DataAccessExeption
@Repository
public class UsersRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UsersRepository.class);
    //private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    RoleRepository roleRepository;

    /**
     * Create user
     */
   @Transactional
    public Users save(Users user) {
        em.persist(user);
        return user;
    }

    /**
     * update user
     */
    public Users update(Users user) {
        return em.merge(user);
    }


    public boolean removeById(int id) {
        Users user = em.find(Users.class, id);
        if (user != null) {
            em.remove(user);
            return true;
        }
        return false;
    }


    public List<Users> getAll() {
        Query query = em.createQuery("from Users");
        List<Users> res = query.getResultList();
        for (Users u : res) {
            LOG.info("User: " + u.toString());
        }
        return res;
    }


    public Users getByLogin(String username) {
        Users user = null;
        Query query = em.createQuery("from Users" +
                " where username = :paramLogin");
        query.setParameter("paramLogin", username);
        List list = query.getResultList();
        if (list.size() != 0) {
            user = (Users) list.get(0);
        }
        return user;
    }


    public Users getUserById(int id) {
        Users user = em.find(Users.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Can't find User for ID "
                    + id);
        }
        return user;
    }


    public boolean isLoginFree(String username) {
        Query query = em.createQuery("from Users where username = :paramName");
        query.setParameter("paramName", username);
        boolean res = query.getResultList().isEmpty();
        return res;
    }


}
