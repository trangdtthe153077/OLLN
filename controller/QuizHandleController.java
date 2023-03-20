/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.QuestionDAO;
import dal.QuizDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AnswerOption;
import model.Question;
import model.Quiz;
import model.User;
import model.UserQuiz;

/**
 *
 * @author DELL
 */
public class QuizHandleController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QuizHandleController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuizHandleController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        QuizDAO quizDB = new QuizDAO();
        Quiz quiz = quizDB.getQuizByQuizIDForQ(quizId);
        QuestionDAO qDB = new QuestionDAO();
        ArrayList<Question> questions = qDB.getQSByQuizID(quizId);
        User user = (User) request.getSession().getAttribute("account");

        if (request.getSession().getAttribute("doingQuiz") == null) {
            User u = new User();
            u.setId(user.getId());
            UserQuiz uq = new UserQuiz();
            uq.setUser(u);
            uq.setQuiz(quiz);
            uq.setTime(ZonedDateTime.now().toInstant().toEpochMilli());
            request.getSession().setAttribute("doingQuiz", uq);
        } else {
            UserQuiz uq = (UserQuiz) request.getSession().getAttribute("doingQuiz");
            ArrayList<AnswerOption> ops = uq.getOps();
            request.setAttribute("choose", ops);
        }

        request.setAttribute("quiz", quiz);
        request.setAttribute("questions", questions);
        request.getRequestDispatcher("quizhandle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("doingQuiz");
        request.getSession().setAttribute("quizHandle_mess", "quizHandle_mess");

        response.sendRedirect("home");
        // chuyen qua trang review quiz vua lam
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
