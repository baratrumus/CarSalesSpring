package carsale.servlets;

import carsale.controller.AdsController;
import carsale.controller.ControllerTemp;
import carsale.models.Ads;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAdServlet  extends HttpServlet {

    private final ControllerTemp controller = AdsController.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(EditAdServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String adId = req.getParameter("adId");
        Ads ad = controller.getAdById(adId);
        String aaaa = ad.getDescr();
        req.setAttribute("ad", ad);
        req.setAttribute("adEdited", "no");
        req.getRequestDispatcher("/WEB-INF/views/updateAd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext context = this.getServletConfig().getServletContext();
        // устанавливаем временную директорию
        File repository = (File) context.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);

        Map<String, String> textParams = new HashMap<>();
        byte[] byteArr = new byte[]{};
        try {
            //получаем  параметры
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    byteArr = streamToByteArray(item);
                    //byteArr = IOUtils.toByteArray(item.getInputStream());
                }
            }
            //получаем текстовые параметры
            items.stream().filter(FileItem::isFormField).forEach(i -> textParams.put(i.getFieldName(), i.getString()));
        } catch (FileUploadException e) {
            System.out.println(e.getMessage());
            this.LOG.error(e.getMessage(), e);
        }

        String adId = textParams.get("adId");
        Ads ad = controller.getAdById(adId);

        ad.setDescr(textParams.get("description"));
        ad.setPrice(textParams.get("price"));
        ad.setSold(Boolean.valueOf(textParams.get("isSold")));
        if (byteArr.length != 0) {
            ad.setPhoto(byteArr);
        }

        if (controller.updateItem(ad)) {
            req.setAttribute("adEdited", "yes");
        }
        req.setAttribute("listOfAds",  controller.getAllItems());

        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }


    /**
     *  Стрим в байтовый массив
     */
    private byte[] streamToByteArray(FileItem file) throws IOException {
        InputStream is = file.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = is.read();
        while (reads != -1) {
            baos.write(reads);
            reads = is.read();
        }
        return baos.toByteArray();
    }
}
