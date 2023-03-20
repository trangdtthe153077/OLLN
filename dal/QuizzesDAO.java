/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Quizzes;

/**
 *
 * @author win
 */
public class QuizzesDAO extends BaseDAO<Quizzes> {

    public Quizzes getQuizById(int id) {
        try {
            String sql = "SELECT *FROM Quiz where id =" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Quizzes r = new Quizzes();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                Course c = new Course();
                c.setId(rs.getInt(3));
                r.setCourse(c);
                r.setQLevel(new LevelDAO().getLevelById(rs.getInt(4)));
                r.setNumberQuestion(rs.getInt(5));
                r.setDuration(rs.getInt(6));
                r.setPassrate(rs.getInt(7));
                r.setStatus(rs.getString(8));
                r.setDescription(rs.getString(9));
                return r;
            }

        } catch (SQLException e) {
        }
        return null;

    }

    public void updateQuizzes(int id, String name, int cid, int lid, String duration, int pass_rate, int status, String description) {
        try {
            String sql = "UPDATE [dbo].[Quiz]\n"
                    + "   SET [name] = ?\n"
                    + "      ,[course_id] = ?\n"
                    + "      ,[level_id] = ?\n"
                    + "      ,[duration] = ?\n"
                    + "      ,[pass_rate] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[description] =  ?\n"
                    + " WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            stm.setInt(2, cid);
            stm.setInt(3, lid);
            stm.setString(4, duration);
            stm.setInt(5, pass_rate);
            stm.setInt(6, status);
            stm.setString(7, description);
            stm.setInt(8, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // cid = null, status = -1, 
    public List<Quizzes> searchQuizzes(int[] cid, String name, int status, int pageIndex, int pageSize) {
        List<Quizzes> list = new ArrayList<>();
        try {
            String sql = "select *from\n"
                    + "   (Select ROW_NUMBER() OVER (ORDER BY q.id ASC) as [Row], c.id as cid, l.id as lid, q.id as qid, q.name , number_question, duration, pass_rate, status\n"
                    + "   from Quiz q inner join Course c\n"
                    + "   on q.course_id = c.id inner join [Level] l\n"
                    + "   on q.level_id = l.id\n"
                    + "   where (1=1)";

            if (cid != null) {
                sql += " and c.id in(";
                for (int i = 0; i < cid.length; i++) {
                    sql += cid[i] + ",";
                }
                if (sql.endsWith(",")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
                sql += ")";
            }
            HashMap<Integer, Object[]> parameters = new HashMap<>();
            int paramIndex = 0;
            if (name != null) {
                sql += "and q.name like ?";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = name;
                parameters.put(paramIndex, param);
            }
            if (status != -1) {
                sql += "and q.status = ?";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = status;
                parameters.put(paramIndex, param);
            }

            sql += " ) as T  where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            // dấu hỏi số 1 của where row_index >= ....
            paramIndex++;
            Object[] param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pageIndex;
            parameters.put(paramIndex, param);
            // dấu hỏi số 2 của where row_index >= ....
            paramIndex++;
            param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pageSize;
            parameters.put(paramIndex, param);
            // dấu hỏi số 3 của where row_index >= ....
            paramIndex++;
            param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pageSize;
            parameters.put(paramIndex, param);
            // dấu hỏi số 4 của where row_index >= ....
            paramIndex++;
            param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pageIndex;
            parameters.put(paramIndex, param);
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : parameters.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    statement.setInt(index, Integer.parseInt(value[1].toString()));
                } else if (type.equals(Date.class.getName())) {
                    statement.setDate(index, Date.valueOf(value[1].toString()));
                } else if (type.equals(String.class.getName())) {
                    statement.setString(index, "%" + value[1].toString() + "%");
                }
            }
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Quizzes r = new Quizzes();
                r.setCourse(new CourseDAO().getCourseById2(rs.getInt("cid")));
                r.setQLevel(new LevelDAO().getLevelById(rs.getInt("lid")));
                r.setId(rs.getInt("qid"));
                r.setName(rs.getString("name"));
                r.setNumberQuestion(rs.getInt("number_question"));
                r.setDuration(rs.getInt("duration"));
                r.setPassrate(rs.getInt("pass_rate"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizzesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;

    }

    public int countQuizzes(int[] cid, String name, int status) {
        try {
            String sql = "SELECT COUNT(*) as total from\n"
                    + "   (Select ROW_NUMBER() OVER (ORDER BY q.id ASC) as [Row], c.id as cid, l.id as lid, q.id as qid\n"
                    + "   from Quiz q inner join Course c\n"
                    + "   on q.course_id = c.id inner join [Level] l\n"
                    + "   on q.level_id = l.id\n"
                    + "   where (1=1)";

            if (cid != null) {
                sql += " and c.id in(";
                for (int i = 0; i < cid.length; i++) {
                    sql += cid[i] + ",";
                }
                if (sql.endsWith(",")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
                sql += ")";
            }
            HashMap<Integer, Object[]> parameters = new HashMap<>();
            int paramIndex = 0;
            if (name != null) {
                sql += "and q.name like ?";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = name;
                parameters.put(paramIndex, param);
            }
            if (status != -1) {
                sql += "and q.status = ?";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = status;
                parameters.put(paramIndex, param);
            }

            sql += " ) as T";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : parameters.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    statement.setInt(index, Integer.parseInt(value[1].toString()));
                } else if (type.equals(Date.class.getName())) {
                    statement.setDate(index, Date.valueOf(value[1].toString()));
                } else if (type.equals(String.class.getName())) {
                    statement.setString(index, "%" + value[1].toString() + "%");
                }
            }
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return rs.getInt("total");

            }
        } catch (SQLException e) {
        }
        return -1;

    }

    public static void main(String[] args) {
        QuizzesDAO qdao = new QuizzesDAO();
        List<Quizzes> list = new ArrayList<>();
        //      int[] a = null;
//        list = qdao.searchQuizzes(a, null, 1, 1, 6);
//        for (Quizzes quizzes : list) {
//            System.out.println(quizzes.toString());
//        }
        int[] b = {1, 2};
        int a = qdao.countQuizzes(b, null, 1);
        System.out.println(a);
    }
}
