package org.wizard_nightmare.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.exceptions.EndGameException;
import org.wizard_nightmare.game.demiurge.exceptions.GoHomekException;
import org.wizard_nightmare.ui.common.Constants;

public class RoomController implements DemiurgeConsumer {

    private Demiurge demiurge;
    @FXML
    private ImageView creature;
    @FXML
    private AnchorPane screen;
    @FXML
    private Label infoLabel;


    public void initialize() {
        try {
            String imagePath = getClass().getResource(Constants.ROOM_IMAGE).toExternalForm();
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
        screen.setFocusTraversable(true);
        screen.requestFocus();
        screen.setOnKeyPressed(this::handleArrow);
        if (this.demiurge.getDungeonManager().hasCreature())
            creature.setVisible(true);
    }

    private void handleArrow(KeyEvent event) {
        System.out.println("Key pressed : " + event.getCode());
        int selection = -1;
        if (event.getCode() == KeyCode.W) {
            selection = 0;
        } else if (event.getCode() == KeyCode.D) {
            selection = 1;
        } else if (event.getCode() == KeyCode.S) {
            selection = 2;
        } else if (event.getCode() == KeyCode.A) {
            selection = 3;
        }
        if (selection >= 0 && selection < demiurge.getDungeonManager().getNumberOfDoors()) {
            try {
                demiurge.getDungeonManager().openDoor(selection);
                App.cambiarPantalla(demiurge, Constants.ROOM);
            } catch (WizardTiredException e) {
                showInfoLabel("Good night... zZzZzZzz");
                App.cambiarPantalla(demiurge, Constants.HOME);
            } catch (GoHomekException e) {
                App.cambiarPantalla(demiurge, Constants.HOME);
            } catch (EndGameException e) {
                App.cambiarPantalla(demiurge, Constants.FINISH);
            }
        } else
            showInfoLabel("There is not a room there");
    }

    private void showInfoLabel(String message) {
        infoLabel.setText(message);
        infoLabel.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), infoLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    public void faceCreature() {
        App.cambiarPantalla(demiurge, Constants.FACE_CREATURE);
    }
}
