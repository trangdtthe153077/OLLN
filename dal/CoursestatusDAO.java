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
import model.CourseStatus;

/**
 *
 * @author Laptop88
 */
public class CoursestatusDAO extends BaseDAO<CourseStatus>{
    public CourseStatus getCourseStatusById(int id) {
        try {
            String sql = "SELECT *FROM [Course_Status] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CourseStatus r = new CourseStatus();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
     public ArrayList<CourseStatus> getAllCourseStatus() {
        ArrayList<CourseStatus> coursestatus = new ArrayList<CourseStatus>();
        try {
            String sql = "SELECT *FROM [Course_Status]";
            PreparedStatement statement = connection.prepareStatement(sql);
         
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CourseStatus r = new CourseStatus();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                coursestatus.add(r);
            }
            return coursestatus;
        } catch (SQLException e) {
        }
        return null;

    }

}
