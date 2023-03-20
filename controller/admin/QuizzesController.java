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
public class QuizzesController extends HttpServlet {

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
            out.println("<title>Servlet QuizzesCOntroller</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuizzesCOntroller at " + request.getContextPath() + "</h1>");
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
        String name = request.getParameter("txt");
        String raw_status = request.getParameter("status");
        if (raw_status == null || raw_status.length() == 0) {
            raw_status = "1";
        }
        int status = Integer.parseInt(raw_status);
        String[] course = request.getParameterValues("course");
        int[] cid = null;
        if (course != null) {
            cid = new int[course.length];
            for (int i = 0; i < course.length; i++) {
                cid[i] = Integer.parseInt(course[i]);
            }
        }
        
        int pageSize = 6;
        QuizzesDAO qdao = new QuizzesDAO();
        String page = request.getParameter("page");
        if (page == null || page.trim().length() == 0) {
            page = "1"; //default page 1 in first access
        }
        int pageIndex = Integer.parseInt(page);
        List<Quizzes> list = new ArrayList<>();
        list = qdao.searchQuizzes(cid, name, status, pageIndex, pageSize);
//        list = qdao.searchQuizzes(null, null, 1, 1, 6);
//        for (Quizzes quizzes : list) {
//            response.getWriter().print(quizzes.toString());
//        }
        int count = qdao.countQuizzes(cid, name, status);
        int totalPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize) + 1;
        String url = "quizzes?";
        String url_param = request.getQueryString(); //get parametter 
        if(url_param!=null && url.length()>0){
           if(url_param.contains("page=" + pageIndex)){
               url_param = url_param.replaceAll("page=" +pageIndex, "");      
            }
            // if not contain page=x , add & 
            if(!url_param.equals("") && !url_param.endsWith("&")){
                url_param += "&"; 
            }
            url += (url_param);
       }
        CourseDAO sccdao = new CourseDAO();
        List<Course> listSubCategoryCourse = new ArrayList<>();
        listSubCategoryCourse = sccdao.getAllCourse();
        boolean []courseCheck = new boolean[listSubCategoryCourse.size()];
        for (int i = 0; i < courseCheck.length; i++) {
            courseCheck[i] = ischeck(listSubCategoryCourse.get(i).getId(), cid); 
        }
        
        request.setAttribute("listSubCategoryCourse", listSubCategoryCourse);
        request.setAttribute("status", status);
        request.setAttribute("txt", name);
        request.setAttribute("courseCheck",courseCheck);
        request.setAttribute("url", url);
        request.setAttribute("totalpage", totalPage);
        request.setAttribute("pageindex", pageIndex);
        request.setAttribute("listtxt", list);
        request.getRequestDispatcher("quizzeslist.jsp").forward(request, response);
    }
    Boolean ischeck(int id, int []x){
        //is id in cids?  
        if (x == null) return false;
        for (int i = 0; i < x.length; i++) {
            if (id==x[i]){
                return true;
            }
        }
        return false;
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
        doGet(request, response);
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
