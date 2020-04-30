package carsale.servlets;

import carsale.controller.AdsController;
import carsale.controller.ControllerTemp;
import carsale.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowUsersServlet extends HttpServlet {

    private final ControllerTemp controller = AdsController.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(ShowAdsServlet.class);

    /**
     * list items
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("userEdited", "no");
        req.setAttribute("listOfUsers", controller.getUsers());
        req.getRequestDispatcher("/WEB-INF/views/editUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Users user = controller.getUserById(req.getParameter("delId"));
        if (user != null) {
            if (controller.removeUser(user)) {
                req.setAttribute("userDeleted", "yes");
            }
        }
        doGet(req, resp);
    }
}
