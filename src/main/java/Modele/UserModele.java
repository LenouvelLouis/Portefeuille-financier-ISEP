package Modele;

import Info.UserInfo;
import java.sql.*;

public class UserModele {
    private Connection conn = null;
    private ResultSet rs = null;
    private Statement pst = null;

    private void initConnection() {
        try {
            this.conn = ConnectDB.ConnectMariaDB();
            assert conn != null;
            this.pst = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error : UserModel -> initConnection : " + e.getMessage());
        }
    }

    public void create_user(UserInfo user) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO user (nom, prenom, tel, mail, h_mdp, salt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, user.getNom());
                pst.setString(2, user.getPrenom());
                pst.setString(3, user.getTel());
                pst.setString(4, user.getMail());
                pst.setString(5, user.getH_mdp());
                pst.setString(6, user.getSalt());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error : UserModel -> create_user : " + e.getMessage());
        }
    }

    public void updateUserInfo(UserInfo user){
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "UPDATE user SET nom=?, prenom=?, tel=?, mail=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, user.getNom());
                pst.setString(2, user.getPrenom());
                pst.setString(3, user.getTel());
                pst.setString(4, user.getMail());
                pst.setInt(5, user.getId());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error : UserModel -> updateUserInfo : " + e.getMessage());
        }
    }

    public UserInfo getUserInfo(String mail){
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM user WHERE mail = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, mail);
                try (ResultSet rs = pst.executeQuery()) {
                    if(rs.next()){
                        return new UserInfo(
                                rs.getInt("id"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("tel"),
                                rs.getString("mail"),
                                rs.getString("h_mdp"),
                                rs.getString("salt"),
                                rs.getFloat("apport")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        } catch (SQLException e) {
            throw new RuntimeException("Error : UserModel -> is_user_create : " + e.getMessage());
        }
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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

    public void updateFunds(int userId, float amount) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "UPDATE wallet_db.user SET apport=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setFloat(1, amount);
                pst.setInt(2, userId);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: UserModele -> updateFunds : " + e.getMessage());
        }
    }
}

