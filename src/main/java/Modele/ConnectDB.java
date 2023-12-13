package Modele;

import java.sql.*;

public class ConnectDB {

    static Connection conn = null;
/*
* Mettre le nom de sa basse de données après localhost
* */
    public static Connection ConnectMariaDB() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://localhost/database","root","root");
            return conn;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
