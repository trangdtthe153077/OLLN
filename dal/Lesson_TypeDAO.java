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
import model.Lesson_Type;

/**
 *
 * @author Laptop88
 */
public class Lesson_TypeDAO extends BaseDAO<Lesson_Type>{
    public Lesson_Type getTypeById(int id) {
        try {
            String sql = "SELECT *FROM [Lesson_Type] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson_Type r = new Lesson_Type();
                r.setId(rs.getInt(1));
                r.setType_name(rs.getString(2));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public ArrayList<Lesson_Type> getAllLessonType() {
        ArrayList<Lesson_Type> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [Lesson_Type]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson_Type r = new Lesson_Type();
                r.setId(rs.getInt(1));
                r.setType_name(rs.getString(2));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }
}
