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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dimension;

/**
 *
 * @author ADMIN
 */
public class DimensionDAO extends BaseDAO<Dimension> {

    public ArrayList<Dimension> getCourseDimensionById(int id) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "SELECT * from [Course_Dimension] inner join Subject_Dimension\n"
                    + "on [Course_Dimension].dimension_id = Subject_Dimension.id\n"
                    + "where course_id=? "
                    + "order by id asc";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            System.out.println(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Dimension r = new Dimension();
                r.setId(rs.getInt("id"));
                r.setType(rs.getString("type"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                dimensions.add(r);
            }
            return dimensions;
        } catch (SQLException e) {
        }
        return null;

    }

    public ArrayList<Dimension> getCourseDimensionByPage(int id, int pageindex, int pagesize, String search) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "Select * from (\n"
                    + "SELECT ROW_NUMBER() OVER(ORDER BY id ASC) AS [Row],id,[type],name,description from [Course_Dimension] inner join Subject_Dimension\n"
                    + "on [Course_Dimension].dimension_id = Subject_Dimension.id\n"
                    + "where course_id=?\n";
            if (search != "") {
                sql += " and (type like '%" + search + "%' or name like '%" + search + "%')";
            }
            sql += ") as a\n"
                    + "where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, pageindex);
            statement.setInt(3, pagesize);
            statement.setInt(4, pageindex);
            statement.setInt(5, pagesize);
            System.out.println(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Dimension r = new Dimension();
                r.setId(rs.getInt("id"));
                r.setType(rs.getString("type"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                dimensions.add(r);
            }
            return dimensions;
        } catch (SQLException e) {
        }
        return null;

    }

    public ArrayList<Dimension> getCourseDimensionWithoutByPage(int id) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "Select * from Subject_Dimension where id not in\n"
                    + "(Select dimension_id from Course_Dimension where course_id=?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            System.out.println(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Dimension r = new Dimension();
                r.setId(rs.getInt("id"));
                r.setType(rs.getString("type"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                dimensions.add(r);
            }
            return dimensions;
        } catch (SQLException e) {
        }
        return null;

    }

    public int getTotal(int id, String search) {
        int total = 0;
        try {
            String sql = "SELECT COUNT(course_id) from [Course_Dimension] inner join Subject_Dimension\n"
                    + "              on [Course_Dimension].dimension_id = Subject_Dimension.id\n"
                    + "               where course_id=?";
            if (search != "") {
                sql += " and (type like '%" + search + "%' or name like '%" + search + "%')";
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }

    public void addDimension(String id, String did) {

        try {
            String sql = "INSERT INTO [Course_Dimension]\n"
                    + "           ([course_id]\n"
                    + "           ,[dimension_id])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, did);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteDimension(String id, String did) {

        try {
            String sql = "DELETE FROM [Course_Dimension]\n"
                    + "      WHERE course_id=? and dimension_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, did);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Dimension> getDimension() {
        ArrayList<Dimension> list = new ArrayList<>();
        String sql = "select * from Subject_Dimension";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension sd = new Dimension();
                sd.setId(rs.getInt("id"));
                sd.setName(rs.getString("name"));
                list.add(sd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateDimension(String id, String did, String status) {

        try {
            String sql = "UPDATE [Course_Dimension]\n"
                    + "   SET [status] = ? \n"
                    + " WHERE course_id = ? and dimension_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setString(2, id);
            statement.setString(3, did);
            System.out.println(sql);
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Dimension> getAllCourseDimensions(int pageindex, int pagesize, String search) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "Select * from\n"
                    + "(\n"
                    + "SELECT ROW_NUMBER() OVER(ORDER BY id ASC) AS [Row],id,[type],name,description from [SubJect_Dimension]";
            if (search != "") {
                sql += " where type like '%" + search + "%' or name like '%" + search + "%'";
            }
            sql += ") as a\n"
                    + "where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, pageindex);
            statement.setInt(2, pagesize);
            statement.setInt(3, pageindex);
            statement.setInt(4, pagesize);
            System.out.println(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Dimension r = new Dimension();
                r.setId(rs.getInt("id"));
                r.setType(rs.getString("type"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                dimensions.add(r);
            }
            return dimensions;
        } catch (SQLException e) {
        }
        return null;
    }

    public int getAllTotal(String search) {
        int total = 0;
        try {
            String sql = "SELECT COUNT(id) from [SubJect_Dimension] where(1=1)";
            if (search != "") {
                sql += " and (type like '%" + search + "%' or name like '%" + search + "%')";
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }

    public void deleteSubjectDimension(String id) {
        try {
            String sql0 = "DELETE FROM [Course_Dimension]\n"
                    + "      WHERE dimension_id=?";

            PreparedStatement statement = connection.prepareStatement(sql0);
            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String sql = "DELETE FROM [Subject_Dimension]\n"
                    + "      WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editDimension(String id, String type, String name, String description) {

        try {
            String sql = "UPDATE [Subject_Dimension]\n"
                    + "   SET [type] = ?\n"
                    + "      ,[name] = ?\n"
                    + "      ,[description] = ?\n"
                    + " WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setString(4, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addNewSubjectDimension(String type, String name, String description) {

        try {
            String sql = "INSERT INTO [Subject_Dimension]\n"
                    + "           ([type]\n"
                    + "           ,[name]\n"
                    + "           ,[description])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        System.out.println("aaaaaa");
        DimensionDAO dao = new DimensionDAO();
        ArrayList<Dimension> courseDimensionById = dao.getCourseDimensionWithoutByPage(1);

        for (Dimension a : courseDimensionById) {
            System.out.println(a.toString());
        }

    }
}
