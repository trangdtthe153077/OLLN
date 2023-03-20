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
public class User {
    private int id;
    private Role role;
    private String username;
    private String password;
    private String fullname;
    private boolean gender;
    private String email;
    private String phone;
    private String avatar;
    private int active;
    private String hash;
    private String captcha;
    private int lifetime;
    private String address;

    public User() {
    }

    public User(Role role, String username, String password, String fullname, boolean gender, String email, String phone, String avatar, int active, String hash) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.active = active;
        this.hash = hash;
    }

    public User(int id, Role role, String username, String password, String fullname, boolean gender, String email, String phone, String avatar) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }

    public User(int id, Role role, String username, String password, String fullname, boolean gender, String email, String phone, String avatar, int active, String hash) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.active = active;
        this.hash = hash;
    }

    
    
    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public User(Role role, String username, String password, String fullname, boolean gender, String email, String phone, String avatar) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

   
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    
    
}
