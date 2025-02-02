package org.wizard_nightmare.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;
import org.wizard_nightmare.game.demiurge.DemiurgeHomeManager;

public class HomeController implements DemiurgeConsumer {
    @FXML
    private Rectangle cama;

    @FXML
    private Rectangle hechizos1;

    @FXML
    private Rectangle hechizos2;

    @FXML
    private Rectangle cofre;

    @FXML
    private Rectangle singa;

    @FXML
    private AnchorPane screen;


    private Demiurge demiurge;


    @Override
    public void setDemiurge(Demiurge demiurge) {
        this.demiurge = demiurge;
    }


    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/habitacion_mago.png").toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");

        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }

    }


    public void handleClick(MouseEvent event) {
        Object source = event.getSource();
        if (source == cama) {
            System.out.println("cama");
        } else if ((source == hechizos1) || (source == hechizos2)) {
            App.cambiarPantalla(demiurge, "/screens/spell_library.fxml");
            System.out.println("hechizos");
        } else if (source == cofre) {
            App.cambiarPantalla(demiurge, "/screens/chest.fxml");
            System.out.println("cofre");
        } else if (source == singa) {
            App.cambiarPantalla(demiurge, "/screens/singa_storage.fxml");
            System.out.println("singa");
        }
    }
}
