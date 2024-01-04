package Modele;

import Info.UserInfo;
import Info.WalletInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletModele {

    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;

    private void initConnection() {
        this.conn = ConnectDB.ConnectMariaDB();
        try {
            assert conn != null;
            this.pst = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error : Wallet -> initConnection : "+e.getMessage());
        }
    }

    public boolean is_wallet_create(UserInfo u, String name) {

        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.wallet_user WHERE mail_user = ? AND name = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, u.getMail());
                pst.setString(2, name);
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.last();
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> is_wallet_create : "+e.getMessage());
        }
    }
    public void create_wallet(UserInfo user,String name) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO wallet_db.wallet_user (name, mail_user) VALUES (?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, name);
                pst.setString(2, user.getMail());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> create_wallet : "+e.getMessage());
        }
    }

    public List<WalletInfo> getWalletInfo(UserInfo u){
        ArrayList walletInfoList = new ArrayList<WalletInfo>();
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.wallet_user WHERE mail_user = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, u.getMail());
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()){
                        String nom = rs.getString("name");
                        String mail_user = rs.getString("mail_user");
                        float totale = rs.getFloat("totale");
                        float totale_action = rs.getFloat("totale_action");
                        float totale_crypto= rs.getFloat("totale_crypto");
                        WalletInfo w = new WalletInfo(nom,mail_user,totale,totale_action,totale_crypto);
                        walletInfoList.add(w);
                    }
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> getWalletInfo : "+e.getMessage());
        }
        return walletInfoList;
    }

}
