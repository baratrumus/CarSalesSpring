package carsale.controller;

import carsale.config.SecurityConfig;
import carsale.models.Users;
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
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            tokenRepository.removeUserTokens(auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            new CookieClearingLogoutHandler(SecurityConfig.REMEMBER_ME_COOKIE).logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }


    @GetMapping(value = "/signup")
    public String signUpForm(Model model) {
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
                    //request.getSession().setAttribute("roleName", user.getRole().getRoleName());
                    request.getSession().setAttribute("id", user.getId());
                }
            }
            //model.addAttribute("listOfAds", adsService.getAll());
            return "list";
        } else {
            model.addAttribute("error", "Login is not free.");
            return "signup";
        }
    }
}
