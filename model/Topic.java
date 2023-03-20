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
public class Topic {
    private int id;
    private Course course;
    private String topic_name;

    public Topic() {
    }

    public Topic(int id, Course course, String topic_name) {
        this.id = id;
        this.course = course;
        this.topic_name = topic_name;
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

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
    
}
