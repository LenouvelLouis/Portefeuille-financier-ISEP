package Modele;

import Info.TransactionInfo;
import Info.TransactionTypeInfo;
import Info.UserInfo;

import java.sql.*;
import java.util.ArrayList;

public class TransactionModele {
    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;

    private void initConnection() {
        this.conn = ConnectDB.ConnectMariaDB();
        try {
            assert conn != null;
            this.pst = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error : TransactionModele -> initConnection : "+e.getMessage());
        }
    }

    public ArrayList<TransactionTypeInfo> getEntreprise(){
        ArrayList<TransactionTypeInfo> transactionTypeInfoList = new ArrayList<TransactionTypeInfo>();
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "SELECT * FROM wallet_db.entreprise";
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
            throw new RuntimeException("Error : TransactionModele -> getEntreprise : "+e.getMessage());
        }
        return transactionTypeInfoList;
    }

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


    public void addTransaction(TransactionInfo t) {
        try {
            if (this.conn == null) {
                this.initConnection();
            }
            String sql = "INSERT INTO wallet_db.transaction (id_wallet, value, date, type, libelle_type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, t.getId_wallet());
                pst.setFloat(2, t.getValue());
                pst.setDate(3, t.getDate());
                pst.setString(4,t.getType());
                pst.setString(5,t.getLibelle_type());
                pst.executeUpdate();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Error : TransactionModele -> addTransaction : "+e.getMessage());
        }
    }
}
