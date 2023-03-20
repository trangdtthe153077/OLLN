/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class UserQuiz {

    private int id;
    private float mark;
    private Timestamp starttime; // endtime?
    private Quiz quiz;
    private User user;
    private long time;
    // them truong status ko? dang lam bai? hay xu ly cai nay banwg session doingQuiz, khi dang lam bai ma thoat ra
    private ArrayList<AnswerOption> ops;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ArrayList<AnswerOption> getOps() {
        return ops;
    }

    public void setOps(ArrayList<AnswerOption> ops) {
        this.ops = ops;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
