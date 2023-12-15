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
    /*
    Méthode qui retourne tous les utilisateurs de la base de données
    {return} : ListUser
     */
    public void getAllUsers() throws SQLException {
        //List<UserInfo> listUser=new ArrayList<UserInfo>();
        if(this.conn==null){
            this.initConnection();
        }
        String sql = "SELECT * FROM wallet_db.user";

        try {
            rs = this.pst.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                //Exemple d'utilisation de liste d'info d'un utilisateur avec une classe user qui stock les infos
                //UserInfo u = new UserInfo(rs.getString("name"),rs.getInt("tel"),rs.getString("pays"),rs.getString("hashmdp"));
                //listUser.add(u);
                System.out.println(rs.getInt("id") + " " + rs.getString("nom"));

            }
        } catch(Exception  e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }
        //return ListUser;
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
}
