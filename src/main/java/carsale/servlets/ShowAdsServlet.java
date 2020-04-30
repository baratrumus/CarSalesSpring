package carsale.servlets;

import carsale.controller.AdsController;
import carsale.controller.ControllerTemp;
import carsale.models.Ads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

public class ShowAdsServlet extends HttpServlet {

    private final ControllerTemp controller = AdsController.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(ShowAdsServlet.class);

    /**
     * list items
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        req.setAttribute("adDeleted", "no");

        //filters
        String[] onlyUserAds = req.getParameterValues("onlyUserAds");
        String[] lastDay = req.getParameterValues("lastDay");
        String[] inSale = req.getParameterValues("inSale");
        String[] withPhoto = req.getParameterValues("withPhoto");
        String checkBrand = req.getParameter("checkBrand");
        String brandId = req.getParameter("brands");

        req.setAttribute("brandList", controller.getBrands());
        req.setAttribute("brandId", brandId);
        req.setAttribute("checkBrand", checkBrand);
        req.setAttribute("withPhoto", withPhoto);
        req.setAttribute("onlyUserAds", onlyUserAds);
        req.setAttribute("lastDay", lastDay);
        req.setAttribute("inSale", inSale);
        req.setAttribute("withPhoto", withPhoto);

        List<Ads> listOfAds = controller.getAllItems();

        List<Ads> filterList = new ArrayList<>();
        filterList.addAll(listOfAds);
        LocalDate now = LocalDate.now();

        for (Ads ad : listOfAds) {
            if (onlyUserAds != null) {
                if (ad.getUserId().getId() != session.getAttribute("id")) {
                    filterList.remove(ad);
                }
            }
            if (lastDay != null) {
                LocalDate adDate = ad.getCreated().toLocalDateTime().toLocalDate();
                if (now.compareTo(adDate) != 0) {
                    filterList.remove(ad);
                }
            }
            if (inSale != null) {
                if (ad.getSold()) {
                    filterList.remove(ad);
                }
            }
            if (withPhoto != null) {
                if (ad.getPhoto().length == 0) {
                    filterList.remove(ad);
                }
            }
            if (checkBrand != null) {
                if (ad.getCarDetails().getBrand().getId() != Integer.parseInt(brandId)) {
                    filterList.remove(ad);
                }
            }
        }

        if (session.getAttribute("login") == null) {
            session.setAttribute("login", "Guest");
        }
        if (session.getAttribute("roleName") == null) {
            session.setAttribute("roleName", "Guest");
        }
        req.setAttribute("listOfAds", filterList);
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

    /**
     * remove ad
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Ads ad = controller.getAdById(req.getParameter("delId"));
        if (ad != null) {
            if (controller.removeItem(ad)) {
                req.setAttribute("adDeleted", "yes");
            }
        }
        doGet(req, resp);
    }


}