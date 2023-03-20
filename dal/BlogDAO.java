/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Blog;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;
import model.Category_Blog;
import model.User;

/**
 *
 * @author win
 */
public class BlogDAO extends BaseDAO<Blog> {

    public Blog getNewestPost() {
        try {
            String sql = "SELECT TOP 1 * FROM Blog_Detail where status = 1 ORDER BY  date DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Blog r = new Blog();
                r.setId(rs.getInt("id"));
                r.setCategoryblog(new CatrgoryblogDAO().getCategoryBlogById(rs.getInt("category_id")));
                r.setUser(new UserDAO().getUserById(rs.getInt("user_id")));
                r.setThumbnail(base64Image);
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));
                r.setFlag(rs.getBoolean("flag"));
                r.setStatus(rs.getBoolean("status"));
                r.setDate(rs.getDate("date"));
                return r;
            }
        } catch (SQLException e) {
        } catch (IOException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public List<Blog> getTop4Post() {
        List<Blog> list = new ArrayList<>();
        try {
            String sql = "SELECT TOP 4 * FROM Blog_Detail where status = 1 ORDER BY  date DESC ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Blog r = new Blog();
                r.setId(rs.getInt("id"));
                r.setCategoryblog(new CatrgoryblogDAO().getCategoryBlogById(rs.getInt("category_id")));
                r.setUser(new UserDAO().getUserById(rs.getInt("user_id")));
                r.setThumbnail(base64Image);
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));
                r.setFlag(rs.getBoolean("flag"));
                r.setStatus(rs.getBoolean("status"));
                r.setDate(rs.getDate("date"));
                list.add(r);
            }
        } catch (SQLException e) {
        } catch (IOException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public ArrayList<Blog> getActiveBlogs() throws IOException {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "select a.*, b.name as 'cate_name', c.username as 'author' from Blog_Detail as a\n"
                + "inner join Category_Blog as b on a.category_id = b.id\n"
                + "inner join [User] as c on a.user_id = c.id\n"
                + "where a.status = 1\n"
                + "order by a.date desc";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Blog r = new Blog();
                r.setId(rs.getInt("id"));
                r.setCategoryblog(new CatrgoryblogDAO().getCategoryBlogById(rs.getInt("category_id")));
                r.setUser(new UserDAO().getUserById(rs.getInt("user_id")));
                r.setThumbnail(base64Image);
                r.setTitle(rs.getString("title"));
                r.setBrief(rs.getString("brief"));
                r.setDescription(rs.getString("description"));
                r.setFlag(rs.getBoolean("flag"));
                r.setStatus(rs.getBoolean("status"));
                r.setDate(rs.getDate("date"));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Category_Blog> getCategories() {
        ArrayList<Category_Blog> categories = new ArrayList<>();
        try {
            String sql = "select * from Category_Blog";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category_Blog c = new Category_Blog();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                categories.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public ArrayList<Blog> getListByPage(ArrayList<Blog> list, int start, int end) {
        ArrayList<Blog> blogs = new ArrayList<>();
        for (int i = start; i < end; i++) {
            blogs.add(list.get(i));
        }
        return blogs;
    }

    public ArrayList<Blog> search(String content) throws IOException {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "select a.*, b.name as 'cate_name', c.username as 'author' from Blog_Detail as a\n"
                + "inner join Category_Blog as b on a.category_id = b.id\n"
                + "inner join [User] as c on a.user_id = c.id\n"
                + "where a.title like ? and a.status = 1\n"
                + "order by a.date desc";
        String keyword = "%" + content + "%";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, keyword);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }

                Blog b = new Blog();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                // ẢNh này cần xem lại 
                b.setThumbnail(base64Image);
                b.setDate(rs.getDate("date"));
                b.setStatus(rs.getBoolean("status"));
                b.setBrief(rs.getString("brief"));
                b.setDescription(rs.getString("description"));
                b.setCategoryblog(new CatrgoryblogDAO().getCategoryBlogById(rs.getInt("category_id")));
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Blog> getBlogsByCategory(int id) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "select a.*, b.name as 'cate_name', c.username as 'author' from Blog_Detail as a\n"
                + "inner join Category_Blog as b on a.category_id = b.id\n"
                + "inner join [User] as c on a.user_id = c.id\n"
                + "where b.id = ? and a.status = 1\n"
                + "order by a.date desc";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Blog b = new Blog();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                // ẢNh này cần xem lại 
                b.setThumbnail(base64Image);
                b.setDate(rs.getDate("date"));
                b.setBrief(rs.getString("brief"));
                b.setDescription(rs.getString("description"));
                b.setCategoryblog(new CatrgoryblogDAO().getCategoryBlogById(rs.getInt("category_id")));
                list.add(b);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Blog getBlogById(int id) {
        String sql = "select a.*, b.name as 'cate_name', c.username as 'author' \n"
                + "from Blog_Detail as a\n"
                + "inner join Category_Blog as b on a.category_id = b.id\n"
                + "inner join [User] as c on a.user_id = c.id\n"
                + "where a.id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Blog b = new Blog();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setThumbnail(base64Image);
                b.setDate(rs.getDate("date"));
                b.setFlag(rs.getBoolean("flag"));
                b.setStatus(rs.getBoolean("status"));
                b.setBrief(rs.getString("brief"));
                b.setDescription(rs.getString("description"));
                Category_Blog cb = new Category_Blog(rs.getInt("category_id"), rs.getString("cate_name"));
                b.setCategoryblog(cb);
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setUsername(rs.getString("author"));
                b.setUser(u);
                return b;
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void HideBlog(int blog_id, int status) {
        try {
            String sql = "update [Blog_Detail] set status = ? where id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(1, blog_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void AddBlog(int cid, String title, Part img, String brief,
            String describe, boolean featured, int author_id, boolean status) {
        try {
            String sql = "INSERT INTO [Blog_Detail]\n"
                    + "           ([category_id]\n"
                    + "           ,[user_id]\n"
                    + "           ,[thumbnail]\n"
                    + "           ,[title]\n"
                    + "           ,[brief]\n"
                    + "           ,[description]\n"
                    + "           ,[flag]\n"
                    + "           ,[status]\n"
                    + "           ,[date])\n"
                    + "		   VALUES (?,?,?,?,?,?,?,?,GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            stm.setInt(2, author_id);
            InputStream image = img.getInputStream();
            stm.setBlob(3, image);
            stm.setString(4, title);
            stm.setString(5, brief);
            stm.setString(6, describe);
            stm.setBoolean(7, featured);
            stm.setBoolean(8, status);
            stm.executeUpdate();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UpdateBlog(int cid, String title, String brief, String describe, boolean featured, boolean status, int blog_id) {
        try {
            String sql = "UPDATE [Blog_Detail]\n"
                    + "   SET [category_id] = ?\n"
                    + "      ,[title] = ?\n"
                    + "      ,[brief] = ?\n"
                    + "      ,[description] = ?\n"
                    + "      ,[flag] = ?\n"
                    + "      ,[status] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            stm.setString(2, title);
            stm.setString(3, brief);
            stm.setString(4, describe);
            stm.setBoolean(5, featured);
            stm.setBoolean(6, status);
            stm.setInt(7, blog_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void UpdateImage(int blog_id, Part img) {
        try {
            String sql = "update [Blog_Detail] set [thumbnail] = ? where id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            InputStream image = img.getInputStream();
            stm.setBlob(1, image);
            stm.setInt(2, blog_id);
            stm.executeUpdate();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Blog> getBlogs(int pageindex, int pagesize) throws IOException {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "select * from \n"
                + "(select a.*, b.name as 'cate_name', c.fullname as 'author',\n"
                + "ROW_NUMBER() over (order by a.id desc) as row_index from Blog_Detail as a\n"
                + "inner join Category_Blog as b on a.category_id = b.id\n"
                + "inner join [User] as c on a.user_id = c.id) tbl\n"
                + "where row_index >= (? -1) * ? + 1\n"
                + "and row_index <= ? * ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageindex);
            stm.setInt(2, pagesize);
            stm.setInt(3, pageindex);
            stm.setInt(4, pagesize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob("thumbnail");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Blog r = new Blog();
                r.setId(rs.getInt("id"));
                r.setCategoryblog(new CatrgoryblogDAO().getCategoryBlogById(rs.getInt("category_id")));
                r.setUser(new UserDAO().getUserById(rs.getInt("user_id")));
                r.setThumbnail(base64Image);
                r.setTitle(rs.getString("title"));
                r.setBrief(rs.getString("brief"));
                r.setDescription(rs.getString("description"));
                r.setFlag(rs.getBoolean("flag"));
                r.setStatus(rs.getBoolean("status"));
                r.setDate(rs.getDate("date"));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int count() {
        try {
            String sql = "select COUNT(*) as total from Blog_Detail as a\n"
                    + "inner join Category_Blog as b on a.category_id = b.id\n"
                    + "inner join [User] as c on a.user_id = c.id";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
