package com.example.portefeuillefinancierisep;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    protected void ConnexionButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("auth-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    protected void InscriptionButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("inscription-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
