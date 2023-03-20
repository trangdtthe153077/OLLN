/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author DELL
 */
public class AnswerOption {
    private int id;
    private Question question;
    private String option;
    private boolean isTrue;

    public AnswerOption() {
    }

    public AnswerOption(int id, Question question, String option, boolean isTrue) {
        this.id = id;
        this.question = question;
        this.option = option;
        this.isTrue = isTrue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    

    public AnswerOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isIsTrue() {
        return isTrue;
    }

    public void setIsTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }

    @Override
    public String toString() {
        return "AnswerOption: " + "option: " + option + " ---- isTrue: " + isTrue + '\n';
    }
}
