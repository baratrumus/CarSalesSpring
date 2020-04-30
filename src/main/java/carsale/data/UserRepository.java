package carsale.data;

import carsale.models.Users;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

//Exception will be translated into subclasses of Spring's DataAccessExeption
@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Users updateUser(Users user) {
        return em.merge(user);
    }

    @Transactional
    public void removeUser(Users user) {
        //Users user1 = em.getReference(Users, id);
        em.remove(user);
    }

    @Transactional
    public Users createUser(Users user) {
        em.persist(user);
        return user;
    }


    public List<Users> getAllUsers() {
        Query query = em.createQuery("from Users where role_id = :paramName");
        query.setParameter("paramName", 2);
        return query.getResultList();
    }


    public Users getUserByLoginPass(String login, String pass) {
        Users user = null;
        Query query = em.createQuery("from Users" +
                    " where login = :paramLogin and password = :paramPass");
        query.setParameter("paramLogin", login);
        query.setParameter("paramPass", pass);
        List list = query.getResultList();
            if (list.size() != 0) {
                user = (Users) list.get(0);
            }
        return user;
    }


    public Users getUserById(Integer id) {
        Users user = em.find(Users.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Can't find User for ID "
                    + id);
        }
        return user;
    }


    public boolean isLoginFree(String login) {
        Query query = em.createQuery("from Users where login = :paramName");
        query.setParameter("paramName", login);
        boolean res = query.getResultList().isEmpty();
        return res;
    }


}
