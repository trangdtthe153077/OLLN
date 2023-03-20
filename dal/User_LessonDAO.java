/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
import model.User_Lesson;

/**
 *
 * @author Laptop88
 */
public class User_LessonDAO extends BaseDAO<User_Lesson> {

    public ArrayList<User_Lesson> getLearnedLessonByUserId(int userid) {
        ArrayList<User_Lesson> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [User_Lesson] where user_id = " + userid;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User_Lesson r = new User_Lesson();
                User u = new User();
                u.setId(rs.getInt(1));
                r.setUser(u);
                r.setLesson(new LessonDAO().getLessonById(rs.getInt(2)));
                r.setStatus(rs.getInt(3));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }

    public void SetLearnedLesson(int userid, int lessonid) {
        try {
            String sql = "INSERT INTO [User_Lesson]\n"
                    + "           ([user_id]\n"
                    + "           ,[lesson_id])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userid);
            statement.setInt(2, lessonid);
            statement.executeUpdate();
        } catch (SQLException e) {
        }

    }
    public void UpdateStatusLearned(int userid, int lessonid) {
        try {
            String sql = "UPDATE [User_Lesson] SET [status] = 1 WHERE [user_id] = ? AND [lesson_id] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userid);
            statement.setInt(2, lessonid);
            statement.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public static void main(String[] args) {
        User_LessonDAO uld = new User_LessonDAO();
        ArrayList<User_Lesson> list = uld.getLearnedLessonByUserId(53);
        for (User_Lesson user_Lesson : list) {
            System.out.println(user_Lesson.getLesson().getId());
        }
    }
}


