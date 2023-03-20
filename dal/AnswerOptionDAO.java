/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AnswerOption;

/**
 *
 * @author Laptop88
 */
public class AnswerOptionDAO extends BaseDAO<AnswerOption> {

    public void AddAnswer(String qid, String content, String isTrue) {

        try {
            String sql = "INSERT INTO [dbo].[AnswerOption]\n"
                    + "           ([questionID]\n"
                    + "           ,[content]\n"
                    + "           ,[isTrue])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, qid);
            statement.setString(2, content);
            statement.setString(3, isTrue);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerOptionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteAnswer(String aid) {
        try {
            String sql = "DELETE FROM [dbo].[AnswerOption]\n"
                    + "      WHERE answerID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, aid);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerOptionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
