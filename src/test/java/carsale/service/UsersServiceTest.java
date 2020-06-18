package carsale.service;

import carsale.config.ApplicationConfig;
import carsale.models.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
public class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    private Users user;


    @Test
    //@WithMockUser
    public void save() {
        user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        usersService.save(user);
        List<Users> result = usersService.getAll();
        assertTrue(result.contains(user));
        usersService.removeById(user.getId());
    }


    @Test
    public void update() {
        user = usersService.loadUserByUsername("user");
        user.setEmail("user333@mail.nu");
        user = usersService.update(user, false);
        assertThat(user.getEmail(), is("user333@mail.nu"));
    }


    @Test
    public void removeById() {
        user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        usersService.save(user);
        int userId = user.getId();
        usersService.removeById(userId);
        var thrown = assertThrows(EntityNotFoundException.class, () -> usersService.getUserById(userId));
        String message = "Can't find User for ID " + userId;
        assertEquals(message, thrown.getMessage());
    }

    @Test
    public void loadUserByUsername() {
        Users auth = usersService.loadUserByUsername("user");
        assertThat(auth, is(Users.class));
    }

    @Test
    public void loadUserByUsernameNotFound() {
        var thrown = assertThrows(UsernameNotFoundException.class, () -> usersService.loadUserByUsername("UnKnownUser"));
        assertEquals("User UnKnownUser is not found", thrown.getMessage());
    }




    @Test
    public void getAll() {
        List<Users> urs = usersService.getAll();
        assertEquals(urs.size(), 2);
    }


    @Test
    public void getUserById() {
        user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        usersService.save(user);
        int userId = user.getId();
        Users resUser = usersService.getUserById(userId);
        assertEquals(user, resUser);
        usersService.removeById(userId);
    }


    @Test
    public void isLoginFree() {
        user = new Users("testUser", "1111", "1111", "pop@mail.com", "+79892131212");
        usersService.save(user);
        int userId = user.getId();
        Boolean res = usersService.isLoginFree("testUser");
        assertFalse(res);
        usersService.removeById(userId);
    }
}