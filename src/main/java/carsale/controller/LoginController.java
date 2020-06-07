package carsale.controller;

import carsale.config.SecurityConfig;
import carsale.models.Users;
import carsale.service.AdsService;
import carsale.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private PersistentTokenRepository tokenRepository;
    private UsersService usersService;


    @Autowired
    public LoginController(PersistentTokenRepository tokenRepository, UsersService usersService) {
        this.tokenRepository = tokenRepository;
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            tokenRepository.removeUserTokens(auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            new CookieClearingLogoutHandler(SecurityConfig.REMEMBER_ME_COOKIE).logout(request, response, auth);
        }
        return "redirect:/?logout=true";
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
}
