package carsale.controller;

import carsale.data.DbTmp;
import carsale.data.DbHibernate;
import carsale.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AdsController.class);
    private static final DbTmp STORAGE = DbHibernate.getInstance();


    public boolean updateUser(Users user) {
        return STORAGE.updateUser(user);
    }


    public boolean removeUser(Users user) {
        return STORAGE.removeUser(user);
    }


    @RequestMapping(value = "/emplouuuusers", method = RequestMethod.GET)
    public List<Users> getUsers() {
        return STORAGE.getUsers();
    }


    @RequestMapping(value = "/employee-module", method = RequestMethod.POST)
    public Integer createUser(Users user) {
        return STORAGE.createUser(user);
    }


    public Users getUserByLoginPass(String login, String pass) {
        Users user = STORAGE.getUserByLoginPass(login, pass);
        return user;
    }
}
