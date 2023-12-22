package Modele;

import Info.UserInfo;
import Info.WalletInfo;
import Info.WalletValueInfo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletModele {
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

    public List<WalletInfo> getAllWallet(String login) throws SQLException {

        if(this.conn==null){
            this.initConnection();
        }
        String sql = "SELECT * FROM wallet_db.wallet_user "
                + "LEFT JOIN wallet_db.wallet_value ON wallet_db.wallet_user.id = wallet_db.wallet_value.id_wallet "
                + "WHERE wallet_db.wallet_user.mail_user = '" + login + "' ORDER BY wallet_db.wallet_value.date DESC ";
        try {
            rs = this.pst.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<WalletInfo> walletInfoList = new ArrayList<>();
        try {
            while (rs.next()) {
                String walletname = rs.getString("wallet_user.name");
                int walletId = rs.getInt("wallet_value.id_wallet");
                double walletValue = rs.getDouble("wallet_value.value");
                Date walletDate = rs.getDate("wallet_value.date");
                String type=rs.getString("wallet_value.type");
                WalletInfo walletInfo = findWalletById(walletInfoList, walletId);

                if (walletInfo == null) {
                    walletInfo = new WalletInfo(walletId, walletname, new ArrayList<>());
                    walletInfoList.add(walletInfo);
                }
                WalletValueInfo walletValueInfo = new WalletValueInfo(walletDate,walletValue,type);
                walletInfo.getWalletValueInfoList().add(walletValueInfo);
            }
        } catch(Exception  e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }
        return walletInfoList;
    }

    private static WalletInfo findWalletById(List<WalletInfo> walletInfoList, int walletId) {
        for (WalletInfo walletInfo : walletInfoList) {
            if (walletInfo.getId() == walletId) {
                return walletInfo;
            }
        }
        return null;
    }
}
