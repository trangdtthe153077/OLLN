/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.QuestionDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Course;
import model.Dimension;
import model.Question;
import utils.readExcel;

/**
 *
 * @author DELL
 */
@MultipartConfig
public class ImportQSController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImportQSController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImportQSController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int cid = Integer.parseInt(request.getParameter("courseID"));
            int did = Integer.parseInt(request.getParameter("dimensionID"));
            String path = "H:\\iter3\\Online-Learning-SWP\\web\\upload\\import\\";
            Part filePart = request.getPart("file");
            String filename = filePart.getSubmittedFileName();
//            out.print("<br><br><hr> file name: " + filename);
            OutputStream os = null;
            InputStream is = null;
            try {
                os = new FileOutputStream(new File(path + File.separator + filename));
                is = filePart.getInputStream();
                int read = 0;
                while ((read = is.read()) != -1) {
                    os.write(read);
                }
//                out.print("<br>file uploaded successfully...");
            } catch (IOException e) {
            }
            readExcel re = new readExcel();
            ArrayList<Question> list = re.getListQS(filename);
            QuestionDAO qsDB = new QuestionDAO();
            for (Question q : list) {
                q.setCourse(new Course(cid));
                q.setDimension(new Dimension(did));
                qsDB.insertQuestion(q);
            }
            response.sendRedirect("qsbank");
        } catch (Exception e) {
        }
//        request.setAttribute("message", "Import file successful");
//        request.getRequestDispatcher("qsbank").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
