/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AnswerOption;
import model.Course;
import model.Question;
import model.Quiz;
import model.UserQuiz;

/**
 *
 * @author Laptop88
 */
public class QuizDAO extends BaseDAO<Quiz> {

    public Quiz getQuizById(int id) {
        try {
            String sql = "SELECT *FROM [Quiz] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Quiz r = new Quiz();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                Course c = new Course();
                c.setId(rs.getInt(3));
                r.setCourse(c);
                r.setQLevel(new LevelDAO().getLevelById(rs.getInt(4)));
                r.setNumber_question(rs.getInt(5));
                r.setDuration(rs.getInt(6));
                r.setPass_rate(rs.getDouble(7));
                r.setType(rs.getString(8));
                r.setDescription(rs.getString(9));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public Quiz getQuizByCourseId(int id) {
        try {
            String sql = "SELECT *FROM [Quiz] WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Quiz r = new Quiz();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                Course c = new Course();
                c.setId(rs.getInt(3));
                r.setCourse(c);
                r.setQLevel(new LevelDAO().getLevelById(rs.getInt(4)));
                r.setNumber_question(rs.getInt(5));
                r.setDuration(rs.getInt(6));
                r.setPass_rate(rs.getDouble(7));
                r.setType(rs.getString(8));
                r.setDescription(rs.getString(9));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public Quiz getQuizByQuizIDForQ(int quizId) {
        try {
            String sql = "select id, name, [description], duration from Quiz where id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quizId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(quizId);
                quiz.setDescription(rs.getString("description"));
                quiz.setName(rs.getString("name"));
                quiz.setDuration(rs.getInt("duration"));
                return quiz;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    public static void main(String[] args) {
//        QuizDAO qdb = new QuizDAO();
//        System.out.println(qdb.getQuizByQuizIDForQ(1).getDescription());
//    }
    public void insertUserQuiz(UserQuiz u) {
        ArrayList<AnswerOption> ops = u.getOps();
        QuestionDAO qDB = new QuestionDAO();
        ArrayList<Question> questions = qDB.getQSByQuizID(u.getQuiz().getId());
        float mark = 0;
        for (Question q : questions) {
            boolean isCorrect = false;
            ArrayList<Integer> listCorrectAnswers = qDB.getIdOptionCorrect(q.getId());
            ArrayList<Integer> answers = answerQuestion(q.getId(), ops);

            if (answers != null && answers.size() == listCorrectAnswers.size()) {
                for (int answer : answers) {
                    if (!listCorrectAnswers.contains(answer)) {
                        isCorrect = false;
                        break;
                    } else {
                        isCorrect = true;
                    }
                }
            }
            if (isCorrect) {
                mark = mark + 1;
            }
        }

        mark = mark * 10 / questions.size();

        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [User_Quiz]\n"
                    + "           ([quiz_id]\n"
                    + "           ,[user_id]\n"
                    + "           ,[score]\n"
                    + "           ,[endtime])\n"
                    + "     VALUES (?, ?,?, GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, u.getQuiz().getId());
            stm.setInt(2, u.getUser().getId());
            stm.setFloat(3, mark);
            stm.executeUpdate();

            String sql_get_uid = " SELECT @@IDENTITY as uid ";
            PreparedStatement stm_get_uid = connection.prepareStatement(sql_get_uid);
            ResultSet rs = stm_get_uid.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("uid"));
            }
            if (u.getOps().size() > 0 || u.getOps() != null) {
                for (AnswerOption op : u.getOps()) {
                    String sql_add_choose = "INSERT INTO [UserQSAnswer]\n"
                            + "           ([questionID]\n"
                            + "           ,[optionID]\n"
                            + "           ,[userquizID])\n"
                            + "     VALUES (?,?,?)";
                    PreparedStatement stm_add_choose = connection.prepareStatement(sql_add_choose);
                    stm_add_choose.setInt(1, op.getQuestion().getId());
                    stm_add_choose.setInt(2, op.getId());
                    stm_add_choose.setInt(3, u.getId());
                    stm_add_choose.executeLargeUpdate();
                }
            }
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<Integer> answerQuestion(int qid, ArrayList<AnswerOption> ops) {
        ArrayList<Integer> answer = new ArrayList<>();

        for (AnswerOption op : ops) {
            if (op.getQuestion().getId() == qid) {
                answer.add(op.getId());
            }
        }
        return answer;
    }

}
