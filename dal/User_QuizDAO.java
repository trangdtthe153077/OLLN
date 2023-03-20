/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User_Quiz;

/**
 *
 * @author Laptop88
 */
public class User_QuizDAO extends BaseDAO<User_Quiz>{
    public User_Quiz getUserQuizById(int id) {
        try {
            String sql = "SELECT *FROM [User_Quiz] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User_Quiz r = new User_Quiz();
                r.setId(rs.getInt(1));
                r.setQuiz(new QuizDAO().getQuizById(rs.getInt(2)));
                r.setUser(new UserDAO().getUserById(rs.getInt(3)));
                r.setScore(rs.getInt(4));
                r.setTime(rs.getInt(5));
                r.setStatus(rs.getInt(6));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public User_Quiz getUserQuizByQuizIdAndUserId(int quiz_id, int user_id) {
        try {
            String sql = "SELECT *FROM [User_Quiz] WHERE quiz_id = ? and user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quiz_id);
            statement.setInt(2, user_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User_Quiz r = new User_Quiz();
                r.setId(rs.getInt(1));
                r.setScore(rs.getInt(4));
                r.setTime(rs.getInt(5));
                r.setStatus(rs.getInt(6));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
}


