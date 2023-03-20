/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.Category_SubCategory_CourseDAO;
import dal.CourseDAO;
import dal.SubCategoryCourseDAO;
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
import model.Category_SubCategory_Course;
import model.Course;
import model.CourseStatus;
import model.SubCategoryCourse;
import model.User;

/**
 *
 * @author ADMIN
 */
public class subjectlistServlet extends HttpServlet {

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
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        User acc = (User) session.getAttribute("account");

//        phan trang
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        int pageindex = Integer.parseInt(page);
        int totall;
        
        if (acc == null) {
            response.sendRedirect("home");
            return;
        }
        
        CourseDAO dao = new CourseDAO();
        ArrayList<Course> allCourse = new ArrayList<>();
        List<CategoryCourse> category = dao.getAllCategoryCourse();
        ArrayList<CourseStatus> status = dao.getCourseStatus();
        SubCategoryCourseDAO subdao = new SubCategoryCourseDAO();
        ArrayList<SubCategoryCourse> subcategory = subdao.getSubCategoryCourse();
        
        String link = "";
        String name = null;
        String searchcategory = null;
        String searchsubcategory = null;
        String searchstatus = null;
        int owner = -2;
        if (acc.getRole().getId()== 5) {
            owner = -1;
        }
        if (acc.getRole().getId() == 4) {
            owner = acc.getId();
        }
        
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("search")) {
            name = request.getParameter("name");
            searchcategory = request.getParameter("category");
            searchsubcategory = request.getParameter("subcategory");
            searchstatus = request.getParameter("status");
            allCourse = dao.searchCourses(pageindex, 10, name, searchcategory, searchsubcategory, searchstatus, owner);
            totall = dao.searchTotal(pageindex, 10, name, searchcategory, searchsubcategory, searchstatus, owner);
            link += "?action=search&name=" + name + "&category=" + searchcategory + "&subcategory=" + searchsubcategory + "&status=" + searchstatus + "";
            
        } else {
            allCourse = dao.getCourses(pageindex, 10, owner);
            totall = dao.getTotal(owner);
        }
        
        int total = 1;
        if (totall % 10 == 0 && totall!=0) {
            total = totall / 10;
        } else {
            total = totall / 10 + 1;
        }
        
        request.setAttribute("pageindex", pageindex);
        request.setAttribute("total", total);
 
//        out.print(totall);
//        out.print(total);
//        out.print(dao.getTotal(acc.getId()));
//        for (Course a : allCourse) {
//            out.print(a);
//        }
//        for (CategoryCourse a : category) {
//            out.print(a);
//        }
//        for (CourseStatus a : status) {
//            out.print(a);
//        }

        request.setAttribute("link", link);
        request.setAttribute("course", allCourse);
        request.setAttribute("status", status);
        
        request.setAttribute("searchname", name);
        request.setAttribute("searchcategory", searchcategory);
        request.setAttribute("searchsubcategory", searchsubcategory);
        
        request.setAttribute("searchstatus", searchstatus);
        request.setAttribute("category", category);
        request.setAttribute("subcategory", subcategory);

        request.getRequestDispatcher("subjectlist.jsp").forward(request, response);
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
