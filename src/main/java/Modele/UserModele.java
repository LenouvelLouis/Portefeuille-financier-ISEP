package Modele;

import Info.UserInfo;
import java.sql.*;
import java.util.Arrays;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModele {
    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;

    private void initConnection() throws SQLException {
        this.conn = ConnectDB.ConnectMariaDB();
    }

    public void create_user(UserInfo user) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO wallet_db.user (nom, prenom, tel, mail, h_mdp,salt) VALUES (?, ?, ?, ?, ?,?)";
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
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean is_login_valid(String mail, String password) throws SQLException {

        if(this.conn==null){
            this.initConnection();
        }

        String sql = "SELECT * FROM wallet_db.user WHERE mail ='"+mail+"' AND h_mdp = '"+password+"'";

        try {
            rs = this.pst.executeQuery(sql);

            if(rs.last())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
}
