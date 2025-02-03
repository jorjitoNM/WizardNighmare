package org.wizard_nightmare.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.SneakyThrows;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.exceptions.EndGameException;
import org.wizard_nightmare.game.demiurge.exceptions.GoHomekException;

public class HomeController implements DemiurgeConsumer {
    @FXML
    private ProgressBar energiaProgress;
    @FXML
    private ProgressBar vidaProgress;
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
        loadHome();
    }


    private void loadHome() {
        energiaProgress.setProgress(demiurge.getWizard().getEnergy());
        vidaProgress.setProgress(demiurge.getWizard().getLife());
       //screen.setOnKeyPressed(this::handleArrow);
    }

    private void handleArrow(KeyEvent event) throws GoHomekException, WizardTiredException, EndGameException {
        int selection = -1;
        if (event.getCode() == KeyCode.UP) {
            selection = 0;
        } else if (event.getCode() == KeyCode.DOWN) {
            selection = 1;
        } else if (event.getCode() == KeyCode.LEFT) {
            selection = 2;
        } else if (event.getCode() == KeyCode.RIGHT) {
            selection = 3;
        }
        if (selection > 0 && selection <= demiurge.getDungeonManager().getNumberOfDoors()) {
            demiurge.getDungeonManager().openDoor(selection - 1);
        }
    }

    @SneakyThrows
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
            demiurge.nextDay();
            System.out.println("cama");
        } else if ((source == hechizos1) || (source == hechizos2)) {
            App.cambiarPantalla(demiurge, "/screens/spell_library.fxml");
        } else if (source == cofre) {
            App.cambiarPantalla(demiurge, "/screens/chest.fxml");
        } else if (source == singa) {
            App.cambiarPantalla(demiurge, "/screens/singa_storage.fxml");
        }
    }
}
