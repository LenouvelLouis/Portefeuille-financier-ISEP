module com.example.portefeuillefinancierisep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires org.mariadb.jdbc;
    opens com.example.portefeuillefinancierisep to javafx.fxml;
    exports com.example.portefeuillefinancierisep;
    exports Modele;
    opens Modele to javafx.fxml;
}