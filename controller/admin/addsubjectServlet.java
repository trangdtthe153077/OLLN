/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.Category_SubCategory_CourseDAO;
import dal.CategorycourseDAO;
import dal.CourseDAO;
import dal.CoursestatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CategoryCourse;
import model.Course;
import model.CourseStatus;
import model.User;

/**
 *
 * @author Laptop88
 */
public class addsubjectServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            CourseDAO cd = new CourseDAO();
            List<CategoryCourse> listcategory = cd.getAllCategoryCourse();
            List<CourseStatus> liststatus = cd.getCourseStatus();
            request.setAttribute("listcategory", listcategory);
            request.setAttribute("liststatus", liststatus);
            request.getRequestDispatcher("addnewsubject.jsp").forward(request, response);
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
        int categoryid = Integer.parseInt(request.getParameter("categoryid"));
        int status = Integer.parseInt(request.getParameter("status"));
        String name = request.getParameter("name");
        String thumbnail = request.getParameter("thumbnail");
        String tagline = request.getParameter("tagline");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean featuresubject = Boolean.parseBoolean(request.getParameter("featuresubject"));
        HttpSession session = request.getSession();
        User acc = (User) session.getAttribute("account");
        CourseDAO cd = new CourseDAO();
        Category_SubCategory_CourseDAO ccd = new Category_SubCategory_CourseDAO();
        CoursestatusDAO csd = new CoursestatusDAO();
        Course newcourse = new Course(ccd.getCategory_SubCategoryCourseById(categoryid), csd.getCourseStatusById(status), name, thumbnail, tagline, title, acc, description, featuresubject);
        cd.AddSubject(newcourse);
        response.sendRedirect("subjectlist");
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
