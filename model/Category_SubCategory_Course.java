/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Laptop88
 */
public class Category_SubCategory_Course {
    private int id;
    private CategoryCourse categorycourse;
    private SubCategoryCourse subcategorycourse;

    public Category_SubCategory_Course() {
    }

    public Category_SubCategory_Course(int id, CategoryCourse categorycourse, SubCategoryCourse subcategorycourse) {
        this.id = id;
        this.categorycourse = categorycourse;
        this.subcategorycourse = subcategorycourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryCourse getCategorycourse() {
        return categorycourse;
    }

    public void setCategorycourse(CategoryCourse categorycourse) {
        this.categorycourse = categorycourse;
    }

    public SubCategoryCourse getSubcategorycourse() {
        return subcategorycourse;
    }

    public void setSubcategorycourse(SubCategoryCourse subcategorycourse) {
        this.subcategorycourse = subcategorycourse;
    }
    
}
