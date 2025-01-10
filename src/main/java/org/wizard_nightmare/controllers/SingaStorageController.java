package org.wizard_nightmare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.DemiurgeHomeManager;
import org.wizard_nightmare.game.objectContainer.Container;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerEmptyException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerErrorException;

import java.util.Iterator;
import java.util.Optional;

public class SingaStorageController {
    @FXML
    private AnchorPane anchorPane;

    private DemiurgeHomeManager homeManager;

    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/singaalmacen.jpeg").toExternalForm();
            anchorPane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }

        Container carrier = homeManager.getCarrier();
        int crystals = carrier.size();
        int singaSpace = homeManager.getSingaSpace();

        handleCrystalsAndSinga(carrier, crystals, singaSpace);
    }

    private void handleCrystalsAndSinga(Container carrier, int crystals, int singaSpace) {
        if (crystals == 0) {
            showAlert("No crystals to merge.", Alert.AlertType.INFORMATION);
            return;
        }

        if (singaSpace == 0) {
            showAlert("Your singa storage is full of singa.", Alert.AlertType.INFORMATION);
            return;
        }

        try {
            StringBuilder message = new StringBuilder("There is space to store: " + singaSpace + " singa.\n");
            message.append("Select an option:\n0. End merge\n");

            Iterator<?> it = carrier.iterator();
            int pos = 1;
            while (it.hasNext()) {
                message.append(pos++).append(". To merge ").append(it.next().toString()).append("\n");
            }

            Optional<String> result = showInputDialog(message.toString());
            if (result.isPresent()) {
                int option = Integer.parseInt(result.get());
                if (option == 0) {
                    return;
                } else if (option > 0 && option <= crystals) {
                    homeManager.mergeCrystal(option - 1);
                } else {
                    showAlert("Select a correct option.", Alert.AlertType.WARNING);
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Only numbers are allowed.", Alert.AlertType.ERROR);
        } catch (WizardTiredException | ContainerErrorException | ContainerEmptyException e) {
            throw new RuntimeException(e);
        }
    }



    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<String> showInputDialog(String message) {
        Alert inputDialog = new Alert(Alert.AlertType.CONFIRMATION);
        inputDialog.setTitle("Merge Crystals");
        inputDialog.setHeaderText(null);
        inputDialog.setContentText(message);

        Optional<ButtonType> result = inputDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return Optional.of("1");
        }
        return Optional.empty();
    }
}
