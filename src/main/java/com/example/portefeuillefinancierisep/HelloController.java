package com.example.portefeuillefinancierisep;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.*;


public class HelloController {
    @FXML
    private Label welcomeText;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        initialize();
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() throws SQLException {
        String sql = "SELECT * FROM database.user";


        conn = ConnectDB.ConnectMariaDB();
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name"));

            }
        } catch(Exception  e) {
            System.out.println("There is an Exception.");
            System.out.println(e.getMessage());
        }
    }


}