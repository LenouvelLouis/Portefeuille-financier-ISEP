package com.example.portefeuillefinancierisep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddWalletController {

    @FXML
    TextField name;

    @FXML
    TextField apport;


    public void Submit(ActionEvent actionEvent) {
        String name = this.name.getText();
    /*    try {
            int apport = Integer.parseInt(this.apport.getText());

        }*/
    }
}
