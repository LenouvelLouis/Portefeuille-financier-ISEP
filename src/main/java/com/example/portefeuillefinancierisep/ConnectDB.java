package com.example.portefeuillefinancierisep;

import java.sql.*;

public class ConnectDB {

    static Connection conn = null;

    public static Connection ConnectMariaDB() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://localhost/database","root","root");
            System.out.println("Connection success!");
            return conn;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
