/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Category_SubCategory_Course;

/**
 *
 * @author Laptop88
 */
public class Category_SubCategory_CourseDAO extends BaseDAO<Category_SubCategory_Course>{
    public Category_SubCategory_Course getCategory_SubCategoryCourseById(int id) {
        try {
            String sql = "SELECT *FROM [Category_SubCategory_Course] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Category_SubCategory_Course r = new Category_SubCategory_Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new CategorycourseDAO().getCategoryCourseById(rs.getInt(2)));
                r.setSubcategorycourse(new SubCategoryCourseDAO().getSubCategoryCourseById(rs.getInt(3)));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    
//    public static void main(String[] args) {
//        Category_SubCategory_CourseDAO dao = new Category_SubCategory_CourseDAO();
//    
//        Category_SubCategory_Course s = dao.getCategory_SubCategoryCourseById(1);
//        System.out.println(s.getSubcategorycourse().getName());
//    }    
    }
