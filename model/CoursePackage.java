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
public class CoursePackage {
    private Course cid;
    private Price_Package ppid;

    public CoursePackage() {
    }

    public CoursePackage(Course cid, Price_Package ppid) {
        this.cid = cid;
        this.ppid = ppid;
    }
    
    public CoursePackage(Course cid) {
        this.cid = cid;
    }
    public Course getCid() {
        return cid;
    }

    public void setCid(Course cid) {
        this.cid = cid;
    }

    public Price_Package getPpid() {
        return ppid;
    }

    public void setPpid(Price_Package ppid) {
        this.ppid = ppid;
    }

    @Override
    public String toString() {
        return "CoursePackage{" + "cid=" + cid + ", ppid=" + ppid + '}';
    }
    
}
