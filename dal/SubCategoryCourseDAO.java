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
import model.SubCategoryCourse;

/**
 *
 * @author Laptop88
 */
public class SubCategoryCourseDAO  extends BaseDAO<SubCategoryCourse>{
    public SubCategoryCourse getSubCategoryCourseById(int id) {
        try {
            String sql = "SELECT *FROM [SubCategory_Course] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SubCategoryCourse r = new SubCategoryCourse();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                r.setImage(rs.getString(3));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public ArrayList<SubCategoryCourse> getSubCategoryCourse() {
        ArrayList<SubCategoryCourse> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [SubCategory_Course]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SubCategoryCourse r = new SubCategoryCourse();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                r.setImage(rs.getString(3));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }
    public List<SubCategoryCourse> getAllSubCategoryCourse() {
        List<SubCategoryCourse> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [SubCategory_Course]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SubCategoryCourse r = new SubCategoryCourse();
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
