package carsale.servlets;

import carsale.controller.AdsController;
import carsale.controller.ControllerTemp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import carsale.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(SignUpServlet.class);
    private final ControllerTemp controller = AdsController.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("userCreated", null);
        req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        //ServletContext context = this.getServletConfig().getServletContext();
        String login = req.getParameter("login");

        if (controller.isLoginFree(login)) {
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");

            Users user = new Users(login, password, email, phone);
            user.setRole(controller.getAllRoles().get(1));

       /*
        String email1 = URLDecoder.decode(paramMap.get("email"), "UTF-8");
        Integer login1 = Integer.parseInt(paramMap.get("login"));
        */
            Integer createdId = controller.createUser(user);
            if (createdId != null) {
                req.setAttribute("userCreated", user);
                if (!session.getAttribute("roleName").equals("Admin")) {
                    session.setAttribute("login", login);
                    session.setAttribute("roleName", user.getRole().getRoleName());
                    session.setAttribute("id", user.getId());
                }
            }
            req.setAttribute("listOfAds", controller.getAllItems());
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Login is not free.");
            doGet(req, resp);
        }

    }
}
