package carsale.dao;

import carsale.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

//Exception will be translated into subclasses of Spring's DataAccessExeption
@Repository
public class UsersRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UsersRepository.class);
    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager em;

    public UsersRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /**
     * Create or update user
     */
    @Transactional
    public Users save(Users user) {
        if (user.getId() == null) {
            user.setRole(roleRepository.getById(1));
            em.persist(user);
        } else {
            em.merge(user);
        }
        return user;
    }

    @Transactional
    public void removeById(int id) {
        Users user = em.find(Users.class, id);
        em.remove(user);
    }


    public List<Users> getAll() {
        Query query = em.createQuery("from Users where role_id = :paramName");
        query.setParameter("paramName", 2);
        List<Users> res = query.getResultList();
        for (Users u : res) {
            LOG.info("User: " + u.toString());
        }
        return res;
    }


    public Users getByLoginPass(String login, String pass) {
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


    public Users getUserById(int id) {
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
