package com.example.portefeuillefinancierisep;

import Info.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Modele.UserModele;

public class FundsController {

    @FXML
    private TextField fundsField;

    @FXML
    private Label msgLabel;

    private UserModele userModel = new UserModele();
    private UserInfo u;

    @FXML
    protected void handleAddFunds() {
        try {
            float amount = Float.parseFloat(fundsField.getText());

            // Obtient l'ID de l'utilisateur connecté à partir de la classe Session
            int userId = this.u.getId();

            userModel.updateFunds(userId, amount);

            msgLabel.setText("Fonds ajoutés : " + amount + " €");
        } catch (NumberFormatException e) {
            msgLabel.setText("Veuillez entrer un montant valide.");
        } catch (Exception e) {
            msgLabel.setText("Erreur lors de la mise à jour des fonds.");
        }
    }

    public void initializeUser(UserInfo u) {
        this.u=u;
    }
}
