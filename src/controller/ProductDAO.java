/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Product;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tzh
 */

public class ProductDAO {

    private Connection con;
    private PreparedStatement cmd;

    public ProductDAO() {
        this.con = Conexao.connect();
    }

    public Product getProductByCode(int code) {

        String SQL = "select * from tb_product where id= ?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, code);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("qnt"));
                return product;
            }

        } catch (SQLException e) {
            System.err.println("ERRO2: " + e.getMessage());
        }

        return null;

    }

    public boolean isNameAlreadyRegistered(String name) {
        String SQL = "select * from tb_product where name=?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setString(1, name);
            ResultSet rs = cmd.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

    public boolean isCodeAlreadyRegistered(int code) {
        String SQL = "select * from tb_product where id=?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, code);
            ResultSet rs = cmd.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

    public int addQnt(Product p, int qnt) {
        String SQL = "update tb_product set qnt = qnt + ? where name = ?";
        if (qnt > 0) {
            try {
                cmd = con.prepareStatement(SQL);
                cmd.setInt(1, qnt);
                cmd.setString(2, p.getName());

                int rowsAffected = cmd.executeUpdate();
                if (rowsAffected > 0) {
                    return 0;
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                System.err.println("ERRO: " + e.getMessage());
                return -1;
            }

        }
        System.err.println("ERRO: negative number or 0 is not suported");
        return -1;

    }

    public int subQnt(Product p, int qnt) {

        if (qnt > 0) {
            try {

                String checkSQL = "select qnt from tb_product where id = ?";
                cmd = con.prepareStatement(checkSQL);
                cmd.setInt(1, p.getCode());
                ResultSet rs = cmd.executeQuery();

                if (rs.next()) {
                    int currentQnt = rs.getInt("qnt");
                    if (currentQnt >= qnt) {

                        String updateSQL = "update tb_product SET qnt = qnt - ? where name = ?";
                        cmd = con.prepareStatement(updateSQL);
                        cmd.setInt(1, qnt);
                        cmd.setString(2, p.getName());

                        int rowsAffected = cmd.executeUpdate();
                        if (rowsAffected > 0) {
                            return 0;
                        }
                        else {
                            return -1;
                        }
                    }
                    else {
                        return -2;
                    }
                }
                else {
                    return -3;
                }
            } catch (SQLException e) {
                System.err.println("ERRO: " + e.getMessage());
                return -1;
            }

        } else {
            return -1;
        }

    }

    public int updatePrice(Product p, float price) {
        if (price < 0) {
            System.err.println("the price cant be negative");
            return -1;
        }

        try {

            String updateProductSQL = "update tb_product set price = ? where id = ?";
            cmd = con.prepareStatement(updateProductSQL);
            cmd.setFloat(1, price);
            cmd.setInt(2, p.getCode());
            int productRowsAffected = cmd.executeUpdate();

            if (productRowsAffected > 0) {

                String updateCartProductSQL = "update tb_cart_product set unit_price = ?, subtotal = quantity * ? where product_id = ?";
                cmd = con.prepareStatement(updateCartProductSQL);
                cmd.setFloat(1, price);
                cmd.setFloat(2, price);
                cmd.setInt(3, p.getCode());
                int cartProductRowsAffected = cmd.executeUpdate();

                if (cartProductRowsAffected > 0) {
                    new CartDAO().updateAllCartTotals();
                    return 0;
                } else {
   
                    return -2;
                    
                    
                }
            } else {
        
                return -1;
            }
        } catch (SQLException e) {
            System.err.println("ERRO: " + e.getMessage());
            return -1;
        }
    }

    public int remove(Product p) {

        try {

            String SQL = "delete from tb_product where id = ?";
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, p.getCode());

            if (cmd.executeUpdate() > 0) {
                ResultSet rs = cmd.getGeneratedKeys();
                new CartDAO().updateAllCartTotals();
                return rs.next() ? rs.getInt(1) : -1;
            }
            return -1;

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            Conexao.desconnect(con);
        }

    }

    public int insert(Product u) {
        try {
            if (isNameAlreadyRegistered(u.getName()) || isCodeAlreadyRegistered(u.getCode())) {
                return -1;
            }
            if(u.getPrice() < 0 || u.getQnt() < 0){
                return -2;
            }
            String SQL = "insert into tb_product" + "(id,name,price,qnt) values (?,?,?,?)";
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, u.getCode());
            cmd.setString(2, u.getName());
            cmd.setFloat(3, u.getPrice());
            cmd.setInt(4, u.getQnt());

            if (cmd.executeUpdate() > 0) {
                ResultSet rs = cmd.getGeneratedKeys();
                return rs.next() ? rs.getInt(1) : -1;
            }
            return -1;

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            Conexao.desconnect(con);
        }

    }

    public List<Product> list() {
        List<Product> list = new ArrayList<>();
        try {
            String SQL = "select * from tb_product order by id";
            cmd = con.prepareStatement(SQL);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("qnt")
                );
                list.add(p);

            }

        } catch (SQLException e) {
            System.err.println("ERRO: " + e.getMessage());

        }
        return list;

    }

    public static void main(String[] args) {

    }

}
