/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class Lesson {

    private int id;
    private Topic topic;
    private String name;
    private Lesson_Type type;
    private int order;
    private String video_link;
    private String content;
    private int status;

    public Lesson() {
    }

    public Lesson(int id, Topic topic, String name, Lesson_Type type, int order, String video_link, String content, int status) {
        this.id = id;
        this.topic = topic;
        this.name = name;
        this.type = type;
        this.order = order;
        this.video_link = video_link;
        this.content = content;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lesson_Type getType() {
        return type;
    }

    public void setType(Lesson_Type type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
