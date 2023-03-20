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
import model.Price_Package;

/**
 *
 * @author win
 */
public class PricePackageDAO extends BaseDAO<Price_Package>{
    public Price_Package getPricePackageById(int id) {
        try {
            String sql = " SELECT *FROM [Price_Package] WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Price_Package r = new Price_Package();
                r.setId(rs.getInt(1));
               r.setStatus(rs.getString(2));
               r.setName(rs.getString(3));
               r.setDuration(rs.getInt(4));
               r.setListprice(rs.getInt(5));
               r.setSaleprice(rs.getInt(6));
               r.setDescription(rs.getString(7));
               
               return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public Price_Package getPricePackage(String id) {
         Price_Package r = new Price_Package();
        try {
            String sql = "SELECT TOP 1000 [id]\n"
                    + "      ,[status]\n"
                    + "      ,[name]\n"
                    + "      ,[duration]\n"
                    + "      ,[list_price]\n"
                    + "      ,[sale_price]\n"
                    + "      ,[description]\n"
                    + "  FROM [Price_Package] \n"
                    + "  where id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setStatus(rs.getString("status"));
                r.setName(rs.getString("name"));
                r.setDuration(rs.getInt("duration"));
                r.setListprice(rs.getInt("list_price"));
                r.setSaleprice(rs.getInt("sale_price"));
                r.setDescription(rs.getString("description"));

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return r;

    }
     public ArrayList<Price_Package> getAllPricePackage() {
        ArrayList<Price_Package> pricepkg = new ArrayList<Price_Package>();
        try {
            String sql = " SELECT *FROM [Price_Package]";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Price_Package r = new Price_Package();
                r.setId(rs.getInt(1));
                r.setStatus(rs.getString(2));
                r.setName(rs.getString(3));
                r.setDuration(rs.getInt(4));
                r.setListprice(rs.getInt(5));
                r.setSaleprice(rs.getInt(6));
                r.setDescription(rs.getString(7));

                pricepkg.add(r);
            }
            return pricepkg;
        } catch (SQLException e) {
        }
        return null;

    }

    public ArrayList<Price_Package> getAllPricePackagebyId(int id) {
        ArrayList<Price_Package> pricepkg = new ArrayList<Price_Package>();
        try {
            String sql = "Select id,name,duration,list_price,sale_price,status from Registrations\n"
                    + "              inner join Price_Package\n"
                    + "              on price_id=Price_Package.id\n"
                    + "               where course_id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Price_Package r = new Price_Package();
                r.setId(rs.getInt(1));
                r.setStatus(rs.getString(2));
                r.setName(rs.getString(3));
                r.setDuration(rs.getInt(4));
                r.setListprice(rs.getInt(5));
                r.setSaleprice(rs.getInt(6));
                r.setDescription(rs.getString(7));

                pricepkg.add(r);
            }
            return pricepkg;
        } catch (SQLException e) {
        }
        return null;

    }

    public ArrayList<Price_Package> getAllPricePackagebyPage(int id, int pageindex, int pagesize, String search) {
        ArrayList<Price_Package> pricepkg = new ArrayList<Price_Package>();
        try {
            String sql = "              Select * from\n"
                    + "              (\n"
                    + "              Select ROW_NUMBER() OVER(ORDER BY id ASC) AS [Row], id,name,duration,list_price,sale_price,status from course_package\n"
                    + "              inner join Price_Package\n"
                    + "              on pricepackage_id=Price_Package.id\n"
                    + "               where course_id =?\n"
                    + ") as a \n"
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
                Price_Package r = new Price_Package();
                r.setId(rs.getInt("id"));
                r.setStatus(rs.getString("status"));
                r.setName(rs.getString("name"));
                r.setDuration(rs.getInt("duration"));
                r.setListprice(rs.getInt("list_price"));
                r.setSaleprice(rs.getInt("sale_price"));

                pricepkg.add(r);
            }
            return pricepkg;
        } catch (SQLException e) {
        }
        return null;

    }

    public ArrayList<Price_Package> searchAllPricePackagebyPage(int id, int pageindex, int pagesize, String search, String min, String max) {
        ArrayList<Price_Package> pricepkg = new ArrayList<Price_Package>();
        try {
            String sql = " Select * from\n"
                    + "      (\n"
                    + "                          Select ROW_NUMBER() OVER(ORDER BY id ASC) AS [Row], id,name,duration,list_price,sale_price,status from course_package\n"
                    + "                           inner join Price_Package\n"
                    + "                           on pricepackage_id=Price_Package.id\n"
                    + "                            where course_id =?\n";
            if (!search.equals("")) {
                sql += "                            and (name like '%" + search + "%' or duration like '%" + search + "%' or status like '%" + search + "%')\n";
            }
            if (!min.equals("") && !max.equals("")) {
                sql += "                            and(sale_price>=" + min + " and list_price<=" + max + ")\n";
            }
            sql += "                  ) as a\n"
                    + "                    where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, pageindex);
            statement.setInt(3, pagesize);
            statement.setInt(4, pageindex);
            statement.setInt(5, pagesize);
            System.out.println(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Price_Package r = new Price_Package();
                r.setId(rs.getInt("id"));
                r.setStatus(rs.getString("status"));
                r.setName(rs.getString("name"));
                r.setDuration(rs.getInt("duration"));
                r.setListprice(rs.getInt("list_price"));
                r.setSaleprice(rs.getInt("sale_price"));

                pricepkg.add(r);
            }
            return pricepkg;
        } catch (SQLException e) {
        }
        return null;

    }

    public int getsearchTotal(int id, String search, String min, String max) {
        int total = 0;
        try {
            String sql = "  Select COUNT(id) from course_package\n"
                    + "              inner join Price_Package\n"
                    + "              on pricepackage_id=Price_Package.id\n"
                    + "               where course_id =?";
            if (!search.equals("")) {
                sql += "                            and (name like '%" + search + "%' or duration like '%" + search + "%' or status like '%" + search + "%')\n";
            }
            if (!min.equals("") && !max.equals("")) {
                sql += "                            and(sale_price>=" + min + " and list_price<=" + max + ")\n";
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

    public int getTotal(int id) {
        int total = 0;
        try {
            String sql = "  Select COUNT(id) from course_package\n"
                    + "              inner join Price_Package\n"
                    + "              on pricepackage_id=Price_Package.id\n"
                    + "               where course_id =?";

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

   

    public ArrayList<Price_Package> getAllPricePackageWithout(int id) {
        ArrayList<Price_Package> pricepkg = new ArrayList<Price_Package>();
        try {
            String sql = "Select * from Price_Package where id not in(\n"
                    + "SELECT pricepackage_id FROM [course_package] where course_id=?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Price_Package r = new Price_Package();
                r.setId(rs.getInt("id"));
                r.setStatus(rs.getString("status"));
                r.setName(rs.getString("name"));
                r.setDuration(rs.getInt("duration"));
                r.setListprice(rs.getInt("list_price"));
                r.setSaleprice(rs.getInt("sale_price"));

                pricepkg.add(r);
            }
            return pricepkg;
        } catch (SQLException e) {
        }
        return null;

    }


    public void insertNewPricePackage(String id, String pid) {
        Price_Package r = new Price_Package();
        try {
            String sql = "INSERT INTO [course_package]\n"
                    + "           ([course_id]\n"
                    + "           ,[pricepackage_id])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, pid);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void deletePricePackage(String id, String pid) {

        try {
            String sql = "DELETE FROM [course_package]\n"
                    + "      WHERE course_id=? and pricepackage_id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, pid);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public ArrayList<Price_Package> getAllPricePackages(int pageindex, int pagesize, String search, String min, String max) {
        ArrayList<Price_Package> pricepkg = new ArrayList<Price_Package>();
        try {
            String sql = "Select * from\n"
                    + " (\n"
                    + " SELECT ROW_NUMBER() OVER(ORDER BY id ASC) AS [Row], id,name,duration,list_price,sale_price,status,description from [Price_Package]  where (1=1)\n";
            if (!search.equals("")) {
                sql += "                            and (name like '%" + search + "%' or duration like '%" + search + "%' or status like '%" + search + "%')\n";
            }
            if (!min.equals("") && !max.equals("")) {
                sql += "                            and(sale_price>=" + min + " and list_price<=" + max + ")\n";
            }
            sql += ") as a\n"
                    + "                    where [Row]>=(?-1)*?+1 and [Row]<=?*? ";
            System.out.println(sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pageindex);
            statement.setInt(2, pagesize);
            statement.setInt(3, pageindex);
            statement.setInt(4, pagesize);
            System.out.println(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Price_Package r = new Price_Package();

                r.setId(rs.getInt("id"));
                r.setStatus(rs.getString("status"));
                r.setName(rs.getString("name"));
                r.setDuration(rs.getInt("duration"));
                r.setListprice(rs.getInt("list_price"));
                r.setSaleprice(rs.getInt("sale_price"));
                r.setDescription(rs.getString("description"));

                pricepkg.add(r);
            }
            return pricepkg;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

 public int getAllTotal(String search, String min, String max) {
        int total = 0;
        try {
            String sql = "Select * from\n"
                    + " (\n"
                    + " SELECT COUNT(id) from [Price_Package]  where (1=1)";
            if (!search.equals("")) {
                sql += "                            and (name like '%" + search + "%' or duration like '%" + search + "%' or status like '%" + search + "%')\n";
            }
            if (!min.equals("") && !max.equals("")) {
                sql += "                            and(sale_price>=" + min + " and list_price<=" + max + ")\n";
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
public void deletePricePackage(String id) {
        Price_Package r = new Price_Package();
        try {
            String sql = "DELETE FROM [Price_Package]\n"
                    + "      WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
 public void editPricePackage(String id, String name, String duration, String listprice, String saleprice, String description, String status) {

        try {
            String sql = "UPDATE [Price_Package]\n"
                    + "   SET [status] = ?\n"
                    + "      ,[name] = ?\n"
                    + "      ,[duration] = ?\n"
                    + "      ,[list_price] =?\n"
                    + "      ,[sale_price] = ?\n"
                    + "      ,[description] = ?\n"
                    + " WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setString(2, name);
            statement.setString(3, duration);
            statement.setString(4, listprice);
            statement.setString(5, saleprice);
            statement.setString(6, description);
            statement.setString(7, id);

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

public void insertNewPricePackage(String name, String duration, String listprice, String saleprice, String description, String status) {
        Price_Package r = new Price_Package();
        try {
            String sql = "INSERT INTO [Price_Package]\n"
                    + "           ([status]\n"
                    + "           ,[name]\n"
                    + "           ,[duration]\n"
                    + "           ,[list_price]\n"
                    + "           ,[sale_price]\n"
                    + "           ,[description])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setString(2, name);
            statement.setString(3, duration);
            statement.setString(4, listprice);
            statement.setString(5, saleprice);
            statement.setString(6, description);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }


    public static void main(String[] args) {
        PricePackageDAO dao = new PricePackageDAO();
        ArrayList<Price_Package> allPricePackagebyPage = dao.getAllPricePackageWithout(1);
        for (Price_Package a : allPricePackagebyPage) {
            System.out.println(a);
        }
    }


}
