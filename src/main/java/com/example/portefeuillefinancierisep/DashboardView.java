package com.example.portefeuillefinancierisep;

import Modele.WalletModele;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class DashboardView extends Application {


    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(AuthApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Page de visualisation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
