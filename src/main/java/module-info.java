module com.example.portefeuillefinancierisep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires org.mariadb.jdbc;
    requires java.net.http;
    requires org.json;
    opens com.example.portefeuillefinancierisep to javafx.fxml;
    exports com.example.portefeuillefinancierisep;
    exports Modele;
    opens Modele to javafx.fxml;
}