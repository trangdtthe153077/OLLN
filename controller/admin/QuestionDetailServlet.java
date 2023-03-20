/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.CategorycourseDAO;
import dal.CourseDAO;
import dal.CoursestatusDAO;
import dal.DimensionDAO;
import dal.PricePackageDAO;
import dal.QuestionDAO;
import dal.SubCategoryCourseDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.CategoryCourse;
import model.Course;
import model.Dimension;
import model.Question;
import model.SubCategoryCourse;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
/**
 *
 * @author Laptop88
 */
public class QuestionDetailServlet extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "web\\img";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        SubCategoryCourseDAO subjectdao = new SubCategoryCourseDAO();
        CategorycourseDAO gradedao = new CategorycourseDAO();
        DimensionDAO dimensiondao = new DimensionDAO();
        QuestionDAO qsdao = new QuestionDAO();
        CourseDAO cd = new CourseDAO();
        String qid = request.getParameter("qid");
        String action = null;
        if (action == null) {
            Question qs = qsdao.getQSById(Integer.parseInt(qid));
            Course course = cd.getCourseById2(qs.getCourse().getId());
            List<CategoryCourse> allGrade = gradedao.getAllCategoryCourse();
            ArrayList<SubCategoryCourse> allSubjects = subjectdao.getSubCategoryCourse();
            
            ArrayList<Dimension> alldimension = dimensiondao.getDimension();

            request.setAttribute("qs", qs);
            request.setAttribute("course", course);
            request.setAttribute("grade", allGrade);
            request.setAttribute("subject", allSubjects);

            request.setAttribute("dimension", alldimension);
            request.getRequestDispatcher("questiondetail.jsp").forward(request, response);
            
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String qid = request.getParameter("qid");
        String coursename = request.getParameter("subjectname");
            String gradeselect = request.getParameter("gradeselect");
            String subjectselect = request.getParameter("subjectselect");
            String dimension = request.getParameter("dimension");
            String status = request.getParameter("status");
            String describe = request.getParameter("describe");
            String thumbnail = uploadFile(request, response);
            response.getWriter().print(thumbnail);
            QuestionDAO qd = new QuestionDAO();
            qd.updateQuestion(qid, coursename, gradeselect, subjectselect, dimension, status, describe, thumbnail);
            response.sendRedirect("questiondetail?qid="+qid);
    }
private String uploadFile(HttpServletRequest request, HttpServletResponse res) throws IOException, ServletException {
        String fileName = "";

        PrintWriter out = res.getWriter();
        try {
            Part filePart = request.getPart("thumbnail");

//            fileName = (String) getFileName(filePart);
            fileName = "Coursename" + request.getParameter("qid") + getFileName(filePart);

            if (getFileName(filePart) == null || getFileName(filePart).equals("")) {
//              out.print("test"+getFileName(filePart));
                return "";
            }
//          else
//          {
//              String username = (String) request.getParameter("username");
//              fileName=username+".jpg";
//          }
            out.print("bbbbbb");

            String applicationPath = request.getServletContext().getRealPath("");
            String contextPath = request.getServletContext().getRealPath("");
            out.println(contextPath);

            out.println(applicationPath + "aaaaaaa" + contextPath);
            String basePath = applicationPath + ".." + File.separator + ".." + File.separator + UPLOAD_DIR + File.separator;
//String basePath="C:\\Users\\ADMIN\\Documents\\Kì 5 Summer 2022\\SWP391\\26thang6\\summer2022-se1616-g3-main\\Online-Learning-SWP"+File.separator;
            out.println(basePath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            fileName = "";
        }
        return "img" + File.separator + fileName;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
