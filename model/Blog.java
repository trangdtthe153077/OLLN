/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.sql.Date;       
/**
 *
 * @author win
 */
public class Blog {
    private int id;
    private Category_Blog categoryblog;
    private User user;
    private String thumbnail;
    private String title;
    private String brief;
    private String description;
    private Boolean flag;
    private Boolean status;
    private Date date;

    public Blog() {
    }

    public Blog(int id, Category_Blog categoryblog, User user, String thumbnail, String title, String brief, String description, Boolean flag, Boolean status, Date date) {
        this.id = id;
        this.categoryblog = categoryblog;
        this.user = user;
        this.thumbnail = thumbnail;
        this.title = title;
        this.brief = brief;
        this.description = description;
        this.flag = flag;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category_Blog getCategoryblog() {
        return categoryblog;
    }

    public void setCategoryblog(Category_Blog categoryblog) {
        this.categoryblog = categoryblog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    
}
