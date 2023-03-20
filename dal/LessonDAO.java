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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Lesson;
import model.Lesson_Type;
import model.Topic;

/**
 *
 * @author Admin
 */
public class LessonDAO extends BaseDAO<Lesson> {

    public ArrayList<Lesson> getAllLesson() {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM Lesson";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson r = new Lesson();
                r.setId(rs.getInt(1));
                r.setTopic(new TopicDAO().getTopicById(rs.getInt(2)));
                r.setName(rs.getString(3));
                r.setType(new Lesson_TypeDAO().getTypeById(rs.getInt(4)));
                r.setOrder(rs.getInt(5));
                r.setVideo_link(rs.getString(6));
                r.setContent(rs.getString(7));
                r.setStatus(rs.getInt(8));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    public List<Lesson> getLessonByTopicId(int topic_id) {
        List<Lesson> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM Lesson where topic_id =" + topic_id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson r = new Lesson();
                r.setId(rs.getInt(1));
                r.setTopic(new TopicDAO().getTopicById(rs.getInt(2)));
                r.setName(rs.getString(3));
                r.setType(new Lesson_TypeDAO().getTypeById(rs.getInt(4)));
                r.setOrder(rs.getInt(5));
                r.setVideo_link(rs.getString(6));
                r.setContent(rs.getString(7));
                r.setStatus(rs.getInt(8));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    public int getLessonByCourseId(int course_id) {
        int count = 0;
        try {
            String sql = "Select COUNT(Lesson.id) from\n"
                    + "Course inner join Topic\n"
                    + "on Course.id=Topic.course_id\n"
                    + "inner join Lesson\n"
                    + "on Topic.id=Lesson.topic_id\n"
                    + "where course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, course_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return count;
    }

    public ArrayList<Lesson> getAllLessonByCourseId(int course_id) {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "Select Lesson.id, Lesson.name, Lesson.status, Lesson.type_id, Topic.id from\n"
                    + "Course inner join Topic\n"
                    + "on Course.id=Topic.course_id\n"
                    + "inner join Lesson\n"
                    + "on Topic.id=Lesson.topic_id\n"
                    + "where course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, course_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson s = new Lesson();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setStatus(rs.getInt(3));
                s.setType(new Lesson_TypeDAO().getTypeById(rs.getInt(4)));
                s.setTopic(new TopicDAO().getTopicById2(rs.getInt(5)));
                list.add(s);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    public Lesson getLessonById(int id) {

        try {
            String sql = "SELECT *FROM [Lesson] where id = " + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson r = new Lesson();
                r.setId(rs.getInt(1));
                Topic t = new Topic();
                t.setId(rs.getInt(2));
                r.setTopic(t);
                r.setName(rs.getString(3));
                r.setType(new Lesson_TypeDAO().getTypeById(rs.getInt(4)));
                r.setOrder(rs.getInt(5));
                r.setVideo_link(rs.getString(6));
                r.setContent(rs.getString(7));
                r.setStatus(rs.getInt(8));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public void UpdateStatusLesson(int status, int id) {

        try {
            String sql = "UPDATE [Lesson] SET [status] = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void UpdateLesson(String lessonname, String topic, String youtubelink, String status, String describe, String type, String lid) {
        try {
            String sql = "UPDATE [Lesson]\n"
                    + "   SET [topic_id] = ?\n"
                    + "      ,[name] = ?\n"
                    + "      ,[type_id] = ?\n"
                    + "      ,[video_link] = ?\n"
                    + "      ,[content] = ?\n"
                    + "      ,[status] = ?\n"
                    + " WHERE [id] =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, topic);
            statement.setString(2, lessonname);
            statement.setString(3, type);
            statement.setString(4, youtubelink);
            statement.setString(5, describe);
            statement.setString(6, status);
            statement.setString(7, lid);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AddLesson(String lessonname, String topic, String youtubelink, String status, String brief, String type) {
        try {
            String sql = "INSERT INTO [dbo].[Lesson]\n"
                    + "           ([topic_id]\n"
                    + "           ,[name]\n"
                    + "           ,[type_id]\n"
                    + "           ,[video_link]\n"
                    + "           ,[content]\n"
                    + "           ,[status])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,0)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, topic);
            statement.setString(2, lessonname);
            statement.setString(3, type);
            statement.setString(4, youtubelink);
            statement.setString(5, brief);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
