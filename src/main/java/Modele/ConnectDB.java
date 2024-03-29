package Modele;

import java.sql.*;

public class ConnectDB {

    /**
     * Fonction qui permet de se connecter à la base de données
     * @return Connection
     */
    public static Connection ConnectMariaDB() {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            // Assurez-vous que les informations de connexion sont correctes
            conn = DriverManager.getConnection("jdbc:mariadb://localhost/wallet_db", "root", "root");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
