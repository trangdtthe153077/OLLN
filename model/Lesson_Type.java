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
public class Lesson_Type {
    private int id;
    private String type_name;

    public Lesson_Type() {
    }

    public Lesson_Type(int id, String type_name) {
        this.id = id;
        this.type_name = type_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
    
    
}
