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
import model.Role;

/**
 *
 * @author Laptop88
 */
public class RoleDAO extends BaseDAO<Role>{
    
    public Role getRoleById(int id) {
        try {
            String sql = "SELECT *FROM [Role] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public List<Role> getAllRole() {
        List<Role> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [Role]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }
}
