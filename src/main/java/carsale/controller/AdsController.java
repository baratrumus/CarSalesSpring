package carsale.controller;


import carsale.service.AdsService;
import carsale.service.CarsService;
import carsale.service.UsersService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import carsale.models.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;



/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Controller
public class AdsController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AdsController.class);
    private AdsService adsService;
    private CarsService carsService;
    private UsersService usersService;

    @Autowired
    public AdsController(AdsService adsService, CarsService carsService, UsersService usersService) {
        this.adsService = adsService;
        this.carsService = carsService;
        this.usersService = usersService;
    }

    @GetMapping(value = "/")
    public String showAds(Model model, HttpServletRequest request) {
        //filters
        String onlyUserAds = request.getParameter("onlyUserAds");
        String lastDay = request.getParameter("lastDay");
        String inSale = request.getParameter("inSale");
        String withPhoto = request.getParameter("withPhoto");
        String checkBrand = request.getParameter("checkBrand");
        String brandId = request.getParameter("brands");

        model.addAttribute("adDeleted", "no");
        model.addAttribute("brandList", carsService.getBrands());
        model.addAttribute("brandId", brandId);
        model.addAttribute("checkBrand", checkBrand);
        model.addAttribute("withPhoto", withPhoto);
        model.addAttribute("onlyUserAds", onlyUserAds);
        model.addAttribute("lastDay", lastDay);
        model.addAttribute("inSale", inSale);
        model.addAttribute("withPhoto", withPhoto);

        List<Ads> filterList = adsService.getFilteredList((Integer)request.getSession().getAttribute("id"),
                onlyUserAds, lastDay, inSale, withPhoto, checkBrand, brandId);

        if (request.getSession().getAttribute("login") == null) {
            request.getSession().setAttribute("login", "Guest");
        }
        if (request.getSession().getAttribute("roleName") == null) {
            request.getSession().setAttribute("roleName", "Guest");
        }
        request.setAttribute("listOfAds", filterList);
        return "list";
    }


    @DeleteMapping (value = "/")
    public String delete(HttpServletRequest request) {
        Ads ad = adsService.getById(Integer.parseInt(request.getParameter("delId")));
        if (ad != null) {
            adsService.removeById(ad.getId());
            request.setAttribute("adDeleted", "yes");
        }
        return "redirect:/";
    }


    //ajax
    @GetMapping (value = "/ad/getModels")
    @ResponseBody
    public List<Models> getModelsByBrand(HttpServletRequest req, @RequestParam("brandId") String brandId,
                            @RequestBody String brandId22) {
        List<Models> retList;
        String brandId55 = brandId;
        String brandId33 = req.getParameter("brandId");
        String look = brandId22;
        //resp.setContentType("application/json");
        if (brandId != null) {
            retList = carsService.getModelsByBrandId(Integer.parseInt(brandId));
        } else {
            retList = carsService.getModelsByBrandId(1);
        }
        return retList;
    }



    @GetMapping (value = "/ad/create")
    public String showCreate (Model model) {
        model.addAttribute("userCreated", "no");
        model.addAttribute("adCreated", "no");
        model.addAttribute("brandList", carsService.getBrands());
        model.addAttribute("bodyList", carsService.getBodies());
        model.addAttribute("engineList", carsService.getEngines());
        return  "newad";
    }



    @PostMapping (value = "/ad/create")
    public String create (Model model, HttpServletRequest request,
                         @RequestParam("brands") int brands,
                         @RequestParam int models,
                         @RequestParam int body,
                         @RequestParam int engine,
                         @RequestParam String caryear,
                         @RequestParam String color,
                         @RequestParam String description,
                         @RequestParam String price,
                         @RequestParam(name = "file", required = false) MultipartFile file) {

        byte[] byteArr = fileToByteArray(file);
        Users user = usersService.getUserById((Integer)request.getSession().getAttribute("id"));
        Timestamp dateNow = new Timestamp(System.currentTimeMillis());

        Car car = new Car(carsService.getBrandById(brands),
                                carsService.getModelById(models),
                                carsService.getBodyById(body),
                                carsService.getEngineById(engine),
                                caryear, color);

        if (carsService.create(car).getId() == null) {
            model.addAttribute("error", "Error in car creation");
            LOG.error("Error in car creation");
            return "newad";
        }

        Ads ad = new Ads(user, car, description, dateNow, byteArr, price);

        if (adsService.save(ad).getId() == null) {
            model.addAttribute("error", "Error in advertisment creation");
            LOG.error("Error in advertisment creation");
            return "newad";
        }
        model.addAttribute("listOfAds",  adsService.getAll());
        model.addAttribute("adCreated", "yes");
        return "list";
    }


    private byte[] fileToByteArray(MultipartFile multipartFile) {
        byte[] bArr = new byte[]{};;
        if (Objects.nonNull(multipartFile)) {
            try (InputStream is = multipartFile.getInputStream()) {
                bArr = IOUtils.toByteArray(is);
            } catch (IOException e) {
                LOG.error("File saving exception", e);
            }
        }
        return bArr;
    }


    @GetMapping("ad/update/{adId}")
    public String showUpdate(@PathVariable String adId, Model model) {
        Ads ad = adsService.getById(Integer.parseInt(adId));
        model.addAttribute("ad", ad);
        model.addAttribute("adEdited", "no");
        return "updateAd";
    }


    @PostMapping (value = "/ad/update/{adId}")
    public String update(Model model,
                        @PathVariable String adId,
                        @RequestParam String isSold,
                        @RequestParam String description,
                        @RequestParam String price,
                        @RequestParam(name = "file", required = false) MultipartFile file) {

        byte[] byteArr = fileToByteArray(file);
        Ads ad = adsService.getById(Integer.parseInt(adId));
        ad.setDescr(description);
        ad.setPrice(price);
        ad.setSold(Boolean.valueOf(isSold));
        if (byteArr.length != 0) {
            ad.setPhoto(byteArr);
        }

        adsService.save(ad);
        model.addAttribute("adEdited", "yes");
        model.addAttribute("listOfAds",  adsService.getAll());
        return "list";
    }


}
