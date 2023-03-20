/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.QuizDAO;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.UserQuiz;

/**
 * Web application lifecycle listener.
 *
 * @author DELL
 */
public class QuizListener implements HttpSessionListener, HttpSessionAttributeListener {

    public static Timer timer;
    int count = 0;

    class RemindTask extends TimerTask {

        private HttpSessionBindingEvent se;

        public RemindTask(HttpSessionBindingEvent se) {
            this.se = se;
        }

        @Override
        public void run() {
            se.getSession().removeAttribute("doingQuiz");
            timer.cancel(); //Terminate the timer thread
        }
    }

    public void Time(int seconds, HttpSessionBindingEvent se) {
        timer = new Timer();
        timer.schedule(new RemindTask(se), seconds * 1000 * 60); // schedule the task
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (se.getName().equalsIgnoreCase("doingQuiz")) {
            UserQuiz uq = (UserQuiz) se.getValue();
            if (count == 0) {
                Time(uq.getQuiz().getDuration(), se);
//                Time(, se);

                count++;
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        if (se.getName().equalsIgnoreCase("doingQuiz")) {
            try {

                QuizDAO qd = new QuizDAO();
                UserQuiz uq = (UserQuiz) se.getValue();
                count = 0;
                qd.insertUserQuiz(uq);

                timer.cancel();

            } catch (Exception e) {
                System.out.println("nu u    ");
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }
}



