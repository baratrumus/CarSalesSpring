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
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Map;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Controller
@RequestMapping("/users")
public class UsersController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AdsController.class);
    private UsersService usersService;


    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(value = "/signup")
    public String signUpForm(Model model) {
        model.addAttribute("userForm", new Users());
        return "signup";
    }


    @PostMapping("/signup")
    public String signUp(@ModelAttribute("userForm") Users userForm, Model model) {

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passError", "passes are different");
            return "signup";
        }
        if (!usersService.isLoginFree(userForm.getUsername())) {
            model.addAttribute("bizyNameError", "this name is bizy");
            return "signup";
        }
        Users user = usersService.save(userForm);
        if (user.getId() != null) {
            return "redirect:/login?created=true";
        } else {
            model.addAttribute("DBerror", "Database error");
            return "signup";
        }
    }


    @GetMapping("/admin")
    public String showUsers(Model model) {
        model.addAttribute("listOfUsers", usersService.getAll());
        model.addAttribute("userEdited", "no");
        return "admin";
    }


    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable int id, HttpServletRequest request, Model model) {
        String cameFromAdm = request.getParameter("cameFromAdm");
        model.addAttribute("cameFromAdm", cameFromAdm);

        Users user = usersService.getUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }


    @PostMapping("/update")
    public String update(Model model, @RequestParam Map<String, String> allParams,
                         HttpServletRequest request) {

        int userId = Integer.parseInt(allParams.get("editedUserId"));
        Users baseUser = usersService.getUserById(userId);
        model.addAttribute("user", baseUser);

        if (!allParams.get("password").equals(allParams.get("passwordConfirm"))) {
            model.addAttribute("passError", "passes are different");
            return "updateUser";
        }
        if (!baseUser.getUsername().equals(allParams.get("username")) && !usersService.isLoginFree(allParams.get("username"))) {
            model.addAttribute("bizyNameError", "this name is bizy");
            return "updateUser";
        }

        Boolean passUpdated = (!baseUser.getPassword().equals(allParams.get("password")));
        baseUser.setUsername(allParams.get("username"));
        baseUser.setEmail(allParams.get("email"));
        baseUser.setPhone(allParams.get("phone"));
        if (passUpdated) {
            baseUser.setPassword(allParams.get("password"));
        }

        usersService.update(baseUser, passUpdated);
        request.getSession().setAttribute("uEdited", "yes");
        if (!allParams.get("cameFromAdm").equals("")) {
            return "redirect:/admin";
        } else {
            return "redirect:/";
        }
    }



    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        Users user = usersService.getUserById(id);
        if (user != null) {
            usersService.removeById(id);
            model.addAttribute("userDeleted", "yes");
        }
        return "editUsers";
    }

}
