/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.CategorycourseDAO;
import dal.CourseDAO;
import dal.CoursePackageDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CategoryCourse;

/**
 *
 * @author win
 */
public class dashboardController extends HttpServlet {
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
            out.println("<title>Servlet dashboardController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet dashboardController at " + request.getContextPath() + "</h1>");
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
        CourseDAO cdao = new CourseDAO();
        CoursePackageDAO cpdao = new CoursePackageDAO();
        int totalCourse = cdao.getTotalCourse();
       
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if(from == null && to == null){
            from = "12/12/2021";
            to = "6/29/2022";
        }
        int totalCourseByDate = cdao.getTotalCourseByDate(from, to);
        
        UserDAO udao = new UserDAO();
        int totalCus = udao.getTotalCustomer();
        int totalCusByDate = udao.getTotalCustomerByDate(from, to);
        int totalCusBoughtByDate = udao.getTotalCustomerBoughtByDate(from, to);
        int totalRevenuesCurrentW = cpdao.getRevenuesCurentW();
        int totalRevenuesPreviousW = cpdao.getRevenuesPreviousW(totalRevenuesCurrentW);
        double totalRevenues = cpdao.getToatlRevenues();
         CategorycourseDAO ccdao = new CategorycourseDAO();
        List<CategoryCourse> listCategoryCourse = new ArrayList<>();
        listCategoryCourse = ccdao.getAllCategoryCourse();
        //chart revenues
        int cMon = cpdao.getRevenuesMonC(7);
        int cTue = cpdao.getRevenuesMonC(6);
        int cWed = cpdao.getRevenuesMonC(5);
        int cThu = cpdao.getRevenuesMonC(4);
        int cFri = cpdao.getRevenuesMonC(3);
        int cSat = cpdao.getRevenuesMonC(2);
        int cSun = cpdao.getRevenuesMonC(1);
        int pSat = cpdao.getRevenuesMonC(13);
        int pFri = cpdao.getRevenuesMonC(12);
        int pThu = cpdao.getRevenuesMonC(11);
        int pWed = cpdao.getRevenuesMonC(10);
        int pTue = cpdao.getRevenuesMonC(9);
        int pMon = cpdao.getRevenuesMonC(8);
        int pSun = cpdao.getRevenuesMonC(14);
        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute("rMon", cMon);
        request.setAttribute("cTue", cTue);
        request.setAttribute("cWed", cWed);
        request.setAttribute("cThu", cThu);
        request.setAttribute("cFri", cFri);
        request.setAttribute("cSat", cSat);
        request.setAttribute("cSun", cSun);
        request.setAttribute("pSun", pSun);
        request.setAttribute("pMon", pMon);
        request.setAttribute("pTue", pTue);
        request.setAttribute("pWed", pWed);
        request.setAttribute("pThu", pThu);
        request.setAttribute("pFri", pFri);
        request.setAttribute("pSat", pSat);
        //chart revenues by category
        double revenue10 = cpdao.getRevenuesByCategory("10", from, to);
        double revenue11 = cpdao.getRevenuesByCategory("11", from, to);
        double revenue12 = cpdao.getRevenuesByCategory("12", from, to);
        double revenue10p =(double) Math.round((revenue10 / totalRevenues) * 100);
        double revenue11p = (double) Math.round((revenue11 / totalRevenues) * 100);
        double revenue12p = (double) Math.round((revenue12 / totalRevenues) * 100);
        request.setAttribute("revenue10p", revenue10p);
        request.setAttribute("revenue11p", revenue11p);
        request.setAttribute("revenue12p", revenue12p);
        request.setAttribute("revenue10", revenue10);
        request.setAttribute("revenue11", revenue11);
        request.setAttribute("revenue12", revenue12);
        //chart trend
        int trendByDateSun = cdao.getTotalResByDate(from, to, 1);
        int trendByDateSat = cdao.getTotalResByDate(from, to, 2);
        int trendByDateFri = cdao.getTotalResByDate(from, to, 3);
        int trendByDateThu = cdao.getTotalResByDate(from, to, 4);
        int trendByDateWed = cdao.getTotalResByDate(from, to, 5);
        int trendByDateTue = cdao.getTotalResByDate(from, to, 6);
        int trendByDateMon = cdao.getTotalResByDate(from, to, 7);
        request.setAttribute("trendByDateSun", trendByDateSun);
        request.setAttribute("trendByDateSat", trendByDateSat);
        request.setAttribute("trendByDateFri", trendByDateFri);
        request.setAttribute("trendByDateThu", trendByDateThu);
        request.setAttribute("trendByDateWed", trendByDateWed);
        request.setAttribute("trendByDateTue", trendByDateTue);
        request.setAttribute("trendByDateMon", trendByDateMon);
        int trendSucessByDateSun = cdao.getSuccessResByDate(from, to, 1);
        int trendSucessByDateSat = cdao.getSuccessResByDate(from, to, 2);
        int trendSucessByDateFri = cdao.getSuccessResByDate(from, to, 3);
        int trendSucessByDateThu = cdao.getSuccessResByDate(from, to, 4);
        int trendSucessByDateWed = cdao.getSuccessResByDate(from, to, 5);
        int trendSucessByDateTue = cdao.getSuccessResByDate(from, to, 6);
        int trendSucessByDateMon = cdao.getSuccessResByDate(from, to, 7);
        request.setAttribute("trendSucessByDateSun", trendSucessByDateSun);
        request.setAttribute("trendSucessByDateSat", trendSucessByDateSat);
        request.setAttribute("trendSucessByDateFri", trendSucessByDateFri);
        request.setAttribute("trendSucessByDateThu", trendSucessByDateThu);
        request.setAttribute("trendSucessByDateWed", trendSucessByDateWed);
        request.setAttribute("trendSucessByDateTue", trendSucessByDateTue);
        request.setAttribute("trendSucessByDateMon", trendSucessByDateMon);
        //chart pie
        int countResByDate = cdao.getCountResByDate(from, to);
        int countResByDateSuccess = cdao.getCountResByDateAndStatus(from, to, "success");
        int countResByDateCanncelled = cdao.getCountResByDateAndStatus(from, to, "cancelled");
        int countResByDateSubmitted = cdao.getCountResByDateAndStatus(from, to, "submitted");
        request.setAttribute("countResByDateSubmitted", countResByDateSubmitted);
        request.setAttribute("countResByDate", countResByDate);
        request.setAttribute("countResByDateCanncelled", countResByDateCanncelled);
        request.setAttribute("countResByDateSuccess", countResByDateSuccess);
        request.setAttribute("listCategoryCourse", listCategoryCourse);
        request.setAttribute("totalRevenues", totalRevenues);
        request.setAttribute("totalRevenuesCurrentW", totalRevenuesCurrentW);
        request.setAttribute("totalRevenuesPreviousW", totalRevenuesPreviousW);
        request.setAttribute("totalCusBoughtByDate", totalCusBoughtByDate);
        request.setAttribute("totalCus", totalCus);
        request.setAttribute("totalCourse", totalCourse);
        request.setAttribute("totalCusByDate", totalCusByDate);
        request.setAttribute("totalCourseByDate", totalCourseByDate);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        CourseDAO cdao = new CourseDAO();
        int totalCourseByDate = cdao.getTotalCourseByDate(from, to);
        request.setAttribute("totalCourseByDate", totalCourseByDate);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);

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
