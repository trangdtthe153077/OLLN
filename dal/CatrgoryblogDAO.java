/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Category_Blog;

/**
 *
 * @author Laptop88
 */
public class CatrgoryblogDAO extends BaseDAO<Category_Blog>{
    public Category_Blog getCategoryBlogById(int id) {
        try {
            String sql = "SELECT *FROM [Category_Blog] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                return c;
            }
        } catch (SQLException e) {
        }
        return null;

    }
}
