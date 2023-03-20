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
import model.Course;
import model.Topic;

/**
 *
 * @author Laptop88
 */
public class TopicDAO extends BaseDAO<Topic>{
    public Topic getTopicById(int id) {
        try {
            String sql = "SELECT *FROM [Topic] where id = "+id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Topic r = new Topic();
                r.setId(rs.getInt(1));
                Course c = new Course();
                c.setId(rs.getInt(2));
                r.setCourse(c);
                r.setTopic_name(rs.getString(3));
              return r;
            }          
        } catch (SQLException e) {
        }
         return null;
      
     }
    public Topic getTopicById2(int id) {
        try {
            String sql = "SELECT *FROM [Topic] where id = "+id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Topic r = new Topic();
                r.setId(rs.getInt(1));
                r.setCourse(new CourseDAO().getNameCourseById(rs.getInt(2)));
                r.setTopic_name(rs.getString(3));
              return r;
            }          
        } catch (SQLException e) {
        }
         return null;
      
     }
    
    public ArrayList<Topic> getTopicByCourse_Id(int course_id) {
        ArrayList<Topic> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [Topic] where course_id = " + course_id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Topic r = new Topic();
                r.setId(rs.getInt(1));
                Course c = new Course();
                c.setId(rs.getInt(2));
                r.setCourse(c);
                r.setTopic_name(rs.getString(3));
              list.add(r);
            }          
        } catch (SQLException e) {
        }
         return list;
      
     }
}


