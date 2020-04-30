package carsale.controller;

import carsale.models.*;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ControllerTemp {
    boolean addItem(Ads ads);
    boolean updateItem(Ads ads);
    boolean removeItem(Ads ads);
    String getAllItems(ModelMap model);
    Ads getAdById(String id);

    List<Users> getUsers();
    boolean removeUser(Users user);
    Users getUserById(String id);
    Users getUserByLoginPass(String login, String pass);
    Integer createUser(Users user);
    boolean updateUser(Users user);
    boolean createCar(Car car);

    List<Roles> getAllRoles();
    List<Brands> getBrands();
    List<Models> getModelsById(int brandId);
    List<BodyType> getBodies();
    List<Engines> getEngines();

    boolean isLoginFree(String login);


    Brands getBrandById(String id);
    Models getModelById(String id);
    BodyType getBodyById(String id);
    Engines getEngineById(String id);

}
