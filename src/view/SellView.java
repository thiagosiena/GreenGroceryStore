
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.CartDAO;
import controller.ProductDAO;
import java.awt.Component;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Cart;
import model.Product;
import model.User;

/**
 *
 * @author Tzh
 */

public class SellView extends javax.swing.JFrame {

    private final User user;

    public SellView(User u) {

        this.user = u;
        initComponents();
        configureStockTable();
        configureCartTable();
        populateCartTable(cartTable);
        populateStockTable(stockTable);
        this.setTitle("Sell Interface");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void updateStockTable(JTable table) {

        DefaultTableModel defaultM = new DefaultTableModel();
        table.setModel(defaultM);
        configureStockTable();
        table.setModel(populateStockTable(table));

    }

    private void updateCartTable(JTable table) {

        DefaultTableModel defaultM = new DefaultTableModel();
        table.setModel(defaultM);
        configureCartTable();
        table.setModel(populateCartTable(table));

    }

    private DefaultTableModel populateCartTable(JTable table) {
        CartDAO cartD = new CartDAO();
        cartD.createCartIfNotExists(user);
        Cart cart = new CartDAO().getCartWithProducts(user);
        totalField.setText(valueOf(cart.getTotal()));
        List<Product> list = cart.getProducts();
        DefaultTableModel tableM = (DefaultTableModel) table.getModel();
        for (Product p : list) {
            tableM.addRow(new Object[]{p.getName(), p.getPrice(), p.getQnt(), p.getSubtotal()});

        }
        return tableM;

    }

    private DefaultTableModel populateStockTable(JTable table) {
        List<Product> list = new ProductDAO().list();
        DefaultTableModel tableM = (DefaultTableModel) table.getModel();
        for (Product p : list) {
            tableM.addRow(new Object[]{p.getCode(), p.getName(), p.getPrice(), p.getQnt()});

        }
        return tableM;

    }

    private void configureCartTable() {

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }

        };

        model.addColumn("product name");
        model.addColumn("unit price");
        model.addColumn("quantity");
        model.addColumn("subtotal");

        cartTable.setModel(model);

    }

    private List<Object> getAllObjectsInRow(JTable table, int rowIndex) {
        List<Object> Obj = new ArrayList<>();
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            Obj.add(table.getValueAt(rowIndex, columnIndex));
        }
        return Obj;
    }

    private Product createProductFromRow(List<Object> rowObjects) {
        if (rowObjects.size() != 4) {
            throw new IllegalArgumentException("ERRO");
        }

        int code = (int) rowObjects.get(0);
        String name = (String) rowObjects.get(1);
        float price = ((Number) rowObjects.get(2)).floatValue();
        int qnt = (int) rowObjects.get(3);

        return new Product(code, name, price, qnt);
    }

    private void configureStockTable() {

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }

        };

        model.addColumn("code");
        model.addColumn("product name");
        model.addColumn("unit price");
        model.addColumn("quantity");

        stockTable.setModel(model);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartTable = new javax.swing.JTable();
        totalField = new java.awt.TextField();
        addToCartButton = new javax.swing.JButton();
        qntField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        removeCartButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        buyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backButton.setText("Back");
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(stockTable);

        cartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(cartTable);

        totalField.setText("textField1");
        totalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalFieldActionPerformed(evt);
            }
        });

        addToCartButton.setText("Add To Cart");
        addToCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToCartButtonActionPerformed(evt);
            }
        });

        qntField.setPreferredSize(new java.awt.Dimension(129, 36));
        qntField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qntFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Quantity");

        jLabel2.setText(" Total");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("Cart");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("Stock");

        removeCartButton.setText(" Remove From Cart");
        removeCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCartButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Select the product from the table");

        jLabel6.setText("Select the product from the table");

        buyButton.setText("Buy");
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backButton)
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(removeCartButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buyButton)))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(totalField, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(qntField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addToCartButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qntField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addToCartButton))
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(2, 2, 2)
                        .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeCartButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(buyButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(backButton)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        MainView m = new MainView(user);
        this.dispose();
        m.setVisible(true);
    }//GEN-LAST:event_backButtonActionPerformed

    private void qntFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qntFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qntFieldActionPerformed

    private void addToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToCartButtonActionPerformed
        try {
            int[] selectedRows = stockTable.getSelectedRows();
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(null, "No rows selected. Please select a product", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else if (selectedRows.length > 1) {
                JOptionPane.showMessageDialog(null, "More than one row selected. Please select only one product at a time", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else {
                int qnt = Integer.parseInt(qntField.getText());
                if (qnt >= 0) {
                    Product p = createProductFromRow(getAllObjectsInRow(stockTable, selectedRows[0]));
                    Cart cart = new CartDAO().getCartWithProducts(user);
                    int rs = new ProductDAO().subQnt(p, qnt);
                    if (rs == 0) {
                        if (new CartDAO().addProductToCart(cart, p, qnt) != -1) {
                            updateCartTable(cartTable);

                            updateStockTable(stockTable);
                        }
                    } else if (rs == -2) {
                        JOptionPane.showMessageDialog(null, "Please enter a number equal or less than to what is in stock", "ERRO", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Only positive numbers", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Only numbers please", "ERRO", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro: " + e.getMessage());
        }


    }//GEN-LAST:event_addToCartButtonActionPerformed

    private void totalFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalFieldActionPerformed

    private void removeCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCartButtonActionPerformed
        try {

            int[] selectedRows = cartTable.getSelectedRows();
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(null, "No rows selected. Please select a product", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else if (selectedRows.length > 1) {
                JOptionPane.showMessageDialog(null, "More than one row selected. Please select only one product at a time", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else {
                List<Object> list = getAllObjectsInRow(cartTable, selectedRows[0]);
                String name = (String) list.get(0);
                float price = ((Number) list.get(2)).floatValue();
                int qnt = (int) list.get(2);
                float subtotal = ((Number) list.get(2)).floatValue();

                Product p = new Product(name, price, qnt, subtotal);
                Cart cart = new CartDAO().getCartWithProducts(user);

                int quantity = new CartDAO().removeProductOfCart(cart, p);
                if (quantity != -1) {
                    if (new ProductDAO().addQnt(p, qnt) != -1) {
                        updateCartTable(cartTable);
                        updateStockTable(stockTable);
                        JOptionPane.showMessageDialog(null, "Product has been removed", "Sucess", JOptionPane.NO_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add quantity back to stock", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to remove product from cart", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_removeCartButtonActionPerformed

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
         try {
           
        new CartDAO().clearCart(new CartDAO().getCartWithProducts(user));
        updateCartTable(cartTable);
        JOptionPane.showMessageDialog(null, "Purchase made successfully!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "An error occurred when making the purchase: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_buyButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SellView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SellView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SellView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SellView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new SellView(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToCartButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton buyButton;
    private javax.swing.JTable cartTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField qntField;
    private javax.swing.JButton removeCartButton;
    private javax.swing.JTable stockTable;
    private java.awt.TextField totalField;
    // End of variables declaration//GEN-END:variables
}
