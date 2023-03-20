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
public class Price_Package {
    private int id;
    private String status;
    private String name;
    private int duration;
    private int listprice;
    private int saleprice;
    private String description;

    public Price_Package() {
    }

    public Price_Package(int id, String status, String name, int duration, int listprice, int saleprice, String description) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.duration = duration;
        this.listprice = listprice;
        this.saleprice = saleprice;
        this.description = description;
    }

    public Price_Package(int salepriceint, int listprice,int  duration) {
        this.saleprice = saleprice;
        this.listprice = listprice;
         this.duration = duration;
    }

    public Price_Package(int listprice) {
        this.listprice = listprice;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getListprice() {
        return listprice;
    }

    public void setListprice(int listprice) {
        this.listprice = listprice;
    }

    public int getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(int saleprice) {
        this.saleprice = saleprice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
