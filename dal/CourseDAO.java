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
import model.CategoryCourse;
import model.Course;
import model.CourseStatus;

/**
 *
 * @author win
 */
public class CourseDAO extends BaseDAO<Course> {
    public Course getCourseById(int id) {
        try {
            String sql = "SELECT *FROM Course where id =" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
//                r.setDimension(new DimensionDAO().getCourseDimensionById(rs.getInt(1)));
                r.setDimension(new DimensionDAO().getCourseDimensionByPage(rs.getInt(1), 1, 10, ""));
                r.setPricepackages(new PricePackageDAO().getAllPricePackagebyPage(rs.getInt(1), 1, 10, ""));
                return r;
            }

        } catch (SQLException e) {
        }
        return null;

    }

    public List<Course> getAllCourse() {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM Course";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;

    }
    public int getTotalCourse() {
        int total = 0;
        try {
            String sql = "select COUNT(id) as Total from Course";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getTotalResByDate(String from, String to, int a) {
        int total = 0;
        try {
            String sql = "select *from\n"
                    + "(select ROW_NUMBER() OVER (ORDER BY registered_date desc) as row_num, t, registered_date from\n"
                    + "(select count(user_id) as t, registered_date from Registrations r where registered_date >=? and registered_date <=?\n"
                    + "group by registered_date) as too) as tooo where row_num = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, from);
            statement.setString(2, to);
            statement.setInt(3, a);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(2);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getSuccessResByDate(String from, String to, int a) {
        int total = 0;
        try {
            String sql = "select *from\n"
                    + "(select ROW_NUMBER() OVER (ORDER BY registered_date desc) as row_num, t, registered_date from\n"
                    + "(select count(user_id) as t, registered_date from Registrations r where registered_date >=? and registered_date <=?\n"
                    + "and status ='success'\n"
                    + "group by registered_date) as too) as tooo where row_num = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, from);
            statement.setString(2, to);
            statement.setInt(3, a);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(2);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getCountResByDate(String from, String to) {
        int total = 0;
        try {
            String sql = "select count(user_id) as t from Registrations r where \n"
                    + "registered_date >=? and registered_date <=?";
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
    public int getCountResByDateAndStatus(String from, String to, String status) {
        int total = 0;
        try {
            String sql = "select count(user_id) as t from Registrations r where \n"
                    + "registered_date >=? and registered_date <=? and status = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, from);
            statement.setString(2, to);
            statement.setString(3, status);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }
    public int getTotalCourseByDate(String from, String to) {
        int total = 0;
        try {
            String sql = "select COUNT(id) as Total from Course where date >= ? and date <= ?";
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
    public Course getCourseById1(int id) {
        try {
            String sql = "SELECT *FROM Course where id =" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
//                r.setDimension(new DimensionDAO().getCourseDimensionById(rs.getInt(1)));
                r.setDimension(new DimensionDAO().getCourseDimensionByPage(rs.getInt(1), 1, 10, ""));
                r.setPricepackages(new PricePackageDAO().getAllPricePackagebyPage(rs.getInt(1), 1, 10, ""));
                return r;
            }

        } catch (SQLException e) {
        }
        return null;

    }
    public Course getCourseById2(int id) {
        try {
            String sql = "SELECT *FROM Course where id =" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
                return r;
            }

        } catch (SQLException e) {
        }
        return null;

    }
    public Course getNameCourseById(int id) {
        try {
            String sql = "SELECT Course.id, Course.name FROM Course where id =" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                return r;
            }

        } catch (SQLException e) {
        }
        return null;

    }

    public Course getLessonCourseById(int course_id) {
        try {
            String sql = "Select COUNT(Lesson.id) as numberoflessons, Course.id as course_id from \n"
                    + "Course inner join Topic\n"
                    + "on Course.id=Topic.course_id\n"
                    + "inner join Lesson\n"
                    + "on Topic.id=Lesson.topic_id\n"
                    + "where course_id = ?\n"
                    + "group by Course.id";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, course_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setNumberoflessons(rs.getInt(1));
                r.setId(rs.getInt(2));
                return r;
            }

        } catch (SQLException e) {
        }
        return null;

    }

    public List<Course> get6Course() {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "SELECT TOP 6 *FROM Course";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }

    public List<Course> getAllCourseByName(String txt) {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Course WHERE [name] LIKE '%" + txt + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;

    }

    public List<Course> getTop3Course() {
        List<Course> list = new ArrayList<>();
        try {
            String sql = "   SELECT c.id, c.category_id, c.status_id, c.owner, c.name, c.thumbnail,\n"
                    + "               c.tagline, c.title, c.date,\n"
                    + "                    c.description, c.featuredsubject FROM (SELECT Top 3 course_id, COUNT(course_id) AS [Regis] FROM Registrations\n"
                    + "                    GROUP BY course_id\n"
                    + "                ORDER BY Regis DESC)t INNER JOIN Course c\n"
                    + "                   ON t.course_id = c.id";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }

    public Course getNewestCourse() {
        try {
            String sql = " SELECT TOP 1 * FROM Course ORDER BY  date DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Course r = new Course();
                r.setId(rs.getInt(1));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt(2)));
                r.setCoursestatus(new CoursestatusDAO().getCourseStatusById(rs.getInt(3)));
                r.setOwner(new UserDAO().getUserById(rs.getInt(4)));
                r.setName(rs.getString(5));
                r.setThumbnail(rs.getString(6));
                r.setTagline(rs.getString(7));
                r.setTitle(rs.getString(8));
                r.setDate(rs.getDate(9));
                r.setDescription(rs.getString(10));
                r.setFeaturedsubject(rs.getBoolean(11));
                return r;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public List<CategoryCourse> getAllCategoryCourse() {
        List<CategoryCourse> list = new ArrayList<>();
        try {
            String sql = "SELECT *FROM Category_Course";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CategoryCourse r = new CategoryCourse();
                r.setId(rs.getInt(1));
                r.setName(rs.getString(2));
                r.setImage(rs.getString(3));
                list.add(r);
            }
        } catch (SQLException e) {
        }
        return list;

    }

    public ArrayList<Course> getCourses(int pageindex, int pagesize, int owner) {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = "Select * from\n"
                    + "             (Select ROW_NUMBER() OVER (ORDER BY id ASC) as [Row],id,name,Categoryid,numberoflessons,fullname,status_name from \n"
                    + "             (Select COUNT(Lesson.id) as numberoflessons, Course.id,Course.name,Category_SubCategory_Course.id as Categoryid,status_name,[owner],[USER].fullname from \n"
                    + "             Course inner join Category_SubCategory_Course\n"
                    + "             on Course.category_id=Category_SubCategory_Course.id\n"
                    + "             inner join Course_Status\n"
                    + "             on Course.status_id=Course_Status.id\n"
                    + "             inner join [User]\n"
                    + "			on Course.[owner]=[User].id\n"
                    + "			left join Topic\n"
                    + "on Course.id=Topic.course_id\n"
                    + "left join Lesson\n"
                    + "on Topic.id=Lesson.id\n";
            if (owner != -1) {
                sql += "where [owner] =" + owner + "\n";
            }
            sql += "group by Course.id,Course.name,Category_SubCategory_Course.id,status_name,[owner],[USER].fullname\n"
                    + "             ) as a\n"
                    + "             ) as T\n"
                    + "             where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pageindex);
            statement.setInt(2, pagesize);
            statement.setInt(3, pageindex);
            statement.setInt(4, pagesize);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Course r = new Course();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt("Categoryid")));
                r.setNumberoflessons(rs.getInt("numberoflessons"));
                r.setOwner_fullname(rs.getString("fullname"));
                r.setStatus_name(rs.getString("status_name"));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;

    }

    public ArrayList<Course> searchCourses(int pageindex, int pagesize, String name, String category, String subcategory, String status, int owner) {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = "Select * from\n"
                    + "             (Select ROW_NUMBER() OVER (ORDER BY id ASC) as [Row],id,name,Categoryid,numberoflessons,fullname,status_name from \n"
                    + "             (Select COUNT(Lesson.id) as numberoflessons, Course.id,Course.name, Category_SubCategory_Course.category_id as cid, Category_SubCategory_Course.subcategory_id as scid, Category_SubCategory_Course.id as Categoryid,status_name,[owner],[USER].fullname from \n"
                    + "             Course inner join Category_SubCategory_Course\n"
                    + "             on Course.category_id=Category_SubCategory_Course.id\n"
                    + "             inner join Course_Status\n"
                    + "             on Course.status_id=Course_Status.id\n"
                    + "             inner join [User]\n"
                    + "			on Course.[owner]=[User].id\n"
                    + "			left join Topic\n"
                    + "on Course.id=Topic.course_id\n"
                    + "left join Lesson\n"
                    + "on Topic.id=Lesson.id\n";
            if (owner != -1) {
                sql += "where [owner] =" + owner + "\n";
            }
            sql += "group by Course.id,Course.name,Category_SubCategory_Course.id,status_name,[owner],[USER].fullname, Category_SubCategory_Course.category_id, Category_SubCategory_Course.subcategory_id\n"
                    + "             ) as a\n"
                    + "             where (1=1)\n";
            if (name != null && !name.equals("")) {
                sql += " and name like '%" + name + "%'";
            }
            if (category != null && !category.equals("")) {
                sql += " and cid='" + category + "'";
            }
            if (subcategory != null && !subcategory.equals("")) {
                sql += " and scid='" + subcategory + "'";
            }
            if (status != null && !status.equals("")) {
                sql += " and status_name='" + status + "'";
            }
            sql += ") as T\n"
                    + "             where [Row]>=(?-1)*?+1 and [Row]<=?*?";
            System.out.println(sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pageindex);
            statement.setInt(2, pagesize);
            statement.setInt(3, pageindex);
            statement.setInt(4, pagesize);
//            statement.setInt(5, pagesize);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Course r = new Course();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCategorycourse(new Category_SubCategory_CourseDAO().getCategory_SubCategoryCourseById(rs.getInt("Categoryid")));
                r.setNumberoflessons(rs.getInt("numberoflessons"));
                r.setOwner_fullname(rs.getString("fullname"));
                r.setStatus_name(rs.getString("status_name"));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;

    }

    public int searchTotal(int pageindex, int pagesize, String name, String category, String subcategory, String status, int owner) {
        int total = 0;
        try {
            String sql = "Select COUNT(id) from\n"
                    + "             \n"
                    + "             (Select COUNT(Lesson.id) as numberoflessons, Course.id,Course.name, Category_SubCategory_Course.category_id as cid, Category_SubCategory_Course.subcategory_id as scid, Category_SubCategory_Course.id as Categoryid,status_name,[owner],[USER].fullname from \n"
                    + "             Course inner join Category_SubCategory_Course\n"
                    + "             on Course.category_id=Category_SubCategory_Course.id\n"
                    + "             inner join Course_Status\n"
                    + "             on Course.status_id=Course_Status.id\n"
                    + "             inner join [User]\n"
                    + "			on Course.[owner]=[User].id\n"
                    + "			left join Topic\n"
                    + "on Course.id=Topic.course_id\n"
                    + "left join Lesson\n"
                    + "on Topic.id=Lesson.id\n";
            if (owner != -1) {
                sql += "where [owner] =" + owner + "\n";
            }
            sql += "group by Course.id,Course.name,Category_SubCategory_Course.id,status_name,[owner],[USER].fullname, Category_SubCategory_Course.category_id, Category_SubCategory_Course.subcategory_id\n"
                    + "             ) as a\n"
                    + "             where (1=1)\n";
            if (name != null && !name.equals("")) {
                sql += " and name like '%" + name + "%'";
            }
            if (category != null && !category.equals("")) {
                sql += " and cid='" + category + "'";
            }
            if (subcategory != null && !subcategory.equals("")) {
                sql += " and scid='" + subcategory + "'";
            }
            if (status != null && !status.equals("")) {
                sql += " and status_name='" + status + "'";
            }
            sql += ") as T\n"
                    + "             where [Row]>=(?-1)*?+1 and [Row]<=?*?";

            System.out.println(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {
        }
        return total;

    }

    public int getTotal(int owner) {
        int total = 0;
        try {
            String sql = "select COUNT(id) from Course";
            if (owner != -1) {
                sql += " where [owner] =" + owner + "\n";
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

    public ArrayList<CourseStatus> getCourseStatus() {
        ArrayList<CourseStatus> list = new ArrayList<>();
        try {
            String sql = "SELECT * from [Course_Status]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CourseStatus r = new CourseStatus();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("status_name"));
                list.add(r);
            }

        } catch (SQLException e) {
        }
        return list;

    }

    public void AddSubject(Course c) {
        try {
            String sql = "INSERT INTO [Course]\n"
                    + "           ([category_id]\n"
                    + "           ,[status_id]\n"
                    + "           ,[owner]\n"
                    + "           ,[name]\n"
                    + "           ,[thumbnail]\n"
                    + "           ,[tagline]\n"
                    + "           ,[title]\n"
                    + "           ,[date]\n"
                    + "           ,[description]\n"
                    + "           ,[featuredsubject])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,GETDATE()\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, c.getCategorycourse().getId());
            statement.setInt(2, c.getCoursestatus().getId());
            statement.setInt(3, c.getOwner().getId());
            statement.setString(4, c.getName());
            statement.setString(5, c.getThumbnail());
            statement.setString(6, c.getTagline());
            statement.setString(7, c.getTitle());
            statement.setString(8, c.getDescription());
            statement.setBoolean(9, c.isFeaturedsubject());
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public ArrayList<Course> getMyCourses(int id) {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = "Select * from Registrations inner join Course on course_id=Course.id\n"
                    + "                     where [user_id]=? and (valid_to>= GETDATE() or valid_to is null) and valid_from is not null";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            Category_SubCategory_CourseDAO catdao = new Category_SubCategory_CourseDAO();
            while (rs.next()) {

                Course r = new Course();
                r.setId(rs.getInt("id"));

                r.setName(rs.getString("name"));
                r.setThumbnail(rs.getString("thumbnail"));
                r.setDescription(rs.getString("description"));
                r.setTagline(rs.getString("tagline"));
                r.setTitle(rs.getString("title"));
                r.setValidfrom(rs.getString("valid_from"));
                r.setValidto(rs.getString("valid_to"));

//                r.setCategorycourse((rs.getInt("category_id")));
                list.add(r);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;

    }

    public void CourseRegister(int userid, String courseid, String pricepkg) {
        try {
            String sql = "INSERT INTO [Registrations]\n"
                    + "           ([user_id]\n"
                    + "           ,[course_id]\n"
                    + "           ,[registered_date]\n"
                    + "           ,[price_id])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,GETDATE()\n"
                    + "           ,? )";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userid);
            statement.setString(2, courseid);
            statement.setString(3, pricepkg);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                String sql = "UPDATE [SWP391-Project ].[dbo].[Registrations]\n"
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

    public Course getSingleCourse(String id) {
        Course r = new Course();
        try {
            String sql = "SELECT * from [Course] where id=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                r = new Course();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setThumbnail(rs.getString("thumbnail"));
                r.setDescription(rs.getString("description"));
                r.setTagline(rs.getString("tagline"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return r;

    }

    public ArrayList<Course> searchMyCourses(String text, String id) {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = "Select * from Registrations inner join Course\n"
                    + "                      on course_id=Course.id\n"
                    + "              where [user_id]=? and name like '%' + ? +'%'";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, text);
            ResultSet rs = statement.executeQuery();
            System.out.println("2");
            while (rs.next()) {

                Course r = new Course();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setThumbnail(rs.getString("thumbnail"));
                r.setDescription(rs.getString("description"));
//                r.setCategory_name(rs.getString("Categoryname"));
//                r.setNumberoflessons(rs.getInt("numberoflessons"));
//                r.setOwner_fullname(rs.getString("fullname"));
//                r.setStatus_name(rs.getString("status_name"));
                list.add(r);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;

    }

    public void UpdateCourse(String id, String coursename, String title, String tagline, String gradeselect, String subjectselect, String featuredsubject, String statusselect, String describe, String thumbnail) {
        try {
            String sql0 = "UPDATE [Course]\n"
                    + "   SET \n";
            if (statusselect != null && !statusselect.equals("")) {
                sql0 += "      [status_id] = " + statusselect + " , \n";
            }

            sql0 += "      [name] = ?\n"
                    + "      ,[tagline] = ?\n"
                    + "      ,[title] = ?\n"
                    + "      ,[description] = ?\n"
                    + "      ,[featuredsubject] = ?\n"
                    + " WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql0);
//            statement.setString(1, statusselect);
            statement.setString(1, coursename);
            statement.setString(2, tagline);
            statement.setString(3, title);
            statement.setString(4, describe);
            statement.setString(5, featuredsubject);
            statement.setString(6, id);

            ResultSet rs = statement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        int catid = getCourseById1(Integer.parseInt(id)).getCategorycourse().getId();
        try {
            String sql1 = "UPDATE [Category_SubCategory_Course]\n"
                    + "   SET [category_id] = ?\n"
                    + "      ,[subcategory_id] = ?\n"
                    + " WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql1);
            statement.setString(1, gradeselect);
            statement.setString(2, subjectselect);
            statement.setInt(3, catid);

            ResultSet rs = statement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!thumbnail.equals("")) {
            try {
                String sql2 = "UPDATE [Course]\n"
                        + "   SET \n"
                        + "    \n"
                        + "     [thumbnail] = ?\n"
                        + " WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql2);
                statement.setString(1, thumbnail);
                statement.setString(2, id);

                ResultSet rs = statement.executeQuery();

            } catch (SQLException ex) {
                Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public ArrayList<Course> getCourseName() {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = "select a.id, a.name + ' by ' + d.fullname as 'name' from Course as a \n" +
                    "inner join [Category_SubCategory_Course] as b on a.category_id = b.id\n" +
                    "inner join [Category_course] as c on c.id = b.category_id\n" +
                    "inner join [User] as d on a.owner = d.id";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public static void main(String[] args) {
        CourseDAO c = new CourseDAO();
        List<Course> list = c.searchCourses(1, 10, "", "2", "", "", 1);
        for (Course course : list) {
            System.out.println(course.getName());
        }
//List<CategoryCourse> list = c.getAllCategoryCourse();
//        for (CategoryCourse categoryCourse : list) {
//            System.out.println(categoryCourse);
//        }
//        Course a = c.getNewestCourse();
//        System.out.println(a);
    }
}
