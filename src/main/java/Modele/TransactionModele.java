package Modele;

import Info.TransactionInfo;
import Info.TransactionTypeInfo;
import Info.UserInfo;

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
                        TransactionTypeInfo w = new TransactionTypeInfo(nom);
                        transactionTypeInfoList.add(w); // On ajoute les résultats dans une liste
                    }
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
                        TransactionTypeInfo w = new TransactionTypeInfo(nom);
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
            String sql = "INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, t.getId_wallet());
                pst.setFloat(2, t.getValue());
                pst.setTimestamp(3, t.getDate());
                pst.setString(4,t.getType());
                pst.setString(5,t.getLibelle_type());
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
                        TransactionInfo t = new TransactionInfo(id_wallet,value, date, type, libelle_type);
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
