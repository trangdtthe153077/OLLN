/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author win
 */
public class Quizzes {
    private int id;
    private Course course;
    private QLevel level;
    private String name;
    private int numberQuestion;
    private int duration;
    private int passrate;
    private String status;
    private String description;

    public Quizzes() {
    }

    public Quizzes(int id, Course course, QLevel level, String name, int numberQuestion, int duration, int passrate, String status, String description) {
        this.id = id;
        this.course = course;
        this.level = level;
        this.name = name;
        this.numberQuestion = numberQuestion;
        this.duration = duration;
        this.passrate = passrate;
        this.status = status;
        this.description = description;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(int numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPassrate() {
        return passrate;
    }

    public void setPassrate(int passrate) {
        this.passrate = passrate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Quizzes{" + "id=" + id + ", course=" + course + ", level=" + level + ", name=" + name + ", numberQuestion=" + numberQuestion + ", duration=" + duration + ", passrate=" + passrate + ", status=" + status + ", description=" + description + '}';
    }
    
     
}
