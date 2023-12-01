module com.example.portefeuillefinancierisep {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.portefeuillefinancierisep to javafx.fxml;
    exports com.example.portefeuillefinancierisep;
}