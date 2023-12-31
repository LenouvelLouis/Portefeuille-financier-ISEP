package Modele;

import Info.UserInfo;
import java.sql.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModele {
    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;

    private void initConnection() {
        this.conn = ConnectDB.ConnectMariaDB();
        try {
            assert conn != null;
            this.pst = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error : UserModel -> initConnection");
        }
    }

    public void create_user(UserInfo user) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO wallet_db.user (nom, prenom, tel, mail, h_mdp, salt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, user.getNom());
                pst.setString(2, user.getPrenom());
                pst.setString(3, user.getTel());
                pst.setString(4, user.getMail());
                pst.setString(5, user.getH_mdp());
                pst.setString(6, user.getSalt());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : UserModel -> create_user");
        }
    }

    public boolean is_user_create(String mail) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.user WHERE mail = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, mail);
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.last();
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : UserModel -> is_user_create");
        }
    }

    public boolean is_login_valid(String mail, String password) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }

            String sql = "SELECT * FROM wallet_db.user WHERE mail ='" + mail + "' AND h_mdp = '" + password + "'";

            try {
                rs = this.pst.executeQuery(sql);

                if (rs.last()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error : UserModel -> is_login_valid");
        }
        return false;
    }
    public String getUserSalt(String email) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT salt FROM wallet_db.user WHERE mail = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, email);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("salt");
                    }
                }
        return null;
    }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserPassword(String email, String hashedPassword) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT h_mdp FROM wallet_db.user WHERE mail = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, email);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String storedHashedPassword = rs.getString("h_mdp");
                        return storedHashedPassword.equals(hashedPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
