package org.wizard_nightmare.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
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


    @SneakyThrows
    public void initialize() {
        screen.setOnKeyPressed(this::handleArrow);
        try {
            String imagePath = getClass().getResource(Constants.HABITACION_LUCHA).toExternalForm();
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
        if (this.demiurge.getDungeonManager().hasCreature())
            creature.setVisible(true);
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

    public void faceCreature(MouseEvent mouseEvent) {
        App.cambiarPantalla(demiurge, Constants.HABITACION_LUCHA);
    }
}
