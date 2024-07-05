package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import view.LoginView;

/**
 *
 * @author Tzh
 */

public class Conexao {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/prova_thiago";
    private static final String USR = "postgres";
    private static final String PWD = "password";
    private static final String DRIVER = "org.postgresql.Driver";
    
    public static Connection connect(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL,USR,PWD);
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return null;
        }
    }
    
    public static void desconnect(Connection con){
        try {
            
            if(con != null) con.close();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Connection con = Conexao.connect();
        if (con != null){
            System.out.println("Conexão realizada com sucesso!");
            LoginView login = new LoginView();
            login.setVisible(true);
        }
        Conexao.desconnect(con);
    }
    
}
