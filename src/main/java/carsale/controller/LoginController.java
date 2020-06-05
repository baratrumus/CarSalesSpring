package carsale.controller;

import carsale.config.SecurityConfig;
import carsale.models.Ads;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private AdsService adsService;

    @Autowired
    public LoginController(PersistentTokenRepository tokenRepository, UsersService usersService, AdsService as) {
        this.tokenRepository = tokenRepository;
        this.usersService = usersService;
        this.adsService = as;
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
        return "signup";
    }


    @PostMapping("/signup")
    public String signUp(Model model, HttpServletRequest request,
                         @ModelAttribute("userForm") Users user) {

        String login = user.getUsername();

        if (usersService.isLoginFree(login)) {
            usersService.save(user);
            if (user.getId() != null) {
                model.addAttribute("user", user);

            }
            model.addAttribute("listOfAds", adsService.getAll());
            return "list";
        } else {
            model.addAttribute("error", "Login is not free.");
            return "signup";
        }
    }
}
