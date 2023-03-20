/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.CategorycourseDAO;
import dal.CoursePackageDAO;
import dal.SubCategoryCourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CategoryCourse;
import model.CoursePackage;
import model.SubCategoryCourse;

/**
 *
 * @author win
 */
public class CourseController extends HttpServlet {

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
            out.println("<title>Servlet CourseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseController at " + request.getContextPath() + "</h1>");
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
        String raw_begin = request.getParameter("begin");
        if (raw_begin == null || raw_begin.trim().length() == 0) {
            raw_begin = "2015-01-01";
        }
        Date beginDate = Date.valueOf(raw_begin);
        String raw_end = request.getParameter("end");
        if (raw_end == null || raw_end.trim().length() == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            raw_end = formatter.format(date);
        }
        Date endDate = Date.valueOf(raw_end);

        String raw_feature = request.getParameter("feature");
        if (raw_feature == null || raw_feature.length() == 0) {
            raw_feature = "0";
        }
        int featured = Integer.parseInt(raw_feature);

        String[] category = request.getParameterValues("category");
        int[] ccid = null;
        if (category != null) {
            ccid = new int[category.length];
            for (int i = 0; i < category.length; i++) {
                ccid[i] = Integer.parseInt(category[i]);
               // response.getWriter().print(ccid[i]);
            }
            //return;
        }
        

        String[] course = request.getParameterValues("course");
        int[] cid = null;
        if (course != null) {
            cid = new int[course.length];
            for (int i = 0; i < course.length; i++) {
                cid[i] = Integer.parseInt(course[i]);
            }
        }
        int pageSize = 6;
        CoursePackageDAO cpDao = new CoursePackageDAO();
        String page = request.getParameter("page");
        if (page == null || page.trim().length() == 0) {
            page = "1"; //default page 1 in first access
        }
        int pageIndex = Integer.parseInt(page);
        List<CoursePackage> list = new ArrayList<>();
        list = cpDao.searchCourses(ccid, cid, name, beginDate, endDate, featured, pageIndex, pageSize);
        int count = cpDao.countCourses(ccid, cid, name, beginDate, endDate, featured);
        int totalPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize) + 1;
        String url = "Course?";
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
        SubCategoryCourseDAO sccdao = new SubCategoryCourseDAO();
        List<SubCategoryCourse> listSubCategoryCourse = new ArrayList<>();
        listSubCategoryCourse = sccdao.getAllSubCategoryCourse();
        boolean []courseCheck = new boolean[listSubCategoryCourse.size()];
        for (int i = 0; i < courseCheck.length; i++) {
            courseCheck[i] = ischeck(listSubCategoryCourse.get(i).getId(), cid); 
        }
        CategorycourseDAO ccdao = new CategorycourseDAO();
        List<CategoryCourse> listCategoryCourse = new ArrayList<>();
        listCategoryCourse = ccdao.getAllCategoryCourse();
        boolean []categoryCourseCheck = new boolean[listCategoryCourse.size()];
        for (int i = 0; i < categoryCourseCheck.length; i++) {
            categoryCourseCheck[i] = ischeck(listCategoryCourse.get(i).getId(), ccid); //finding tag having check
        }
        request.setAttribute("categoryCourseCheck",categoryCourseCheck);
      request.setAttribute("courseCheck",courseCheck);
        
        request.setAttribute("listCategoryCourse", listCategoryCourse);
        request.setAttribute("listSubCategoryCourse", listSubCategoryCourse);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("txt", name);
        request.setAttribute("endDate", endDate);
        request.setAttribute("featured", featured);
        request.setAttribute("url", url);
        request.setAttribute("totalpage", totalPage);
        request.setAttribute("pageindex", pageIndex);
        request.setAttribute("listtxt", list);
        request.getRequestDispatcher("course.jsp").forward(request, response);

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
//        String raw_begin = request.getParameter("begin");
//        if(raw_begin==null || raw_begin.trim().length()==0){
//            raw_begin = "2015-01-01";
//        }
//        Date beginDate = Date.valueOf(raw_begin) ;
//        String raw_end = request.getParameter("end");
//        if(raw_end==null || raw_end.trim().length()==0){
//             SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
//             Date date = new Date(System.currentTimeMillis());
//             
//            raw_end = formatter.format(date);
//        }
//        Date endDate = Date.valueOf(raw_end) ;
//        
//        String raw_feature = request.getParameter("feature");
//        if(raw_feature==null || raw_feature.length()==0 ){
//           raw_feature="0";
//        }
//          int featured = Integer.parseInt(raw_feature);
//         
//        
//        String[] category = request.getParameterValues("category");
//        int[] ccid = null;
//        if(category!=null){
//            ccid = new int[category.length];
//            for (int i = 0; i < category.length; i++) {
//                   ccid[i] = Integer.parseInt(category[i]);
//            }
//        }
//        
//        String[] course = request.getParameterValues("course");
//        int[] cid = null;
//        if(course!=null){
//            cid = new int[course.length];
//            for (int i = 0; i < course.length; i++) {
//                   cid[i] = Integer.parseInt(course[i]);
//            }
//        }
//        int pageSize = 6;
//         CoursePackageDAO cpDao = new CoursePackageDAO();
//         String page = request.getParameter("page");
//         if(page==null || page.trim().length() == 0){
//            page = "1"; //default page 1 in first access
//        }
//        int pageIndex = Integer.parseInt(page);
//        List<CoursePackage> list = new ArrayList<>();
//        list = cpDao.searchCourses(ccid, cid, beginDate, endDate, featured, pageIndex, pageSize);
//        int count = cpDao.countCourses(ccid, cid, beginDate, endDate, featured);
//        int totalPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize) + 1;
//        String url = "Course?";
//         String url_param=request.getQueryString(); //get parametter 
//       if(url_param!=null && url.length()>0){
//           if(url_param.contains("page=" + pageIndex)){
//               url_param = url_param.replaceAll("page=" +pageIndex, "");      
//            }
//            // if not contain page=x , add & 
//            if(!url_param.equals("") && !url_param.endsWith("&")){
//                url_param += "&"; 
//            }
//            url += (url_param);
//       }
//       request.setAttribute("beginDate", beginDate);
//       request.setAttribute("endDate", endDate);
//       request.setAttribute("featured", featured);
//       request.setAttribute("url", url);
//       request.setAttribute("totalpage", totalPage);
//       request.setAttribute("pageindex", pageIndex);
//       request.setAttribute("listtxt", list);
//       request.getRequestDispatcher("course.jsp").forward(request, response);
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
