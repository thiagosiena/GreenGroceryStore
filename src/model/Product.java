/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tzh
 */

public class Product {

    private int code;
    private String name;
    private float price;
    private int qnt;
    private float subtotal;

    public Product(int code, String name, float price, int qnt, float subtotal) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.qnt = qnt;
        this.subtotal = subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return name;
    }

    public float getSubtotal() {
        return qnt * price;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public void setPrice(float price) {
        this.price = price;
    }
 
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQnt() {
        return qnt;
    }

    public float getPrice() {
        return price;
    }

    public Product() {
    }

    public Product(int code) {
        this.code = code;
    }

    public Product(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Product(String name, float price, int qnt, float subtotal) {
        this.name = name;
        this.price = price;
        this.qnt = qnt;
        this.subtotal = subtotal;
    }

    public Product(int code, String name, int qnt) {
        this.code = code;
        this.name = name;
        this.qnt = qnt;
    }

    public Product(int code, String name, float price, int qnt) {
        this.code = code;
        this.name = name;
        this.qnt = qnt;
        this.price = price;

    }

}
