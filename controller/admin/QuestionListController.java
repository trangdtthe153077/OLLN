/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.CourseDAO;
import dal.DimensionDAO;
import dal.QuestionDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import model.QLevel;
import model.Question;
import model.Dimension;

/**
 *
 * @author DELL
 */
public class QuestionListController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionDAO qsDB = new QuestionDAO();
        String raw_course = request.getParameter("course");
        String raw_dimension = request.getParameter("dimension");
        String raw_level = request.getParameter("level");
        String raw_status = request.getParameter("status");
//        String raw_order = request.getParameter("orderby");
//        String raw_action = request.getParameter("action");
        int cid, did, lid, status;
//        String action = "", order = "";
        try {
            cid = Integer.parseInt(raw_course);
        } catch (NumberFormatException e) {
            cid = -1;
        }

        try {
            did = Integer.parseInt(raw_dimension);
        } catch (NumberFormatException e) {
            did = -1;
        }

        try {
            lid = Integer.parseInt(raw_level);
        } catch (NumberFormatException e) {
            lid = -1;
        }

        try {
            status = Integer.parseInt(raw_status);
        } catch (NumberFormatException e) {
            status = -1;
        }

//        if (raw_order == null) {
//            order = "";
//        } else {
//            order = raw_order;
//        }
//
//        if (raw_action == null) {
//            action = "";
//        } else {
//            action = raw_action;
//        }
        CourseDAO courseDB = new CourseDAO();
        List<Course> courses = courseDB.getCourseName();
        request.setAttribute("courses", courses);

        DimensionDAO dimDB = new DimensionDAO();
        ArrayList<Dimension> dimensions = dimDB.getDimension();
        request.setAttribute("dimensions", dimensions);

        ArrayList<QLevel> levels = qsDB.getQLevel();
        request.setAttribute("levels", levels);

        // phan trang
        int pagesize = 6;
        String page = request.getParameter("page");
        if (page == null || page.trim().length() == 0) {
            page = "1";
        }
        int pageindex = Integer.parseInt(page);
        ArrayList<Question> list = qsDB.getQSBankFilter(cid, did, lid, status, pageindex, pagesize);
        request.setAttribute("qslist", list);

        int count = qsDB.count(cid, did, lid, status);
        int totalpage = (count % pagesize == 0) ? (count / pagesize) : (count / pagesize + 1);
        request.setAttribute("total", totalpage);

        request.setAttribute("pageindex", pageindex);

        String url = "qsbank?";
        String url_param = request.getQueryString();
        if (url_param != null && url.length() > 0) {
            if (url_param.contains("page=" + pageindex)) {
                url_param = url_param.replaceAll("page=" + pageindex, "");
            }
            // if not contain page=x , add & 
            if (!url_param.equals("") && !url_param.endsWith("&")) {
                url_param += "&";
            }
            url += (url_param);
        }

        request.setAttribute("url", url);
        request.setAttribute("course", cid);
        request.setAttribute("dimension", did);
        request.setAttribute("level", lid);
        request.setAttribute("status", status);
        request.getRequestDispatcher("questionlist.jsp").forward(request, response);
//        request.getRequestDispatcher("test.jsp").forward(request, response);
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
