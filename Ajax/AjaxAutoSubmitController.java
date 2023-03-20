/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AnswerOption;
import model.Question;
import model.User;
import model.UserQuiz;

/**
 *
 * @author DELL
 */
public class AjaxAutoSubmitController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AjaxAutoSubmitController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AjaxAutoSubmitController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("account");
        int quizId = Integer.parseInt(request.getParameter("quiz"));
        QuestionDAO qDB = new QuestionDAO();
        ArrayList<Question> questions = qDB.getQSByQuizID(quizId);
        ArrayList<AnswerOption> ops = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            int questionID = Integer.parseInt(request.getParameter("question" + i));
            String[] answers = request.getParameterValues("answer" + i);
            if (answers != null && answers.length > 0) {
                for (int j = 0; j < answers.length; j++) {
                    if (answers[j].length() > 0 && answers[j] != null) {
                        int as = Integer.parseInt(answers[j]);
                        AnswerOption o = new AnswerOption();
                        o.setId(as);

                        Question q = new Question();
                        q.setId(questionID);

                        o.setQuestion(q);
                        ops.add(o);
                    }
                }
            }
        }

        UserQuiz userQuiz = (UserQuiz) request.getSession().getAttribute("doingQuiz");
        if (ops != null || ops.size() > 0) {
            userQuiz.setOps(ops);
        }
        request.getSession().setAttribute("doingQuiz", userQuiz);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
