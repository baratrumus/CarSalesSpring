package carsale.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import carsale.controller.AdsController;
import carsale.controller.ControllerTemp;
import carsale.models.Ads;
import carsale.models.Car;
import carsale.models.Models;
import carsale.models.Users;
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
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAdServlet extends HttpServlet {
    private final ControllerTemp controller = AdsController.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(CreateAdServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("userCreated", "no");
        req.setAttribute("adCreated", "no");
        req.setAttribute("brandList", controller.getBrands());
        req.setAttribute("bodyList", controller.getBodies());
        req.setAttribute("engineList", controller.getEngines());
        String brandId = req.getParameter("brandId");
        if (brandId != null) {
            List<Models> modelsList =  controller.getModelsById(Integer.parseInt(brandId));
            resp.setContentType("application/json");
            PrintWriter pr = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(modelsList);
            pr.write(jsonInString);
            pr.flush();
        } else {
            req.getRequestDispatcher("/WEB-INF/views/newad.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        resp.setContentType("text/html");
        ServletContext context = this.getServletConfig().getServletContext();
        DiskFileItemFactory factory = new DiskFileItemFactory();

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

        Users user = controller.getUserById(session.getAttribute("id").toString());
        Timestamp dateNow = new Timestamp(System.currentTimeMillis());

        Car carDetails = new Car(controller.getBrandById(textParams.get("brands")),
                                 controller.getModelById(textParams.get("xml/models")),
                                 controller.getBodyById(textParams.get("body")),
                                 controller.getEngineById(textParams.get("engine")),
                                 textParams.get("caryear"),
                                 textParams.get("color"));

        boolean isCar = controller.createCar(carDetails);

        Ads ad = new Ads(user, carDetails, textParams.get("description"),
               dateNow, false, byteArr, textParams.get("price"));

        if (controller.addItem(ad)) {
            req.setAttribute("adCreated", "yes");
        }
        req.setAttribute("listOfAds",  controller.getAllItems());

        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        //resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    private Blob byteToBlob(byte[] ba) throws IOException {
        Blob blob = null;
        try {
            blob = new SerialBlob(ba);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
            this.LOG.error(se.getMessage(), se);
        }
        return blob;
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
