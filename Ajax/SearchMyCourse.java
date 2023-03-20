/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajax;

import dal.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchMyCourse", urlPatterns = {"/SearchMyCourse"})
public class SearchMyCourse extends HttpServlet {

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
        String id = request.getParameter("id");
        String text = request.getParameter("text");
        CourseDAO dao = new CourseDAO();
        ArrayList<Course> courses = dao.searchMyCourses(text, id);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.print("<div class=\"mb-3\">\n"
                    + "            <i class=\"fa-solid fa-magnifying-glass\"></i> Result for \"" + text + "\"\n"
                    + "           \n"
                    + "        </div>\n"
                    + "        <h6 class=\"mb-4 pb-2\" style=\"border-bottom: solid rgba(128, 128, 128, 0.201) 1px;\">\n"
                    + "                Search in my courses...\n"
                    + "        </h6>");
            if (courses.size() == 0) {
                out.print("<div>No course found...</div>");
            }
            for (Course o : courses) {
                out.print(
                        "          <a class=\"card-search searchcourse align-items-center py-1 d-flex\" href=\"#about\">\n"
                        + "              <img style=\"height:60px; width:100px;\" class=\"img-fluid\" src=\"" + o.getThumbnail() + "\">\n"
                        + "              <div class=\"mx-2 pt-1\" >\n"
                        + "              <h6 class=\"card-title\">" + o.getName() + "</h6>\n"
                        + "              <p class=\"card-text\" >" + o.getTagline() + "</p>\n"
                        + "                </div>\n"
                        + "          </a>\n"
                        + "          ");
            }
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
        processRequest(request, response);
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
