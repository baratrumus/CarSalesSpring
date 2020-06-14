package carsale.controller;

import carsale.config.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private PersistentTokenRepository tokenRepository;



    @Autowired
    public LoginController(PersistentTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;

    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        String targetUrl = getRememberMeTargetUrlFromSession(request);
        if (StringUtils.hasText(targetUrl)) {
            model.addAttribute("targetUrl", targetUrl);
        }
        return "login";
    }


    @PostMapping("/auth")
    public String makeAuthForTestRoles(HttpServletRequest request) {
        String f = request.getParameter("login");
        String r = f + 1;
        try {
            request.login(request.getParameter("login"), "1");
        } catch (ServletException e) {
            LOG.error("Error while login ", e);
        }

        return "redirect:/";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("userId", null);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            tokenRepository.removeUserTokens(auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            new CookieClearingLogoutHandler(SecurityConfig.REMEMBER_ME_COOKIE).logout(request, response, auth);
        }
        return "redirect:/?logout=true";
    }

    /**
     * Check if user is login by remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     */
    private boolean isRememberMeAuthenticated() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    /**
     * save targetURL in session
     */
    private void setRememberMeTargetUrlToSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("targetUrl", "/");
        }
    }

    /**
     * get targetURL from session
     */
    private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
        String targetUrl = "";
        HttpSession session = request.getSession(false);
        if (session != null) {
            targetUrl = session.getAttribute("targetUrl") == null ? ""
                     : session.getAttribute("targetUrl").toString();
        }
        return targetUrl;
    }



}
