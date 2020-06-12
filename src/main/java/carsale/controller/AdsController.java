package carsale.controller;


import carsale.service.AdsService;
import carsale.service.CarsService;
import carsale.service.FormDataWithFile;
import carsale.service.UsersService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import carsale.models.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.http.HttpServletRequest;



/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Integer userId = (auth.getPrincipal().toString() != "anonymousUser") ?
                usersService.loadUserByUsername(auth.getName()).getId() : null;

        request.getSession().setAttribute("userId", userId);

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

        List<Ads> filterList = adsService.getFilteredList(userId, onlyUserAds, lastDay,
                inSale, withPhoto, checkBrand, brandId);

        request.setAttribute("listOfAds", filterList);
        return "list";
    }


    @PostMapping(value = "/ad/delete")
    public String delete(HttpServletRequest request) {
        Ads ad = adsService.getById(Integer.parseInt(request.getParameter("delId")));
        if (ad != null) {
            adsService.removeById(ad.getId());
            request.getSession().setAttribute("adDeleted", "yes");
        }
        return "redirect:/";
    }


    //ajax
    @GetMapping (value = "/ad/getModels", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Models> getModelsByBrand(@RequestParam("brandId") String brandId) {
        return carsService.getModelsByBrandId(Integer.parseInt(brandId));
    }



    @GetMapping (value = "/ad/create")
    public String showCreate(Model model) {
        model.addAttribute("brandList", carsService.getBrands());
        model.addAttribute("bodyList", carsService.getBodies());
        model.addAttribute("engineList", carsService.getEngines());
        return  "newad";
    }


    @PostMapping (value = "/ad/create")
    public String create(@AuthenticationPrincipal UserDetails authorizedUser,
                         HttpServletRequest request, Model model,
                         @ModelAttribute FormDataWithFile formDataWithFile) {

        MultipartFile file = formDataWithFile.getFile();
        Users user = usersService.loadUserByUsername(authorizedUser.getUsername());
        byte[] byteArr = fileToByteArray(file);
        Timestamp dateNow = new Timestamp(System.currentTimeMillis());

        Car car = new Car(carsService.getBrandById(Integer.parseInt(formDataWithFile.getBrands())),
                          carsService.getModelById(Integer.parseInt(formDataWithFile.getModels())),
                          carsService.getBodyById(Integer.parseInt(formDataWithFile.getBody())),
                          carsService.getEngineById(Integer.parseInt(formDataWithFile.getEngine())),
                          formDataWithFile.getCaryear(),
                          formDataWithFile.getColor(),
                          Integer.parseInt(formDataWithFile.getMileage()));

        if (carsService.create(car).getId() == null) {
            model.addAttribute("error", "Error in car creation");
            LOG.error("Error in car creation");
            return "newad";
        }

        Ads ad = new Ads(user, car, formDataWithFile.getDescription(), dateNow, byteArr,
                     Integer.parseInt(formDataWithFile.getPrice()));

        if (adsService.save(ad).getId() == null) {
            model.addAttribute("error", "Error in advertisment creation");
            LOG.error("Error in advertisment creation");
            return "newad";
        }
        request.getSession().setAttribute("adCreated", "yes");
        return "redirect:/";
    }


    private byte[] fileToByteArray(MultipartFile multipartFile) {
        byte[] bArr = new byte[]{};
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
        return "updateAd";
    }


    @PostMapping (value = "/ad/update")
    public String update(Model model,
                        HttpServletRequest request,
                        @ModelAttribute FormDataWithFile formDataWithFile,
                        @RequestParam String adId) {

        MultipartFile file = formDataWithFile.getFile();
        byte[] byteArr = fileToByteArray(file);
        Ads ad = adsService.getById(Integer.parseInt(adId));
        ad.setDescr(formDataWithFile.getDescription());
        ad.setPrice(Integer.parseInt(formDataWithFile.getPrice()));
        ad.setSold(Boolean.valueOf(formDataWithFile.getSold()));
        if (byteArr.length != 0) {
            ad.setPhoto(byteArr);
        }

        adsService.save(ad);
        request.getSession().setAttribute("adEdited", "yes");
        return "redirect:/";
    }


}
