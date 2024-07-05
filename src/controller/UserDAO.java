package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tzh
 */

public class UserDAO {

    private Connection con;
    private PreparedStatement cmd;

    public UserDAO() {
        this.con = Conexao.connect();
    }


    public User getUserByEmail(String email) {

        String SQL = "select * from tb_usuario where email=?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setString(1, email);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("name"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("ERRO2: " + e.getMessage());
        }

        return null;

    }

    public User getUserByCode(int code) {

        String SQL = "select * from tb_usuario where id =?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setInt(1, code);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("name"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("ERRO2: " + e.getMessage());
        }

        return null;

    }

    public boolean isAlreadyRegistered(String email) {
        String SQL = "select * from tb_usuario where email=?";
        try {
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setString(1, email);
            ResultSet rs = cmd.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

    public boolean login(User u) {
        try {
            String SQL = "select * from tb_usuario where email=? and password=MD5(?)";
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setString(1, u.getEmail());
            cmd.setString(2, u.getPassword());
            ResultSet rs = cmd.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconnect(con);
        }
    }

    public int CreateUser(User u) {
        try {
            return 1;
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            Conexao.desconnect(con);
        }
    }

    public int insert(User u) {
        try {
            if (isAlreadyRegistered(u.getEmail())) {
                return -1;
            }
            String SQL = "insert into tb_usuario" + "(email,password,name) values (?,MD5(?),?)";
            cmd = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            cmd.setString(1, u.getEmail());
            cmd.setString(2, u.getPassword());
            cmd.setString(3, u.getName());

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

    public static void main(String[] args) {

    }
}
