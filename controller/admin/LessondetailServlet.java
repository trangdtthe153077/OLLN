/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.LessonDAO;
import dal.Lesson_TypeDAO;
import dal.TopicDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Lesson;
import model.Lesson_Type;
import model.Topic;

/**
 *
 * @author Laptop88
 */
public class LessondetailServlet extends HttpServlet {

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
        int lid = Integer.parseInt(request.getParameter("lid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        Lesson_TypeDAO ltd = new Lesson_TypeDAO();
        TopicDAO td = new TopicDAO();
        LessonDAO ld= new LessonDAO();
        ArrayList<Lesson_Type> listtype = ltd.getAllLessonType();
        ArrayList<Topic> listtopic = td.getTopicByCourse_Id(cid);
        Lesson lesson = ld.getLessonById(lid);
        request.setAttribute("listtype", listtype);
        request.setAttribute("listtopic", listtopic);
        request.setAttribute("lesson", lesson);
        request.setAttribute("cid", cid);
        request.getRequestDispatcher("lessondetail.jsp").forward(request, response);
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
        LessonDAO ld = new LessonDAO();
        
        String action = request.getParameter("action");
        String cid = request.getParameter("cid");
        String lid = request.getParameter("lid");
        if (action.equals("updatelesson")) {
            String lessonname = request.getParameter("lessonname");
            String topic = request.getParameter("topic");
            String youtubelink = request.getParameter("youtubelink");
            String status = request.getParameter("status");
            String describe = request.getParameter("describe");
            String type = request.getParameter("type");
            
            ld.UpdateLesson(lessonname, topic, youtubelink, status, describe, type, lid);
            response.sendRedirect("editlesson?lid="+lid+"&cid="+ cid);
        }
        if (action.equals("addnewlesson")) {
            String lessonname = request.getParameter("lessonname");
            String topic = request.getParameter("topicselect");
            String youtubelink = request.getParameter("youtubelink");
            String status = request.getParameter("statusselect");
            String brief = request.getParameter("brief");
            String type = request.getParameter("typeselect");
            
            ld.AddLesson(lessonname, topic, youtubelink, status, brief, type);
            response.sendRedirect("lesson?cid="+ cid);
        }
        
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
