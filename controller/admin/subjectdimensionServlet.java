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
import dal.SubCategoryCourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CategoryCourse;
import model.Course;
import model.CourseStatus;
import model.Dimension;
import model.Price_Package;
import model.SubCategoryCourse;

/**
 *
 * @author ADMIN
 */
public class subjectdimensionServlet extends HttpServlet {

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

        DimensionDAO dimensiondao = new DimensionDAO();

        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {

            ArrayList<Dimension> dimensions = dimensiondao.getAllCourseDimensions(1, 10, "");
            int total = dimensiondao.getAllTotal("");

            if (total % 10 != 0) {
                total = total / 10 + 1;
            } else if (total == 0) {
                total = 1;
            } else {
                total = total / 10;
            }
            int pagetotal = total;

            request.setAttribute("total", pagetotal);
            request.setAttribute("dimensions", dimensions);

        } else if (action != null && action.equals("deletedimension")) {

            dimensiondao.deleteSubjectDimension(id);

            response.sendRedirect("subjectdimension");
            return;
        }
        request.getRequestDispatcher("subjectdimension.jsp").forward(request, response);
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

        CourseDAO dao = new CourseDAO();
        DimensionDAO dimensiondao = new DimensionDAO();

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action.equals("edit")) {
            String type = request.getParameter("type");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            dimensiondao.editDimension(id, type, name, description);

        }
        else if (action.equals("addnew")) {
            String type = request.getParameter("type");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            dimensiondao.addNewSubjectDimension(type, name, description);

        }
        response.sendRedirect("subjectdimension");
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

