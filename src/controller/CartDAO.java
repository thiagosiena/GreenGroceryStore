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
import model.Cart;
import model.User;

/**
 *
 * @author Tzh
 */

public class CartDAO {

    private final Connection con;
    private PreparedStatement cmd;

    public CartDAO() {
        this.con = Conexao.connect();
    }
    
    public int clearCart(Cart cart) {
    String deleteProductsSQL = "delete from tb_cart_product where cart_id = ?";
    String updateCartSQL = "update tb_cart set total = 0 where id = ?";

    try {
        

        cmd = con.prepareStatement(deleteProductsSQL);
        cmd.setInt(1, cart.getCode());
        int rowsDeleted = cmd.executeUpdate();

        
        cmd = con.prepareStatement(updateCartSQL);
        cmd.setInt(1, cart.getCode());
        int rowsUpdated = cmd.executeUpdate();

        
        if (rowsDeleted > 0 && rowsUpdated > 0) {
          
            return 0; 
        } else {
            
            return -1; 
        }
    } catch (SQLException e) {
      
        System.err.println("ERRO" + e.getMessage());
        return -1;
    } 
    
}
 

    public boolean isProductCodeAlreadyInCart(int cartCode, int productCode) {
        String SQL = "select * from tb_cart_product where cart_id = ? and product_id= ?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, cartCode);
            cmd.setInt(2, productCode);
            ResultSet rs = cmd.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

    public void updateAllCartTotals() {
        try {

            String selectCartsSQL = "select id from tb_cart";
            cmd = con.prepareStatement(selectCartsSQL);
            ResultSet cartsResult = cmd.executeQuery();

            while (cartsResult.next()) {
                int cartId = cartsResult.getInt("id");
                float cartTotal = calculateCartTotal(cartId);

                String updateTotalSQL = "update tb_cart set total = ? where id = ?";
                cmd = con.prepareStatement(updateTotalSQL);
                cmd.setFloat(1, cartTotal);
                cmd.setInt(2, cartId);
                cmd.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("ERRO" + e.getMessage());
        }
    }

    private float calculateCartTotal(int cartId) {
        try {
            String calcTotalSQL = "select sum(subtotal) as total from tb_cart_product where cart_id = ?";
            cmd = con.prepareStatement(calcTotalSQL);
            cmd.setInt(1, cartId);
            ResultSet totalResult = cmd.executeQuery();
            if (totalResult.next()) {
                return totalResult.getFloat("total");
            } else {
                return 0;
            }

        } catch (SQLException e) {
            System.err.println("ERRO" + e.getMessage());
            return -1;
        }
    }

    public int updateCartTotal(int cartId) {

        String updateSQL = "update tb_cart set total = ? where id = ?";

        try {

            float totalSum = calculateCartTotal(cartId);
            cmd = con.prepareStatement(updateSQL);
            cmd.setDouble(1, totalSum);
            cmd.setInt(2, cartId);
            int affectedRows = cmd.executeUpdate();
            return affectedRows > 0 ? 0 : -1;

        } catch (SQLException e) {
            return -1;
        }

    }

    public int addProductToCart(Cart cart, Product p, int qnt) {

        if (isProductCodeAlreadyInCart(cart.getCode(), p.getCode())) {
            String SQL = "update tb_cart_product set quantity = quantity + ?, unit_price = ?, subtotal = ? where cart_id = ? and product_id = ?";
            String selectQntSQL = "select quantity from tb_cart_product where cart_id = ? and product_id = ?";

            try {
                cmd = con.prepareStatement(selectQntSQL);
                cmd.setInt(1, cart.getCode());
                cmd.setInt(2, p.getCode());
                ResultSet checkRs = cmd.executeQuery();
                if (checkRs.next()) {
                    int existingQuantity = checkRs.getInt("quantity");
                    int newQuantity = existingQuantity + qnt;
                    float newSubtotal = newQuantity * p.getPrice();

                    try {
                        cmd = con.prepareStatement(SQL);
                        cmd.setInt(1, qnt);
                        cmd.setFloat(2, p.getPrice());
                        cmd.setFloat(3, newSubtotal);
                        cmd.setInt(4, cart.getCode());
                        cmd.setInt(5, p.getCode());
                        int rowsAffected = cmd.executeUpdate();
                        return rowsAffected > 0 ? updateCartTotal(cart.getCode()) : -1;

                    } catch (SQLException e) {
                        System.err.println("ERRO" + e.getMessage());
                        return -1;
                    }
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                System.err.println("ERRO" + e.getMessage());
                return -1;
            }
        } else {
            String SQL = "insert into tb_cart_product (cart_id, product_id, product_name, quantity, unit_price, subtotal) values (?, ?, ?, ?, ?, ?)";
            try {
                cmd = con.prepareStatement(SQL);
                cmd.setInt(1, cart.getCode());
                cmd.setInt(2, p.getCode());
                cmd.setString(3, p.getName());
                cmd.setInt(4, qnt);
                cmd.setFloat(5, p.getPrice());
                cmd.setFloat(6, p.getPrice() * qnt);
                int rowsAffected = cmd.executeUpdate();
                return rowsAffected > 0 ? updateCartTotal(cart.getCode()) : -1;
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        }

    }


    public int removeProductOfCart(Cart cart, Product p) {
   

        try {
            String selectQntSQL = "select quantity from tb_cart_product where cart_id = ? and product_name = ?";
            String deleteSQL = "delete from tb_cart_product where cart_id = ? and product_name = ?";

            // Seleciona a quantidade do produto no carrinho
            cmd = con.prepareStatement(selectQntSQL);
            cmd.setInt(1, cart.getCode());
            cmd.setString(2, p.getName());
            ResultSet checkRs = cmd.executeQuery();

            if (checkRs.next()) {
                int existingQuantity = checkRs.getInt("quantity");

                // Deleta o produto do carrinho
                cmd = con.prepareStatement(deleteSQL);
                cmd.setInt(1, cart.getCode());
                cmd.setString(2, p.getName());

                if (cmd.executeUpdate() > 0) {
                    updateCartTotal(cart.getCode());
                    return existingQuantity;
                }
            }

            return -1;
        } catch (SQLException e) {
            System.err.println("ERRO" + e.getMessage());
            return -1;
        }
    }

    public Cart getCartWithProducts(User u) {
        Cart cart = null;
        List<Product> products = new ArrayList<>();
        String cartSQL = "select id, total from tb_cart where user_id = ?";
        try {
            cmd = con.prepareStatement(cartSQL);
            cmd.setInt(1, u.getId());
            try (ResultSet cartRs = cmd.executeQuery()) {
                if (!cartRs.next()) {
                    System.out.println("Not find any cart for the user");
                    return null;
                }
                int cartId = cartRs.getInt("id");
                float total = cartRs.getFloat("total");

                String productSQL = "SELECT p.id AS product_id, p.name, p.price, cp.quantity "
                        + "FROM tb_cart_product cp "
                        + "JOIN tb_product p ON cp.product_id = p.id "
                        + "WHERE cp.cart_id = ?";
                try {
                    cmd = con.prepareStatement(productSQL);
                    cmd.setInt(1, cartId);
                    try (ResultSet rs = cmd.executeQuery()) {
                        while (rs.next()) {
                            int productId = rs.getInt("product_id");
                            String name = rs.getString("name");
                            float price = rs.getFloat("price");
                            int quantity = rs.getInt("quantity");
                            float subtotal = price * quantity;
                            Product product = new Product(productId, name, price, quantity, subtotal);
                            products.add(product);
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("ERRO" + e.getMessage());
                }
                cart = new Cart(cartId, u, (ArrayList<Product>) products, total);
            }
        } catch (SQLException e) {
            System.err.println("ERRO" + e.getMessage());
        }
        return cart;
    }

    public boolean doesUserExist(int userId) {

        String SQL = "select id from tb_usuario where id = ?";
        try {
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, userId);
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("ERRO" + e.getMessage());
            return false;
        }

    }

    public void createCartIfNotExists(User u) {
        if (u == null) {
            System.err.println("User Invalid");
            return;
        }

        try {
            if (!doesUserExist(u.getId())) {
                System.err.println("User not found");
                return;
            }

            String checkCartSQL = "select id from tb_cart where user_id = ?";
            cmd = con.prepareStatement(checkCartSQL);
            cmd.setInt(1, u.getId());
            ResultSet checkCartRs = cmd.executeQuery();

            if (!checkCartRs.next()) {
                String SQL = "insert into tb_cart (user_id, total) values (?, 0.0)";
                cmd = con.prepareStatement(SQL);
                cmd.setInt(1, u.getId());
                cmd.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("ERRO" + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("ERRO" + e.getMessage());
            }
        }
    }

}
