/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Tzh
 */

public class Cart {
    
    private int code;
    private User user;
    private ArrayList<Product> products;
    private float total;
    
    public Cart(int code, User user, ArrayList<Product> products) {
        this.code = code;
        this.user = user;
        this.products = products;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public float getTotal() {
        return total;
    }

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public Cart(User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public Cart(User user, ArrayList<Product> products, float total) {
        this.user = user;
        this.products = products;
        this.total = total;
    }

    public Cart(int code, User user, ArrayList<Product> products, float total) {
        this.code = code;
        this.user = user;
        this.products = products;
        this.total = total;
    }

}
