package carsale.service;

import carsale.dao.UsersRepository;
import carsale.models.Users;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import static java.util.Objects.isNull;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Users save(Users user) {
        try {
            validateUsers(user);
        } catch (ValidationException vae) {
            vae.printStackTrace();
        }
        return usersRepository.save(user);
    }

    @Override
    @Transactional
    public Users loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = usersRepository.getByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " is not found");
        }
        return user;
    }

    @Transactional
    public void removeById(int userId) {
        usersRepository.removeById(userId);
    }

    @Transactional
    public List<Users> getAll() {
        return usersRepository.getAll();
    }

    @Transactional
    public Users getByLoginPass(String username, String pass) {
         return usersRepository.getByLoginPass(username, pass);
    }

    @Transactional
    public Users getUserById(int id) {
        return usersRepository.getUserById(id);
    }

    @Transactional
    public boolean isLoginFree(String username) {
        return usersRepository.isLoginFree(username);
    }

    private void validateUsers(Users user) throws ValidationException {
        if (isNull(user)) {
            throw new ValidationException("User is null");
        }
        if (isNull(user.getUsername()) || user.getUsername().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
    }
}
