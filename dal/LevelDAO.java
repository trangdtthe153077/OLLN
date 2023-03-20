/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.QLevel;

/**
 *
 * @author Laptop88
 */
public class LevelDAO extends BaseDAO<QLevel>{
    public QLevel getLevelById(int id) {
        try {
            String sql = "SELECT *FROM [Level] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                QLevel r = new QLevel();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public List<QLevel> getAllLevel() {
        List<QLevel> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [Level]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                QLevel r = new QLevel();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;
    }
}
