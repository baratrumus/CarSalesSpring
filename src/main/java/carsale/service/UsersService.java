package carsale.service;

import carsale.dao.RoleRepository;
import carsale.dao.UsersRepository;
import carsale.models.Authorities;
import carsale.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.xml.bind.ValidationException;
import java.util.List;


import static java.util.Objects.isNull;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UsersService(UsersRepository usersRepository,
                        PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Users save(Users user) {
        try {
            validateUsers(user);
        } catch (ValidationException vae) {
            vae.printStackTrace();
        }
        if (user.getId() == null) {
            String encoded = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoded);
            user.setEnabled(true);
            usersRepository.save(user);
            Authorities role = new Authorities("ROLE_USER", user.getUsername(), user);
            roleRepository.save(role);
        } else {
            usersRepository.update(user);
        }
        return user;
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
