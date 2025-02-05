package org.wizard_nightmare.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;
import org.wizard_nightmare.game.character.exceptions.WizardNotEnoughEnergyException;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.dungeon.Room;
import org.wizard_nightmare.ui.common.Constants;

public class FaceCreatureController implements DemiurgeConsumer {

    @FXML
    private AnchorPane screen;
    @FXML
    private ImageView creature;
    @FXML
    private ImageView wizardFX;
    @FXML
    private ProgressBar creatureHealth;
    @FXML
    private ProgressBar wizardHealth;
    @FXML
    private GridPane weapons;
    @FXML
    private Label infoLabel;
    private Demiurge demiurge;
    private DoubleProperty progressValueCreature;
    private DoubleProperty progressValueWizard;

    public void initialize() {
        try {
            String imagePath = getClass().getResource(Constants.FACE_CREATURE_IMAGE).toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
            Image creatureImage = new Image(getClass().getResourceAsStream(Constants.CREATURE_IMAGE));
            creature.setImage(creatureImage);
            Image wizardImage = new Image(getClass().getResourceAsStream(Constants.WIZARD_IMAGE));
            wizardFX.setImage(wizardImage);
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    private void setCharactersLifebar() {
        creatureHealth.progressProperty().bind(progressValueCreature);
        wizardHealth.progressProperty().bind(progressValueWizard);
    }

    public void exitRoom() {
        if (demiurge.getDungeonManager().canRunAway())
            App.cambiarPantalla(demiurge,Constants.HOME);
        else{
            try {
                demiurge.getDungeonManager().creatureAttack();
            } catch (CharacterKilledException e) {
                App.cambiarPantalla(demiurge, Constants.FINISH);
            }
            progressValueWizard.set(demiurge.getWizard().getLife());
            creatureAttack();
        }
    }

    private void creatureAttack() {
        creature.setVisible(false);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.play();
        pause.setOnFinished(event -> creature.setVisible(true));
    }

    public void useItem(MouseEvent mouseEvent) {
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (clickedNode != null && clickedNode != weapons) {
            Integer column = GridPane.getColumnIndex(clickedNode);
            try {
                if (demiurge.getDungeonManager().priority()) {
                    showInfoLabel("Wizard has priority");
                    if (demiurge.getDungeonManager().wizardAttack(demiurge.getWizard().getAttack(column)))
                        wizardAttack();
                    if (demiurge.getDungeonManager().creatureAttack())
                        creatureAttack();
                } else {
                    showInfoLabel("Creature has priority");
                    if (demiurge.getDungeonManager().creatureAttack())
                        creatureAttack();
                    if (demiurge.getDungeonManager().wizardAttack(demiurge.getWizard().getAttack(column)))
                        wizardAttack();
                }
            } catch (WizardTiredException e) {
                App.cambiarPantalla(demiurge, Constants.HOME);
            } catch (WizardNotEnoughEnergyException e) {
                showInfoLabel("You dont have enough energy!!");
            } catch (CharacterKilledException e) {
                App.cambiarPantalla(demiurge, Constants.FINISH);
            }
        }
    }

    private void wizardAttack() {
        wizardFX.setVisible(false);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.play();
        pause.setOnFinished(event -> {
            wizardFX.setVisible(true);
        });
    }

    @Override
    public void loadScreenData(Demiurge demiurge) {
        this.demiurge = demiurge;
        setCharactersLifebar();
        Room r = (Room) (demiurge.getDungeonManager().getSite());
        progressValueCreature = new SimpleDoubleProperty(r.getCreature().getLife());
        progressValueWizard = new SimpleDoubleProperty(demiurge.getWizard().getLife());
    }

    private void showInfoLabel (String message){
        infoLabel.setText(message);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), infoLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }
}
