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
import model.CategoryCourse;

/**
 *
 * @author Laptop88
 */
public class CategorycourseDAO extends BaseDAO<CategoryCourse>{
    public CategoryCourse getCategoryCourseById(int id) {
        try {
            String sql = "SELECT *FROM [Category_Course] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CategoryCourse r = new CategoryCourse();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public List<CategoryCourse> getAllCategoryCourse() {
        List<CategoryCourse> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM Category_Course";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CategoryCourse r = new CategoryCourse();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                r.setImage(rs.getString(3));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }
}
