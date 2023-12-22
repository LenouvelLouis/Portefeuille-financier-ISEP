package Modele;

import Info.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserModele {
    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;

    private void initConnection() throws SQLException {
        this.conn=ConnectDB.ConnectMariaDB();
        try {
            assert conn != null;
            this.pst = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void create_user(UserInfo user) throws SQLException {
        if(this.conn==null){
            this.initConnection();
        }

        String sql = "INSERT INTO wallet_db.user (nom, prenom, tel, mail, h_mdp) VALUES ('" + user.getNom() + "', '" + user.getPrenom() + "', '" + user.getTel() + "', '" + user.getMail() + "', '" + user.getH_mdp() + "')";

        try {
            pst.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean is_user_create(String mail) throws SQLException {

        if(this.conn==null){
            this.initConnection();
        }

        String sql = "SELECT * FROM wallet_db.user WHERE mail ='"+mail+"'";

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
