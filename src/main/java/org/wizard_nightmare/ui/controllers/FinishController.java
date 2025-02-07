package org.wizard_nightmare.ui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.ui.common.Constants;

public class FinishController implements DemiurgeConsumer {

    @FXML
    private AnchorPane screen;

    private Demiurge demiurge;

    public void initialize() {
        try {
            String imagePath = getClass().getResource(Constants.FINISH_IMAGE).toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    @Override
    public void setDemiurge(Demiurge demiurge) {
        this.demiurge = demiurge;
    }

    public void goToMenu(ActionEvent actionEvent) {
        App.cambiarPantalla(demiurge, Constants.START);
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
