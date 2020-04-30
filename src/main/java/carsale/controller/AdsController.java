package carsale.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import carsale.data.DbHibernate;
import carsale.data.DbTmp;
import carsale.models.*;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMethod;
//data org.springframework.context.annotation.;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Controller
//@RequestMapping("/")
public class AdsController  implements ControllerTemp {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AdsController.class);
    private static final DbTmp STORAGE = DbHibernate.getInstance();




    /*
    @ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.clinic.getPetTypes();
	}
	либо

	@RequestMapping(value = "/pets", method = RequestMethod.POST)
      public void addPet(@ModelAttribute Pet pet) {
        this.pets.add(pet);
        return "redirect:pets.do";
      }

@ModelAttribute Pet pet --- атрибуты будут извлечены из параметров запроса и если они залезут в конструктор - будет создана автоматически модель.

     */




    public boolean addItem(Ads ads) {
        return STORAGE.addNewItem(ads);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllItems(ModelMap map) {
        map.addAttribute("listOfAds", STORAGE.getAllItems());
        return "/";
    }

    public boolean removeItem(Ads ads) {
        return STORAGE.removeItem(ads);
    }


    @GetMapping(value = "/emplouuuusers")
    public boolean updateItem(Ads ads) {
        return STORAGE.updateItem(ads);
    }



    public boolean isLoginFree(String login) {
        return STORAGE.isLoginFree(login);
    }




    @Override
    public boolean createCar(Car car) {
        return STORAGE.createCar(car);
    }



    @Override
    public Ads getAdById(String id) {
        return STORAGE.getAdById(Integer.parseInt(id));
    }

    @Override
    public Users getUserById(String id) {
        return STORAGE.getUserById(Integer.parseInt(id));
    }

    @Override
    public Brands getBrandById(String id) {
        return STORAGE.getBrandById(Integer.parseInt(id));
    }

    @Override
    public Models getModelById(String id) {
        return STORAGE.getModelById(Integer.parseInt(id));
    }

    @Override
    public BodyType getBodyById(String id) {
        return STORAGE.getBodyById(Integer.parseInt(id));
    }

    @Override
    public Engines getEngineById(String id) {
        return STORAGE.getEngineById(Integer.parseInt(id));
    }

    @Override
    public List<Roles> getAllRoles() {
        return STORAGE.getAllRoles();
    }

    @Override
    public List<Brands> getBrands() {
        return STORAGE.getBrands();
    }

    @Override
    public List<Models> getModelsById(int brandId) {
        return STORAGE.getModelsById(brandId);
    }

    @Override
    public List<BodyType> getBodies() {
        return STORAGE.getBodies();
    }

    @Override
    public List<Engines> getEngines() {
        return STORAGE.getEngines();
    }


   /* @Override
    public Boolean changeItemDone(int itemId, boolean state) {
        return STORAGE.changeItemDone(itemId, state);
    }*/
}
