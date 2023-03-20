/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Question {

    private int id;
    private Course course;
    private Dimension dimension;
    private QLevel level;
    private boolean status;
    private String content;
    private String media;
    private String explain;
    private ArrayList<AnswerOption> answers = new ArrayList<>();

    public Question() {
    }

    public Question(int id, Course course, Dimension dimension, QLevel level, boolean status, String content, String media, String explain) {
        this.id = id;
        this.course = course;
        this.dimension = dimension;
        this.level = level;
        this.status = status;
        this.content = content;
        this.media = media;
        this.explain = explain;
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

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public QLevel getLevel() {
        return level;
    }

    public void setLevel(QLevel level) {
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public ArrayList<AnswerOption> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerOption> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question: " + "\nlevel: " + level + "\nContent: " + content + "\nAnswers: " + answers + '}';
    }

}
