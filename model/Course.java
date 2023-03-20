/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author win
 */
public class Course {

    private int id;
    private Category_SubCategory_Course categorycourse;
    private CourseStatus coursestatus;
    private String name;
    private String thumbnail;
    private String tagline;
    private String title;
    private Date date;
    private User owner;
    private String description;
    private boolean featuredsubject;
    private ArrayList<Dimension> dimension;
    private ArrayList<Price_Package> pricepackages;
    private String validfrom;
    private String validto;

    public String getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(String validfrom) {
        this.validfrom = validfrom;
    }

    public String getValidto() {
        return validto;
    }

    public void setValidto(String validto) {
        this.validto = validto;
    }
    

    public Course(Category_SubCategory_Course categorycourse, CourseStatus coursestatus, String name, String thumbnail, String tagline, String title, User owner, String description, boolean featuredsubject) {
        this.categorycourse = categorycourse;
        this.coursestatus = coursestatus;
        this.name = name;
        this.thumbnail = thumbnail;
        this.tagline = tagline;
        this.title = title;
        this.owner = owner;
        this.description = description;
        this.featuredsubject = featuredsubject;
    }

    public Course(int id) {
        this.id = id;
    }

    public ArrayList<Dimension> getDimension() {
        return dimension;
    }

    public void setDimension(ArrayList<Dimension> dimension) {
        this.dimension = dimension;
    }

    public ArrayList<Price_Package> getPricepackages() {
        return pricepackages;
    }

    public void setPricepackages(ArrayList<Price_Package> pricepackages) {
        this.pricepackages = pricepackages;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getNumberoflessons() {
        return numberoflessons;
    }

    public void setNumberoflessons(int numberoflessons) {
        this.numberoflessons = numberoflessons;
    }

    public String getOwner_fullname() {
        return owner_fullname;
    }

    public void setOwner_fullname(String owner_fullname) {
        this.owner_fullname = owner_fullname;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    private String category_name;
    private int numberoflessons;
    private String owner_fullname;
    private String status_name;

    public Course() {
    }

    public Course(int id, Category_SubCategory_Course categorycourse, CourseStatus coursestatus, String name, String thumbnail, String tagline, String title, Date date, User owner, String description, boolean featuredsubject) {
        this.id = id;
        this.categorycourse = categorycourse;
        this.coursestatus = coursestatus;
        this.name = name;
        this.thumbnail = thumbnail;
        this.tagline = tagline;
        this.title = title;
        this.date = date;
        this.owner = owner;
        this.description = description;
        this.featuredsubject = featuredsubject;
    }

    public Course(Category_SubCategory_Course categorycourse, CourseStatus coursestatus, String name, String thumbnail, String tagline, String title, Date date, User owner, String description, boolean featuredsubject) {
        this.categorycourse = categorycourse;
        this.coursestatus = coursestatus;
        this.name = name;
        this.thumbnail = thumbnail;
        this.tagline = tagline;
        this.title = title;
        this.date = date;
        this.owner = owner;
        this.description = description;
        this.featuredsubject = featuredsubject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category_SubCategory_Course getCategorycourse() {
        return categorycourse;
    }

    public void setCategorycourse(Category_SubCategory_Course categorycourse) {
        this.categorycourse = categorycourse;
    }

    public CourseStatus getCoursestatus() {
        return coursestatus;
    }

    public void setCoursestatus(CourseStatus coursestatus) {
        this.coursestatus = coursestatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeaturedsubject() {
        return featuredsubject;
    }

    public void setFeaturedsubject(boolean featuredsubject) {
        this.featuredsubject = featuredsubject;
    }
}
