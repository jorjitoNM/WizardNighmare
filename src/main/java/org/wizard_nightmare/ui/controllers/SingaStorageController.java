package org.wizard_nightmare.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.objectContainer.Container;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerEmptyException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerErrorException;

public class SingaStorageController implements DemiurgeConsumer {
    private Demiurge demiurge;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label crystalsLabel;
    @FXML
    private Label singaSpaceLabel;
    @FXML
    private Button mergeButton;

    private int crystals;
    private int singaSpace;
    private Container carrier;

    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/singa_almacen.jpeg").toExternalForm();
            anchorPane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    private void initializeDemiurgeData() {
        carrier = demiurge.getHomeManager().getCarrier();
        crystals = carrier.size();
        singaSpace = demiurge.getHomeManager().getSingaSpace();

        updateUI();
    }

    private void updateUI() {
        crystalsLabel.setText("Crystals: " + crystals);
        singaSpaceLabel.setText("Singa space available: " + singaSpace);
        mergeButton.setDisable(crystals == 0 || singaSpace == 0);
    }

    @FXML
    private void mergeCrystals() {
        if (crystals > 0 && singaSpace > 0) {
            try {
                demiurge.getHomeManager().mergeCrystal(0);
                crystals--;
                singaSpace--;
                updateUI();
            } catch (WizardTiredException | ContainerErrorException | ContainerEmptyException e) {
                System.out.println("Error merging crystals: " + e.getMessage());
            }
        }
    }

    @FXML
    public void returnBack(ActionEvent actionEvent) {
        App.cambiarPantalla(demiurge, "/screens/home.fxml");
    }

    @Override
    public void loadScreenData(Demiurge demiurge) {
        this.demiurge = demiurge;
        initializeDemiurgeData();
    }
}