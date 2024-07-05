/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tzh
 */

public class User {

    private String email;
    private String password;
    private String name;
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public User(int id , String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getId() + ":" + getEmail();
    }

}
