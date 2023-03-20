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
public class Quiz {
    private int id;
    private String name;
    private Course course;
    private QLevel level;
    private int number_question;
    private int duration;
    private double pass_rate;
    private String type;
    private String description;

    public Quiz() {
    }

    public Quiz(int id, String name, Course course, QLevel level, int number_question, int duration, double pass_rate, String type, String description) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.level = level;
        this.number_question = number_question;
        this.duration = duration;
        this.pass_rate = pass_rate;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public QLevel getQLevel() {
        return level;
    }

    public void setQLevel(QLevel level) {
        this.level = level;
    }

    public int getNumber_question() {
        return number_question;
    }

    public void setNumber_question(int number_question) {
        this.number_question = number_question;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPass_rate() {
        return pass_rate;
    }

    public void setPass_rate(double pass_rate) {
        this.pass_rate = pass_rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
