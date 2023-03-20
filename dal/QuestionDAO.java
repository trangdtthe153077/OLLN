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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AnswerOption;
import model.Course;
import model.Question;
import model.QLevel;
import model.Dimension;

/**
 *
 * @author DELL
 */
public class QuestionDAO extends BaseDAO<Question> {
 public ArrayList<Question> getQSByQuizID(int quizID) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select a.id, a.content, a.level_id, b.level_name \n"
                    + "from Question as a inner join [Level] as b on a.level_id = b.id\n"
                    + "where a.quiz_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quizID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setContent(rs.getString("content"));

                QLevel level = new QLevel();
                level.setId(rs.getInt("level_id"));
                level.setName(rs.getString("level_name"));
                q.setLevel(level);

                ArrayList<AnswerOption> options = getOptionByQid(q.getId());
                q.setAnswers(options);
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<AnswerOption> getOptionByQid(int qid) {
        ArrayList<AnswerOption> list = new ArrayList<>();
        try {
            String sql = "select * from AnswerOption where questionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, qid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AnswerOption o = new AnswerOption();
                o.setId(rs.getInt("answerID"));
                o.setOption(rs.getString("content"));
                o.setIsTrue(rs.getBoolean("isTrue"));
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

//    public static void main(String[] args) {
//        QuestionDAO qdb = new QuestionDAO();
//        ArrayList<Question> questions = qdb.getQSByQuizID(1);
//        System.out.println("number of question in quiz: " + questions.size());
//        ArrayList<AnswerOption> options = qdb.getOptionByQid(questions.get(0).getId());
//        System.out.println(options.toString());
//    }
    public ArrayList<Question> getQSBank() {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select a.*, b.name as 'coursename', c.name as 'dimensionname', \n"
                    + "d.level_name as 'levelname', e.content as 'answer' from Question as a\n"
                    + "inner join Course as b on a.course_id = b.id\n"
                    + "inner join Subject_Dimension as c on a.dimension_id = c.id\n"
                    + "inner join [Level] as d on a.level_id = d.id\n"
                    + "inner join [AnswerOption] as e on a.id = e.questionID\n"
                    + "where e.isTrue = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question qs = new Question();
                qs.setId(rs.getInt("id"));
                qs.setContent(rs.getString("content"));
                qs.setStatus(rs.getBoolean("status"));

                Course c = new Course();
                c.setId(rs.getInt("course_id"));
                c.setName(rs.getString("coursename"));
                qs.setCourse(c);

                Dimension sd = new Dimension(rs.getInt("dimension_id"), rs.getString("dimensionname"));
                qs.setDimension(sd);

                QLevel l = new QLevel(rs.getInt("level_id"), rs.getString("levelname"));
                qs.setLevel(l);

                ArrayList<AnswerOption> ans = new ArrayList<>();
                ans.add(new AnswerOption(rs.getString("answer")));
                qs.setAnswers(ans);
                list.add(qs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<QLevel> getQLevel() {
        ArrayList<QLevel> list = new ArrayList<>();
        String sql = "select * from [Level]";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QLevel l = new QLevel(rs.getInt("id"), rs.getString("level_name"));
                list.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

//            list = qsDB.getQSBankFilter(cid, did, lid, status, pageindex, pagesize);
    public ArrayList<Question> getQSBankFilter(int cid, int did, int lid, int status,
            int pageindex, int pagesize) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select * from \n"
                    + "(select ROW_NUMBER() over (order by a.id asc) as row_index, a.*, "
                    + "b.name as 'coursename', c.name as 'dimensionname', \n"
                    + "d.level_name as 'levelname', e.content as 'answer' from Question as a\n"
                    + "inner join Course as b on a.course_id = b.id\n"
                    + "inner join Subject_Dimension as c on a.dimension_id = c.id\n"
                    + "inner join [Level] as d on a.level_id = d.id\n"
                    + "inner join [AnswerOption] as e on a.id = e.questionID\n"
                    + "where e.isTrue = 1 and (1=1) ";
            HashMap<Integer, Object[]> parameters = new HashMap<>();
            int paramIndex = 0;
            if (cid != -1) {
                sql += " and b.id = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = cid;
                parameters.put(paramIndex, param);
            }
            if (did != -1) {
                sql += " and c.id = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = did;
                parameters.put(paramIndex, param);
            }
            if (lid != -1) {
                sql += " and d.id = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = lid;
                parameters.put(paramIndex, param);
            }
            if (status != -1) {
                sql += " and a.status = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = status;
                parameters.put(paramIndex, param);
            }

            sql += " ) as tbl\n"
                    + "where row_index >= (?-1) * ? + 1 \n"
                    + "and row_index <=  ?*?";
            // dấu hỏi số 1 của where row_index >= ....
            paramIndex++;
            Object[] param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pageindex;
            parameters.put(paramIndex, param);
            // dấu hỏi số 2 của where row_index >= ....
            paramIndex++;
            param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pagesize;
            parameters.put(paramIndex, param);
            // dấu hỏi số 3 của where row_index >= ....
            paramIndex++;
            param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pageindex;
            parameters.put(paramIndex, param);
            // dấu hỏi số 4 của where row_index >= ....
            paramIndex++;
            param = new Object[2];
            param[0] = Integer.class.getTypeName();
            param[1] = pagesize;
            parameters.put(paramIndex, param);
            PreparedStatement stm = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : parameters.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    stm.setInt(index, Integer.parseInt(value[1].toString()));
                } else if (type.equals(Date.class.getName())) {
                    stm.setDate(index, Date.valueOf(value[1].toString()));
                } else if (type.equals(String.class.getName())) {
                    stm.setString(index, "%" + value[1].toString() + "%");
                }
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question qs = new Question();
                qs.setId(rs.getInt("id"));
                qs.setContent(rs.getString("content"));
                qs.setStatus(rs.getBoolean("status"));

                Course c = new Course();
                c.setId(rs.getInt("course_id"));
                c.setName(rs.getString("coursename"));
                qs.setCourse(c);

                Dimension sd = new Dimension(rs.getInt("dimension_id"), rs.getString("dimensionname"));
                qs.setDimension(sd);

                QLevel l = new QLevel(rs.getInt("level_id"), rs.getString("levelname"));
                qs.setLevel(l);

                ArrayList<AnswerOption> ans = new ArrayList<>();
                ans.add(new AnswerOption(rs.getString("answer")));
                qs.setAnswers(ans);
                list.add(qs);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int count(int cid, int did, int lid, int status) {
        try {
            String sql = "select count(*) as 'total' from Question as a\n"
                    + "inner join Course as b on a.course_id = b.id\n"
                    + "inner join Subject_Dimension as c on a.dimension_id = c.id\n"
                    + "inner join [Level] as d on a.level_id = d.id\n"
                    + "inner join [AnswerOption] as e on a.id = e.questionID\n"
                    + "where e.isTrue = 1 and (1=1) ";
            HashMap<Integer, Object[]> parameters = new HashMap<>();
            int paramIndex = 0;
            if (cid != -1) {
                sql += " and b.id = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = cid;
                parameters.put(paramIndex, param);
            }
            if (did != -1) {
                sql += " and c.id = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = did;
                parameters.put(paramIndex, param);
            }
            if (lid != -1) {
                sql += " and d.id = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = lid;
                parameters.put(paramIndex, param);
            }
            if (status != -1) {
                sql += " and a.status = ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = status;
                parameters.put(paramIndex, param);
            }

            PreparedStatement stm = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : parameters.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    stm.setInt(index, Integer.parseInt(value[1].toString()));
                } else if (type.equals(Date.class.getName())) {
                    stm.setDate(index, Date.valueOf(value[1].toString()));
                } else if (type.equals(String.class.getName())) {
                    stm.setString(index, "%" + value[1].toString() + "%");
                }
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getIDLevelByName(String level) {
        String sql = "select id from Level where level_name like ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, level);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void insertQuestion(Question q) {
        try {
            String sql_qs = "INSERT INTO [Question]\n"
                    + "           ([course_id]\n"
                    + "           ,[dimension_id]\n"
                    + "           ,[level_id]\n"
                    + "           ,[status]\n"
                    + "           ,[content]\n"
                    + "           ,[media]\n"
                    + "           ,[explain])\n"
                    + "     VALUES (?, ?, ?, 1, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql_qs);
            stm.setInt(1, q.getCourse().getId());
            stm.setInt(2, q.getDimension().getId());
            stm.setInt(3, q.getLevel().getId());
            stm.setString(4, q.getContent());
            stm.setString(5, q.getMedia());
            stm.setString(6, q.getExplain());
            stm.executeUpdate();

            int qid = getIDLatestQs();
            String sql_option = "INSERT INTO [AnswerOption]\n"
                    + "           ([questionID]\n"
                    + "           ,[content]\n"
                    + "           ,[isTrue])\n"
                    + "     VALUES (?, ?, ?)";
            for (AnswerOption ans : q.getAnswers()) {
                PreparedStatement stm_option = connection.prepareStatement(sql_option);
                stm_option.setInt(1, qid);
                stm_option.setString(2, ans.getOption());
                stm_option.setBoolean(3, ans.isIsTrue());
                stm_option.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getIDLatestQs() {
        try {
            String sql = "select top 1 id from Question order by id desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    ArrayList<Integer> getIdOptionCorrect(int qid) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            String sql = "select answerID, questionID, content, isTrue from AnswerOption \n"
                    + "where questionID = ? and isTrue = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, qid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                list.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public Question getQSById(int qid) {
        ArrayList<AnswerOption> ans = new ArrayList<>();
        Question qs = new Question();
        try {
            String sql = "select a.*, b.name as 'coursename', c.name as 'dimensionname',\n"
                    + "d.level_name as 'levelname', e.content as 'answer', e.isTrue, e.answerID from Question as a\n"
                    + "inner join Course as b on a.course_id = b.id\n"
                    + "inner join Subject_Dimension as c on a.dimension_id = c.id\n"
                    + "inner join [Level] as d on a.level_id = d.id\n"
                    + "inner join [AnswerOption] as e on a.id = e.questionID\n"
                    + "where a.id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, qid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                qs.setId(rs.getInt("id"));
                qs.setContent(rs.getString("content"));
                qs.setStatus(rs.getBoolean("status"));

                Course c = new Course();
                c.setId(rs.getInt("course_id"));
                c.setName(rs.getString("coursename"));
                qs.setCourse(c);

                Dimension sd = new Dimension(rs.getInt("dimension_id"), rs.getString("dimensionname"));
                qs.setDimension(sd);

                QLevel l = new QLevel(rs.getInt("level_id"), rs.getString("levelname"));
                qs.setLevel(l);

                AnswerOption a = new AnswerOption();
                a.setId(rs.getInt("answerID"));
                a.setOption(rs.getString("answer"));
                a.setIsTrue(rs.getBoolean("isTrue"));
                ans.add(a);
                qs.setAnswers(ans);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qs;
    }

    public static void main(String[] args) {
        QuestionDAO qd = new QuestionDAO();
        Question q = qd.getQSById(1);
        for (AnswerOption a : q.getAnswers()) {
            System.out.println(a.getOption());
        }
    }

    public void updateQuestion(String qid, String coursename, String gradeselect, String subjectselect, String dimension, String status, String describe, String thumbnail) {
        try {
            String sql = "UPDATE [dbo].[Question]\n"
                    + "   SET\n"
                    + "      [dimension_id] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[content] = ?\n"
                    + "      ,[media] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, dimension);
            stm.setString(2, status);
            stm.setString(3, describe);
            stm.setString(4, thumbnail);
            stm.setString(5, qid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
