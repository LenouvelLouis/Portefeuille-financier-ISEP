package Modele;

import Info.TransactionInfo;
import Info.TransactionTypeInfo;

import java.sql.*;
import java.util.ArrayList;
/**
 * Class qui permet de de faire les requêtes sur la table transaction
 */
public class TransactionModele {
    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;

    /**
     * Fonction qui permet de se connecter à la base de données
     */
    private void initConnection() {
        this.conn = ConnectDB.ConnectMariaDB();
        try {
            assert conn != null;
            this.pst = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error : TransactionModele -> initConnection : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet de récupérer les entreprises
     * @return ArrayList<TransactionTypeInfo>
     */
    public static void updateCryptoPrice(String name, double price) {
        try {
            Connection conn = ConnectDB.ConnectMariaDB();
            String sql = "UPDATE wallet_db.crypto SET value = ? WHERE nom = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setDouble(1, price);
                pst.setString(2, name);
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : TransactionModele -> updateCryptoPrice : "+e.getMessage());
        }
    }

    public static void updateActionPrice(String symbol, Number value) {
        try {
            Connection conn = ConnectDB.ConnectMariaDB();
            String sql = "UPDATE wallet_db.entreprise SET value = ? WHERE nom = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setDouble(1, value.doubleValue());
                pst.setString(2, symbol);
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : TransactionModele -> updateActionPrice : "+e.getMessage());
        }
    }

    public ArrayList<TransactionTypeInfo> getEntreprise(){
        ArrayList<TransactionTypeInfo> transactionTypeInfoList = new ArrayList<TransactionTypeInfo>();
        try {
            if (this.conn == null) { // Si la connexion n'est pas initialisé
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.entreprise"; // Requête SQL
            try (PreparedStatement pst = conn.prepareStatement(sql)) { // On prépare la requête
                try (ResultSet rs = pst.executeQuery()) { // On exécute la requête
                    while (rs.next()){ // On parcours les résultats
                        String nom = rs.getString("nom");
                        Double value = rs.getDouble("value");
                        TransactionTypeInfo w = new TransactionTypeInfo(nom,value);
                        transactionTypeInfoList.add(w);}
                }
            }
        } catch (SQLException | RuntimeException e) { // Si il y a une erreur
            throw new RuntimeException("Error : TransactionModele -> getEntreprise : "+e.getMessage());
        }
        return transactionTypeInfoList;
    }

    /**
     * Fonction qui permet de récupérer les crypto monnaies
     * @return ArrayList<TransactionTypeInfo>
     */
    public ArrayList<TransactionTypeInfo> getCrypto(){
        ArrayList<TransactionTypeInfo> transactionTypeInfoList = new ArrayList<TransactionTypeInfo>();
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.crypto";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()){
                        String nom = rs.getString("nom");
                        Double value = rs.getDouble("value");
                        TransactionTypeInfo w = new TransactionTypeInfo(nom,value);
                        transactionTypeInfoList.add(w);
                    }
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : TransactionModele -> getCrypto : "+e.getMessage());
        }
        return transactionTypeInfoList;
    }

    /**
     * Fonction qui permet d'ajouter une transaction
     */
    public void addTransaction(TransactionInfo t) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type,real_value) VALUES (?, ?, ?, ?, ?,?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, t.getId_wallet());
                pst.setFloat(2, t.getValue());
                pst.setTimestamp(3, t.getDate());
                pst.setString(4,t.getType());
                pst.setString(5,t.getLibelle_type());
                pst.setFloat(6,t.getRealvalue());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : TransactionModele -> addTransaction : "+e.getMessage());
        }
    }

    /**
     * Fonction qui permet de récupérer les transactions d'un wallet
     * @param id
     * @return ArrayList<TransactionInfo>
     */
    public ArrayList<TransactionInfo> getTransactionByWallet(int id) {
        ArrayList<TransactionInfo> transactionInfos = new ArrayList<TransactionInfo>();
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.transaction WHERE id_wallet = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int id_wallet = rs.getInt("id_wallet");
                        float value = rs.getFloat("value");
                        Timestamp date = rs.getTimestamp("date");
                        String type = rs.getString("type");
                        String libelle_type = rs.getString("libelle_type");
                        Float realvalue = rs.getFloat("real_value");
                        TransactionInfo t = new TransactionInfo(id_wallet,value, date, type, libelle_type, realvalue);
                        transactionInfos.add(t);
                    }
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : TransactionModele -> getTransactionByWallet : "+e.getMessage());
        }
        return transactionInfos;
    }
}
