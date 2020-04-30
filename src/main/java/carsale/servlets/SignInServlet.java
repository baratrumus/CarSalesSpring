package carsale.servlets;

import carsale.controller.AdsController;
import carsale.controller.ControllerTemp;
import carsale.models.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final ControllerTemp controller = AdsController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        //autentification
        Users u = controller.getUserByLoginPass(login, password);
        if (u != null) {
            HttpSession session = req.getSession();
            //synchronized (session) {
                session.setAttribute("login", login);
                session.setAttribute("roleName", u.getRole().getRoleName());
                session.setAttribute("id", u.getId());
            //}
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("error", "Invalid crefentionals");
            doGet(req, resp);
        }
    }
}
