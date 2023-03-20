/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.LessonDAO;
import dal.QuizDAO;
import dal.TopicDAO;
import dal.User_LessonDAO;
import dal.User_QuizDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Lesson;
import model.Quiz;
import model.Topic;
import model.User;
import model.User_Lesson;
import model.User_Quiz;

/**
 *
 * @author Laptop88
 */
public class ChooseLessonView extends HttpServlet {

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
            
       
        HttpSession session =request.getSession();
        User acc = (User) session.getAttribute("account");
        int cid = Integer.parseInt(request.getParameter("cid"));
        
        TopicDAO td = new TopicDAO();
        LessonDAO ld= new LessonDAO();
        QuizDAO qd =new QuizDAO();
        User_LessonDAO uld = new User_LessonDAO();
        User_QuizDAO uqd = new User_QuizDAO();
        ArrayList<Topic> listtopic = td.getTopicByCourse_Id(cid);
        Quiz quiz = qd.getQuizByCourseId(cid);
        User_Quiz userquiz = uqd.getUserQuizByQuizIdAndUserId(quiz.getId(), acc.getId());
        ArrayList<User_Lesson> listlearn = uld.getLearnedLessonByUserId(acc.getId());
        int countlearned = 0;
        for (User_Lesson user_Lesson : listlearn) {
            if(user_Lesson.getStatus() == 1 && user_Lesson.getLesson().getId() != cid){
                countlearned++;
            }
        }
        
        int countlearn = ld.getLessonByCourseId(cid);
        
        String lid = request.getParameter("lid");
        
        Lesson lesson = null;
        
        if (lid.equals("")) {
            lesson = ld.getLessonById(listlearn.get(0).getLesson().getId());
        } else {
            int lessonid = Integer.parseInt(lid);
            for (User_Lesson listlestatu : listlearn) {
                if (listlestatu.getLesson().getId() < lessonid) {
                    lesson = ld.getLessonById(listlestatu.getLesson().getId());
                }
            }
            if (lesson == null) {
                lesson = ld.getLessonById(listlearn.get(0).getLesson().getId());
            }
        }
        request.setAttribute("listlearn", listlearn);
        request.setAttribute("userquiz", userquiz);
        request.setAttribute("quiz", quiz);
        request.setAttribute("lesson", lesson);
        request.setAttribute("countlearned", countlearned);
        request.setAttribute("countlearn", countlearn);
        request.setAttribute("listtopic", listtopic);
        request.getRequestDispatcher("lessonview.jsp").forward(request, response);

      
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


