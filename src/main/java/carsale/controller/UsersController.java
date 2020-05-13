package carsale.controller;

import carsale.models.Users;
import carsale.service.AdsService;
import carsale.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UsersController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AdsController.class);
    private UsersService usersService;
    private AdsService adsService;

    @Autowired
    public UsersController(UsersService usersService, AdsService adsService) {
        this.usersService = usersService;
        this.adsService = adsService;
    }


    @GetMapping(value = "/signup")
    public String signUpForm(Model model) {
        //model.addAttribute("book", new Book());
        return "signup";
    }


    @PostMapping("/signup")
    public String signUp(Model model, @RequestParam Map<String, String> allParams,
                         @RequestBody MultiValueMap<String, String> formData,
                         @ModelAttribute("userForm") Users user,
                         HttpServletRequest request,
                         BindingResult bindingResult) {

        String login = request.getParameter("login");
        String login1 = allParams.get("login");
        String login2 = formData.getFirst("login");

        if (usersService.isLoginFree(login)) {
            usersService.save(user);
            if (user.getId() != null) {
                model.addAttribute("user", user);
                if (!request.getSession().getAttribute("roleName").equals("Admin")) {
                    request.getSession().setAttribute("login", login);
                    request.getSession().setAttribute("roleName", user.getRole().getRoleName());
                    request.getSession().setAttribute("id", user.getId());
                }
            }
            model.addAttribute("listOfAds", adsService.getAll());
            return "list";
        } else {
            model.addAttribute("error", "Login is not free.");
            return "signup";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, HttpServletRequest request, Model model) {
        String cameFromAdm = request.getParameter("cameFromAdm");
        Users user;
        if (cameFromAdm != null) { //пришли из админки
            model.addAttribute("cameFromAdm", "yes");
        }
        user = usersService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userEdited", "no");
        return "updateUser";
    }


    @PostMapping("/update")
    public String signUp(Model model, @ModelAttribute("userForm") Users user,
                         HttpServletRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());

        usersService.save(user);
        model.addAttribute("userEdited", "yes");
        String cameFromAdm = request.getParameter("cameFromAdm");
        if (cameFromAdm != null) {  //пришли их админки юзеров и туда и возвращаемся
            model.addAttribute("listOfUsers", usersService.getAll());
            return "editUsers.jsp";
        } else {    //пришли из личного аккаунта и возвращаемся в лист объявлений
            model.addAttribute("listOfAds", adsService.getAll());
            return "list.jsp";
        }
    }



    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        Users user = usersService.getUserById(id);
        if (user != null) {
            usersService.removeById(id);
            model.addAttribute("userDeleted", "yes");
        }
        return "editUsers";
    }


}
