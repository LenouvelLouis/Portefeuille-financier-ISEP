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

    /**
     * Fonction qui vérifie si un wallet existe déjà
     * @return boolean
     */
    public boolean is_wallet_create(UserInfo u, String name) {

        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.wallet_user WHERE id_user = ? AND name = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, u.getId());
                pst.setString(2, name);
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.last();
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> is_wallet_create : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet de créer un wallet
     * @param user, name
     */
    public void create_wallet(UserInfo user,String name) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO wallet_db.wallet_user (name, id_user) VALUES (?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, name);
                pst.setInt(2, user.getId());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> create_wallet : "+e.getMessage());
        }
    }


    /**
     * Fonction qui permet de récupérer les wallets d'un utilisateur
     * @param u
     * @return ArrayList<WalletInfo>
     */
    public ArrayList<WalletInfo> getWalletInfo(UserInfo u){
        ArrayList walletInfoList = new ArrayList<WalletInfo>();
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.wallet_user WHERE id_user = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, u.getId());
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()){
                        int id = rs.getInt("id");
                        String nom = rs.getString("name");
                        int id_user = rs.getInt("id_user");
                        float totale = rs.getFloat("totale");
                        float totale_action = rs.getFloat("totale_action");
                        float totale_crypto= rs.getFloat("totale_crypto");
                        WalletInfo w = new WalletInfo(id,nom,id_user,totale,totale_action,totale_crypto);
                        walletInfoList.add(w);
                    }
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> getWalletInfo : "+e.getMessage());
        }
        return walletInfoList;
    }

    /**
     * Fonction qui permet mettre à jour le totale d'un wallet
     * @param walletInfo , v
     */
    public void updateTotal(WalletInfo walletInfo, float v) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "UPDATE wallet_db.wallet_user SET totale=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setFloat(1, v);
                pst.setInt(2, walletInfo.getId());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> updateTotal : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet mettre à jour le totale action d'un wallet
     * @param walletInfo , v
     */
    public void updateTotaleActions(WalletInfo walletInfo, float v) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "UPDATE wallet_db.wallet_user SET totale_action=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setFloat(1, v);
                pst.setInt(2, walletInfo.getId());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> updateTotaleActions : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet mettre à jour le totale crypto d'un wallet
     * @param walletInfo , v
     */
    public void updateTotaleCrypto(WalletInfo walletInfo, float v) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "UPDATE wallet_db.wallet_user SET totale_crypto=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setFloat(1, v);
                pst.setInt(2, walletInfo.getId());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> updateTotaleCrypto : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet de récupérer le totale d'un wallet
     * @param id
     * @return float
     */
    public float getTotale(int id) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT totale FROM wallet_db.wallet_user WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    rs.next();
                    return rs.getFloat("totale");
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> getTotale : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet de récupérer le totale action d'un wallet
     * @param id
     * @return float
     */
    public float getTotaleAction(int id) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT totale_action FROM wallet_db.wallet_user WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    rs.next();
                    return rs.getFloat("totale_action");
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> getTotaleAction : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet de récupérer le totale crypto d'un wallet
     * @param id
     * @return float
     */
    public float getTotaleCrypto(int id) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT totale_crypto FROM wallet_db.wallet_user WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    rs.next();
                    return rs.getFloat("totale_crypto");
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : WalletModele -> getTotaleCrypto : "+e.getMessage());
        }
    }
}
