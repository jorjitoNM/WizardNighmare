package org.wizard_nightmare.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;
import org.wizard_nightmare.game.demiurge.DemiurgeHomeManager;

public class HomeController implements DemiurgeConsumer {

    @FXML
    private Label nivel;
    @FXML
    private Button inventory;
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

    @FXML
    private Label infoLabel;

    @FXML
    private Label vidaLabel;
    @FXML
    private Label energiaLabel;

    @FXML
    private VBox inventoryContainer;

    private Demiurge demiurge;

    private boolean isInventoryVisible = false;

    List<Item> items = new ArrayList<>();

    @Override
    public void setDemiurge(Demiurge demiurge) {
        this.demiurge = demiurge;
        loadHome();
    }

    private void loadHome() {
        double vidaActual = demiurge.getWizard().getLife();
        double vidaMaxima = demiurge.getWizard().getLifeMax();
        double energiaActual = demiurge.getWizard().getEnergy();
        double energiaMaxima = demiurge.getWizard().getEnergyMax();

        vidaProgress.setProgress(vidaActual / vidaMaxima);
        energiaProgress.setProgress(energiaActual / energiaMaxima);
        vidaLabel.setText(String.format("%.1f / %.1f", vidaActual, vidaMaxima));
        energiaLabel.setText(String.format("%.1f / %.1f", energiaActual, energiaMaxima));
        screen.setOnKeyPressed(this::handleArrow);

        items.addAll(demiurge.getWizard().getWearables().getItems());
        items.addAll(demiurge.getWizard().getCrystalCarrier().getItems());
        items.addAll(demiurge.getWizard().getJewelryBag().getItems());

        nivel.setText(String.valueOf(demiurge.getHome().getComfort()));
    }


    public void initialize() {
        screen.setOnKeyPressed(this::handleArrow);
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
        } else if ((source == hechizos1) || (source == hechizos2)) {
            App.cambiarPantalla(demiurge, "/screens/spell_library.fxml");
        } else if (source == cofre) {
            App.cambiarPantalla(demiurge, "/screens/chest.fxml");
        } else if (source == singa) {
            App.cambiarPantalla(demiurge, "/screens/singa_storage.fxml");
        } else if (source == inventory) {
            toggleInventory();
        }
    }

    private void toggleInventory() {
        if (!isInventoryVisible) {

            inventoryContainer.getChildren().clear();
            for (Item item : items) {
                Button itemButton = new Button(item.toString());
                itemButton.setOnAction(e -> useItem(item));
                inventoryContainer.getChildren().add(itemButton);
            }
            inventoryContainer.setVisible(true);
        } else {
            inventoryContainer.setVisible(false);
        }
        isInventoryVisible = !isInventoryVisible;
    }

    private void useItem(Item item) {
        System.out.println("Usando: " + item.toString());
    }

    private void handleArrow (KeyEvent event) {
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
        if (selection >= 0 && selection <= demiurge.getDungeonManager().getNumberOfDoors()) {
            try {
                demiurge.getDungeonManager().openDoor(selection);
            } catch (WizardTiredException e) {
                showInfoLabel("Good night... zZzZzZzz");
                App.cambiarPantalla(demiurge, Constants.HOME);
            } catch (GoHomekException e) {
                App.cambiarPantalla(demiurge,Constants.HOME);
            } catch (EndGameException e) {
                App.cambiarPantalla(demiurge,Constants.FINISH);
            }
        }
        else
            showInfoLabel("There is not a room there");
    }

    private void showInfoLabel (String message){
        infoLabel.setText(message);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), infoLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setDelay(Duration.seconds(2));
        fadeTransition.play();
    }
}
