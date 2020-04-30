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

public class EditUserServlet extends HttpServlet {
    private final ControllerTemp controller = AdsController.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String uId = req.getParameter("userIdfromAdm");
        Users user;
        if (uId != null) { //пришли из админки
            user = controller.getUserById(uId);
            req.setAttribute("userEdited", "no");
            req.setAttribute("userIdfromAdm", uId);
        } else { //из юзер акка
            user = controller.getUserById(session.getAttribute("id").toString());
        }
        req.setAttribute("user", user);
        req.setAttribute("userEdited", "no");
        req.getRequestDispatcher("/WEB-INF/views/updateUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        req.setAttribute("userEdited", "no");

        String uId = req.getParameter("userIdfromAdm");
        Users user;
        if (uId != null) {
            user = controller.getUserById(uId);
        } else {
            user = controller.getUserById(session.getAttribute("id").toString());
        }
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));

        if (controller.updateUser(user)) {
            req.setAttribute("userEdited", "yes");
        }
        if (uId != null) {  //пришли их админки юзеров и туда и возвращаемся
            req.setAttribute("listOfUsers", controller.getUsers());
            req.getRequestDispatcher("/WEB-INF/views/editUsers.jsp").forward(req, resp);
        } else {    //пришли из личного аккаунта и возвращаемся в лист объявлений
            req.setAttribute("listOfAds", controller.getAllItems());
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        }
    }
}
