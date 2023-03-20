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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author win
 */
public class UserDAO extends BaseDAO<User> {

    public User getAccountByUsernameAndPassword(String email, String pass) {
        try {
            String sql = "SELECT *FROM [User] WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, DigestUtils.md5Hex(pass));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                a.setActive(rs.getInt(10));
                return a;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public User getUserById(int id) {
        try {
            String sql = "SELECT *FROM [User] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                a.setActive(rs.getInt(10));
                return a;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public int getTotalCustomerByDate(String from, String to) {
        int total = 0;
        try {
            String sql = "select COUNT(id) as Total from [User] where date >= ? and date <= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, from);
            statement.setString(2, to);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getTotalCustomer() {
        int total = 0;
        try {
            String sql = "select COUNT(id) as Total from [User]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getTotalCustomerBoughtByDate(String from, String to) {
        int total = 0;
        try {
            String sql = "select COUNT(user_id) as Total from Registrations \n"
                    + "where registered_date >= ? and registered_date <= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, from);
            statement.setString(2, to);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }

    public void updateProfile(String username, String fullname, String phonenumber, Boolean gender, String email,String avatar) {
        try {
            String sql = "UPDATE [User]\n"
                    + "   SET [username] = ?\n"
                    + "      ,[fullname] = ?\n"
                    + "      ,[gender] = ?\n"
                    + "      ,[phone] = ?\n";
            
                    if(!avatar.equals(""))
                    sql+="      ,[avatar] = '"+avatar+"'";
                            
                    sql+= " WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, fullname);
            statement.setBoolean(3, gender);
            statement.setString(4, phonenumber);
            statement.setString(5, email);
            

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateRole(int role_id, int user_id) {
        try {
            String sql = "UPDATE [User]\n"
                    + "   SET [role_id] = ? WHERE id= ?\n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, role_id);
            statement.setInt(2, user_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateStatus(int active ,int user_id) {
        try {
            String sql = "UPDATE [User]\n"
                    + "   SET [active] = ? WHERE id= ?\n";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, active);
            statement.setInt(2, user_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<User> getAllExpert() {
        List<User> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [User] WHERE role_id = 4";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;

    }
//    public static void main(String[] args) {
//        UserDAO dao = new UserDAO();
//        dao.updateProfile("trang", "Trang", "0348927827", false, "trangdtthe153077@fpt.edu.vn");
//        User a = dao.getAccountByUsernameAndPassword("trangdtthe153077@fpt.edu.vn", "aaa123");
//        System.out.println(a);
//    List<User> listExpert = new ArrayList<>();
//     listExpert = dao.getAllExpert();
//        for (User user : listExpert) {
//            System.out.println(user);
//        }
//        
//    }

    public User checkAccountExist(String email) {
        try {
            String sql = "SELECT * FROM [User] WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User c = new User();
                c.setId(rs.getInt(1));
                c.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                c.setUsername(rs.getString(3));
                c.setPassword(rs.getString(4));
                c.setFullname(rs.getString(5));
                c.setGender(rs.getBoolean(6));
                c.setEmail(rs.getString(7));
                c.setPhone(rs.getString(8));
                c.setAvatar(rs.getString(9));
                c.setActive(rs.getInt(10));
                c.setHash(rs.getString(11));
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void SignUp(String email, String password, String fullname, String phone, boolean gender, String hash) {
        try {
            String sql = "INSERT INTO [User]\n"
                    + "           ([email]\n"
                    + "           ,[password]\n"
                    + "           ,[fullname]\n"
                    + "           ,[gender]\n"
                    + "           ,[phone]\n"
                    + "           ,[hash])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, DigestUtils.md5Hex(password));
            statement.setString(3, fullname);
            statement.setBoolean(4, gender);
            statement.setString(5, phone);
            statement.setString(6, hash);
            statement.executeUpdate();

            SendEmail se = new SendEmail(email, hash);
            se.sendMail();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void AdminAddUser(User user) {
        try {
            String sql = "INSERT INTO [User]\n"
                    + "           ([fullname]\n"
                    + "           ,[email]\n"
                    + "           ,[phone]\n"
                    + "           ,[address]\n"
                    + "           ,[gender]\n"
                    + "           ,[role_id]\n"
                    + "           ,[password]\n"
                    + "           ,[active]\n"
                    + "           ,[hash])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFullname());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getAddress());
            statement.setBoolean(5, user.isGender());
            statement.setInt(6, user.getRole().getId());
            statement.setString(7, DigestUtils.md5Hex(user.getPassword())); 
            statement.setInt(8, user.getActive());
            statement.setString(9, user.getHash());
            statement.executeUpdate();

            SendRandomPasstoEmail se = new SendRandomPasstoEmail(user.getEmail(), user.getPassword(), user.getFullname());
            se.sendMail();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String activeAccount(String email, String hash) {
        try {

            PreparedStatement pst = connection.prepareStatement("select username, hash, active from [User] where email=? and hash=?");
            pst.setString(1, email);
            pst.setString(2, hash);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                PreparedStatement pst1 = connection.prepareStatement("update [User] set active=1 where email=? and hash=?");
                pst1.setString(1, email);
                pst1.setString(2, hash);

                int i = pst1.executeUpdate();

                if (i != 0) {
                    return "Success";
                }
            }

        } catch (SQLException e) {
        }
        return "Error";
    }

    public void updatePassword(int id, String password) {
        String sql = "update [User] \n"
                + "set password = '" + password + "' "
                + "where id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {

        }
    }
    public User checkAccByEmail(String email) {
        String sql = "select * from [User] where email = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User account = new User(new RoleDAO().getRoleById(rs.getInt(2)), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getBoolean(6), rs.getString(7), rs.getString(8), rs.getString(9));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

public void Recover(String email, String password) {
        String sql = "update [User] set [password] = ? where email = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, password);
            stm.setString(2, email);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeCaptcha(String email) {
        String sql = "DELETE FROM [Verification] WHERE email = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCaptcha(String email, String captcha) {
        String sql = "INSERT INTO [Verification] ([email], [captcha], [lifetime]) \n"
                + "VALUES (?, ?, DATEDIFF(SECOND,'1970-01-01', DATEADD(SECOND, 180 ,GETUTCDATE())))";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, captcha);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User checkCaptcha(String captcha, String email) {
        String sql = "select * from Verification where email = ? and captcha like ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, captcha);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User account = new User();
                account.setEmail(email);
                account.setCaptcha(captcha);
                account.setLifetime(rs.getInt(3));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

     public List<User> getAllUserList() {
        List<User> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [User]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User r = new User();
                r.setId(rs.getInt(1));
                r.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                r.setUsername(rs.getString(3));
                r.setPassword(rs.getString(4));
                r.setFullname(rs.getString(5));
                r.setGender(rs.getBoolean(6));
                r.setEmail(rs.getString(7));
                r.setPhone(rs.getString(8));
                r.setAvatar(rs.getString(9));
                r.setActive(rs.getInt(10));
                list.add(r);
            }
            
        } catch (SQLException e) {
        }
        return list;
    }
     
     public List<User> SortIdAsc() {
        List<User> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [User] ORDER BY id "  ;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                a.setActive(rs.getInt(10));
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;

    }
 public List<User> SortIdDesc() {
        List<User> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM [User] ORDER BY id DESC"  ;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                a.setActive(rs.getInt(10));
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;

    }

   public List<User> searchSort(String name, int sort) {
        List<User> list = new ArrayList<>();
        try {

            String sql = "select * from [User] where username like '%"+name+"%'";
            StringBuilder sb = new StringBuilder(sql);
            if(sort == 2){
                sb.append(" ORDER BY [id] ASC");
            }
            if(sort == 3){
                sb.append(" ORDER BY [id] DESC");
            }
            PreparedStatement statement = connection.prepareStatement(sb.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                a.setActive(rs.getInt(10));
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    public List<User> searchNAmeUser(String name) {
        List<User> list = new ArrayList<>();
        try {
            String sql = "select * from [User] where username like '%"+name+"%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt(1));
                a.setRole(new RoleDAO().getRoleById(rs.getInt(2)));
                a.setUsername(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setFullname(rs.getString(5));
                a.setGender(rs.getBoolean(6));
                a.setEmail(rs.getString(7));
                a.setPhone(rs.getString(8));
                a.setAvatar(rs.getString(9));
                a.setActive(rs.getInt(10));
                list.add(a);
            }
        } catch (SQLException e) {
        }
        return list;
    }

     public void UpdateRegister(String userid, String id) {
        String date = null;
        int priceid = 0;
        try {
            String sql0 = " Select valid_from, price_id from [Registrations]\n"
                    + "  WHERE [user_id]=? and course_id=?";
            PreparedStatement statement = connection.prepareStatement(sql0);
            statement.setString(1, userid);
            statement.setString(2, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                date = rs.getString("valid_from");
                priceid = rs.getInt("price_id");
            }
            System.out.println(date + priceid);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (date == null) {
            try {
                String sql = "UPDATE [Registrations]\n"
                        + "   SET \n"
                        + "      [valid_from] = GETDATE()\n"
                        + "      ,[valid_to] = DATEADD(month, (Select duration from Price_Package where id=?), GETDATE())\n"
                        + " WHERE [user_id]=? and course_id=?\n"
                        + " ";
                System.out.println("dong2" + priceid + userid + id);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, priceid);
                statement.setString(2, userid);
                statement.setString(3, id);
                statement.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     public boolean isAuthenticated(int id, String feature) {
        try {
            String sql = "Select * from [Authorization] inner join [Feature]\n"
                    + "on [Authorization].fid =[Feature].fid\n"
                    + "where rid=? and Name like ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, feature);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
             return true;
            }
        } catch (SQLException e) {
        }
        return false;

    }


}
