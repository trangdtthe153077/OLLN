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
import model.CoursePackage;

/**
 *
 * @author win
 */
public class CoursePackageDAO extends BaseDAO<CoursePackage> {

    public CoursePackage getCoursePackageById(int id) {
        try {
            String sql = " SELECT *FROM [course_package] WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CoursePackage r = new CoursePackage();
                r.setCid(new CourseDAO().getCourseById2(rs.getInt(1)));
                r.setPpid(new PricePackageDAO().getPricePackageById(2));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public List<CoursePackage> getAllCourseWithPriceByName(String txt) {
        List<CoursePackage> list = new ArrayList<>();
        try {
            String sql = "select  c.id , pp.id \n"
                    + "   from Course c inner join course_package cp \n"
                    + "   on c.id = cp.course_id join Price_Package pp\n"
                    + "   on cp.pricepackage_id = pp.id\n"
                    + "   where duration = (select MIN(duration) from Price_Package) and status_id = 1 and c.name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + txt + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CoursePackage r = new CoursePackage();
                r.setCid(new CourseDAO().getCourseById1(rs.getInt(1)));
                r.setPpid(new PricePackageDAO().getPricePackageById(rs.getInt(2)));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }

    public CoursePackage getCourseAndPackage(int course_id) {
        try {
            String sql = "Select *\n"
                    + "from  Course_package\n"
                    + "where pricepackage_id = (select MIN(pricepackage_id) from Course_package) AND course_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, course_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CoursePackage r = new CoursePackage();
                r.setCid(new CourseDAO().getCourseById1(rs.getInt("course_id")));
                r.setPpid(new PricePackageDAO().getPricePackageById(rs.getInt("pricepackage_id")));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }
    public int getRevenuesByCategory(String cate, String from, String to) {
        int total = 0;
        try {
            String sql = "select sum(sale_price) as total from Registrations r inner join Price_Package pp\n"
                    + "on r.price_id = pp.id inner join Course c\n"
                    + "on r.course_id = c.id  where c.name like ? and registered_date >= ? and registered_date <=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" +cate+"%");
            statement.setString(2, from);
            statement.setString(3, to);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    
    public List<CoursePackage> getAllCourseWithPriceByName(String txt, int pageIndex, int pageSize) {
        List<CoursePackage> list = new ArrayList<>();
        try {
            String sql = "select *from\n"
                    + " (Select ROW_NUMBER() OVER (ORDER BY c.id ASC) as [Row], c.id as cid, pp.id as ppid \n"
                    + "   from Course c inner join course_package cp \n"
                    + "   on c.id = cp.course_id join Price_Package pp\n"
                    + "   on cp.pricepackage_id = pp.id inner join Category_SubCategory_Course csc\n"
                    + "   on csc.id = c.category_id\n"
                    + "   where duration = (select MIN(duration) from Price_Package)and c.name LIKE ? ) as T  where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + txt + "%");
            statement.setInt(2, pageIndex);
            statement.setInt(3, pageSize);
            statement.setInt(4, pageIndex);
            statement.setInt(5, pageSize);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CoursePackage r = new CoursePackage();
                r.setCid(new CourseDAO().getCourseById1(rs.getInt("cid")));
                r.setPpid(new PricePackageDAO().getPricePackageById(rs.getInt("ppid")));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }
    public int getRevenuesCurentW() {
        int total = 0;
        try {
            String sql = "select Sum(sale_price)  from\n"
                    + "(select top 7  registered_date, sale_price from Registrations r \n"
                    + "inner join Price_Package pp on r.price_id = pp.id \n"
                    + "group by registered_date,sale_price\n"
                    + "Order by registered_date desc) as total";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getRevenuesPreviousW(int a) {
        int total = 0;
        try {
            String sql = "select Sum(sale_price)  from\n"
                    + "(select top 14  registered_date, sale_price from Registrations r \n"
                    + "inner join Price_Package pp on r.price_id = pp.id \n"
                    + "group by registered_date,sale_price\n"
                    + "Order by registered_date desc) as total";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1) - a;
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getToatlRevenues() {
        int total = 0;
        try {
            String sql = "select Sum(sale_price) from Registrations r \n"
                    + "inner join Price_Package pp on r.price_id = pp.id ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getRevenuesMonC(int a) {
        int total = 0;
        try {
            String sql = "select *from\n"
                    + "(select ROW_NUMBER() OVER (\n"
                    + "	ORDER BY registered_date desc\n"
                    + "   ) as row_num,d.t, d.registered_date from\n"
                    + "(select  top 14 Sum(sale_price) as t, registered_date from\n"
                    + "(select registered_date, sale_price from Registrations r \n"
                    + " right join Price_Package pp on r.price_id = pp.id ) as total \n"
                    + "group by registered_date ORDER BY registered_date desc ) as d \n"
                    + "\n"
                    + ") as too where row_num = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, a);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(2);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getRevenuesByCategory(int a, String cate) {
        int total = 0;
        try {
            String sql = "select *from(select ROW_NUMBER() OVER (ORDER BY registered_date desc) \n"
                    + "as row_num,d.t, d.registered_date from\n"
                    + "(select  top 14 Sum(sale_price) as t, registered_date from\n"
                    + "(select registered_date, sale_price from Registrations r\n"
                    + "right join Price_Package pp on r.price_id = pp.id  inner join Course c\n"
                    + "on r.course_id = c.id inner join Category_SubCategory_Course csc \n"
                    + "on csc.category_id = c.category_id inner join Category_Course cc\n"
                    + "on cc.id = csc.subcategory_id  where cc.name = ?) as total \n"
                    + "group by registered_date ORDER BY registered_date desc ) as d ) as too where row_num = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cate);
            statement.setInt(2, a);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(2);
            }

        } catch (SQLException e) {
        }
        return total;

    }

    public List<CoursePackage> getAllCourseWithPrice(int pageIndex, int pageSize) {
        List<CoursePackage> list = new ArrayList<>();
        try {
            String sql = "select *from\n"
                    + " (Select ROW_NUMBER() OVER (ORDER BY c.id ASC) as [Row], c.id as cid, pp.id as ppid \n"
                    + "   from Course c inner join course_package cp \n"
                    + "   on c.id = cp.course_id join Price_Package pp\n"
                    + "   on cp.pricepackage_id = pp.id inner join Category_SubCategory_Course csc\n"
                    + "   on csc.id = c.category_id\n"
                    + "   where duration = (select MIN(duration) from Price_Package)) as T  where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pageIndex);
            statement.setInt(2, pageSize);
            statement.setInt(3, pageIndex);
            statement.setInt(4, pageSize);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CoursePackage r = new CoursePackage();
                r.setCid(new CourseDAO().getCourseById1(rs.getInt("cid")));
                r.setPpid(new PricePackageDAO().getPricePackageById(rs.getInt("ppid")));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }
    public int getCountCourse() {
        try {
            String sql = "SELECT COUNT(*) from\n"
                    + " (Select ROW_NUMBER() OVER (ORDER BY c.id ASC) as [Row], c.id as cid, pp.id as ppid \n"
                    + "   from Course c inner join course_package cp \n"
                    + "   on c.id = cp.course_id join Price_Package pp\n"
                    + "   on cp.pricepackage_id = pp.id inner join Category_SubCategory_Course csc\n"
                    + "   on csc.id = c.category_id\n"
                    + "   where duration = (select MIN(duration) from Price_Package)) as Total";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                
                return rs.getInt("Total");
            }
        } catch (Exception e) {
        }
        return -1;
    }

    //ccid = null, cid = null, date begin and end null, feature = -1, 
    public List<CoursePackage> searchCourses(int[] ccid, int[] cid, String name, Date begin, Date end, int feature, int pageIndex, int pageSize) {
        List<CoursePackage> list = new ArrayList<>();
        try {
            String sql = "select *from\n"
                    + "   (Select ROW_NUMBER() OVER (ORDER BY c.id ASC) as [Row], c.id as cid, pp.id as ppid from Course c inner join course_package cp \n"
                    + "   on c.id = cp.course_id join Price_Package pp\n"
                    + "   on cp.pricepackage_id = pp.id inner join Category_SubCategory_Course csc\n"
                    + "   on csc.id = c.category_id\n"
                    + "   where duration = (select MIN(duration) from Price_Package) \n"
                    + "   and (1=1)";

            if (ccid != null) {
                sql += "and csc.category_id in(";
                for (int i = 0; i < ccid.length; i++) {
                    sql += ccid[i] + ",";
                }
                if (sql.endsWith(",")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
                sql += ")";
            }
            if (cid != null) {
                sql += "and csc.subcategory_id in(";
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
                sql += "And c.name like ?";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = name;
                parameters.put(paramIndex, param);
            }
            if (begin != null) {
                sql += " and c.date >= ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Date.class.getTypeName();
                param[1] = begin;
                parameters.put(paramIndex, param);
            }
            if (end != null) {
                sql += " and c.date <= ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Date.class.getTypeName();
                param[1] = end;
                parameters.put(paramIndex, param);
            }
            if (feature != -1) {
                sql += " And c.featuredsubject =? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = feature;
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
                CoursePackage r = new CoursePackage();
                r.setCid(new CourseDAO().getCourseById1(rs.getInt("cid")));
                r.setPpid(new PricePackageDAO().getPricePackageById(rs.getInt("ppid")));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }


    public int countCourses(int[] ccid, int[] cid, String name, Date begin, Date end, int feature) {
        List<CoursePackage> list = new ArrayList<>();
        try {
            String sql = "SELECT COUNT(*) as total from\n"
                    + "   (Select  c.id as cid, pp.id as ppid from Course c inner join course_package cp \n"
                    + "   on c.id = cp.course_id join Price_Package pp\n"
                    + "   on cp.pricepackage_id = pp.id inner join Category_SubCategory_Course csc\n"
                    + "   on csc.id = c.category_id\n"
                    + "   where duration = (select MIN(duration) from Price_Package) \n"
                    + "   and (1=1)";

            if (ccid != null) {
                sql += "and csc.category_id in(";
                for (int i = 0; i < ccid.length; i++) {
                    sql += ccid[i] + ",";
                }
                if (sql.endsWith(",")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
                sql += ")";
            }
            if (cid != null) {
                sql += "and csc.subcategory_id in(";
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
                sql += " And c.name like ?  ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = String.class.getTypeName();
                param[1] = name;
                parameters.put(paramIndex, param);
            }
            if (begin != null) {
                sql += " and c.date >= ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Date.class.getTypeName();
                param[1] = begin;
                parameters.put(paramIndex, param);
            }
            if (end != null) {
                sql += " and c.date <= ? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Date.class.getTypeName();
                param[1] = end;
                parameters.put(paramIndex, param);
            }
            if (feature != -1) {
                sql += " And c.featuredsubject =? ";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getTypeName();
                param[1] = feature;
                parameters.put(paramIndex, param);
            }
            sql += " ) as T ";
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
        CoursePackageDAO d = new CoursePackageDAO();
        List<CoursePackage> list = new ArrayList<>();
        CoursePackage c = d.getCourseAndPackage(2);
        // list = d.getAllCourseWithPriceByName("ma");
//       String begin = "2020/02/02";
//        String end = "2022/12/12";
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//
//        Date date1 = (Date) df.parse(begin);
//        Date date2 = (Date) df.parse(end);
//        int[] a = null;
//        int[] b = null;
//        list = d.searchCourses(a, b, null, null, 0, 1, 6);
//        for (CoursePackage coursePackage : list) {
//            System.out.println(coursePackage);
//        }
//list = d.getAllCourseWithPrice("math",1, 6);
//        for (CoursePackage coursePackage : list) {
//            System.out.println(coursePackage.toString());
//        }
System.out.println(c.getCid().getThumbnail());
    }

}
