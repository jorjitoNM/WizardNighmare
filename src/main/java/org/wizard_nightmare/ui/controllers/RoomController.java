package org.wizard_nightmare.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class RoomController {

    @FXML
    private AnchorPane screen;




    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/habitacion.png").toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }
}
