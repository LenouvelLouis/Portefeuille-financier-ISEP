package com.example.portefeuillefinancierisep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeApplication extends Application {
    /**
     * @param stage
     * @throws IOException
     *
     * Cette fonction permet de lancer l'application
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home-view.fxml")); // On charge le fichier fxml
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Home"); // On donne un titre à la fenêtre
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false); // On empêche le redimensionnement de la fenêtre
    }

    /**
     * @param args
     *
     * Cette fonction permet de lancer l'application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
