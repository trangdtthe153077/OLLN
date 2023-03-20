/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.CourseDAO;
import dal.LevelDAO;
import dal.QuizzesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import model.QLevel;
import model.Quizzes;

/**
 *
 * @author win
 */
public class QuizzesDetailController extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QuizzesDetailController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuizzesDetailController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);
        int id = Integer.parseInt(request.getParameter("id"));
        QuizzesDAO qdao = new QuizzesDAO();
        Quizzes q = qdao.getQuizById(id);
        CourseDAO sccdao = new CourseDAO();
        List<Course> listSubCategoryCourse = new ArrayList<>();
        listSubCategoryCourse = sccdao.getAllCourse();
        LevelDAO ldao = new LevelDAO();
        List<QLevel> listLevel = new ArrayList<>();
        listLevel = ldao.getAllLevel();
        request.setAttribute("listLevel", listLevel);
        request.setAttribute("listSubCategoryCourse", listSubCategoryCourse);
        request.setAttribute("quizzes", q);
        request.getRequestDispatcher("quizzesdetail.jsp").forward(request, response);
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
        //processRequest(request, response);
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("quizzesname");
        int cid = Integer.parseInt(request.getParameter("subjectselect"));
        int lid = Integer.parseInt(request.getParameter("levelselect"));
        String duration = request.getParameter("duration");
        int passrate = Integer.parseInt(request.getParameter("passrate"));
        int status = Integer.parseInt(request.getParameter("statusselect"));
        String description = request.getParameter("describe");
        QuizzesDAO qdao = new QuizzesDAO();
        qdao.updateQuizzes(id, name, cid, lid, duration, passrate, status, description);
        request.getRequestDispatcher("quizzes").forward(request, response);
        
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
