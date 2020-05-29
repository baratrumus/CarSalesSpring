package carsale.service;

import carsale.dao.UsersRepository;
import carsale.models.Users;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users save(Users user) {
        try {
            validateUsers(user);
        } catch (ValidationException vae) {
            vae.printStackTrace();
        }
        return usersRepository.save(user);
    }

    public void removeById(int userId) {
        usersRepository.removeById(userId);
    }

    public List<Users> getAll() {
        return usersRepository.getAll();
    }

    public Users getByLoginPass(String login, String pass) {
         return usersRepository.getByLoginPass(login, pass);
    }

    public Users getUserById(int id) {
        return usersRepository.getUserById(id);
    }

    public boolean isLoginFree(String login) {
        return usersRepository.isLoginFree(login);
    }

    private void validateUsers(Users user) throws ValidationException {
        if (isNull(user)) {
            throw new ValidationException("User is null");
        }
        if (isNull(user.getLogin()) || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
    }
}
