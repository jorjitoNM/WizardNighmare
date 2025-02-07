package org.wizard_nightmare.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.Character;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;
import org.wizard_nightmare.game.character.exceptions.WizardNotEnoughEnergyException;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
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
    private Label infoLabel;
    @FXML
    private ImageView weapon1;
    @FXML
    private ImageView weapon2;
    @FXML
    private ImageView weapon3;

    private Demiurge demiurge;

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

    public void exitRoom() {
        if (demiurge.getDungeonManager().canRunAway())
            App.cambiarPantalla(demiurge, Constants.HOME);
        else {
            try {
                demiurge.getDungeonManager().creatureAttack();
            } catch (CharacterKilledException e) {
                App.cambiarPantalla(demiurge, Constants.FINISH);
            }
            creatureAttack();
        }
    }

    private void creatureAttack() {
        creature.setVisible(false);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.play();
        pause.setOnFinished(event -> creature.setVisible(true));
    }


    public void useItem(MouseEvent event) {
        int column = -1;
        if (event.getSource() == weapon2) {
            column = 1;
        } else if (event.getSource() == weapon3) {
            column = 2;
        } else if (event.getSource() == weapon1) {
            column = 0;
        }
        if (demiurge.getWizard().getNumberOfAttacks() < 3 && column == 2)
            showInfoLabel("Attack not available");
        else {
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
                updateCharactersLifebar();
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
        pause.setOnFinished(event -> wizardFX.setVisible(true));
    }

    @Override
    public void loadScreenData(Demiurge demiurge) {
        this.demiurge = demiurge;
        updateCharactersLifebar();
        try {
            Image weapon1Image = new Image(getClass().getResourceAsStream(Constants.WEAPON1));
            weapon1.setImage(weapon1Image);
            Image weapon2Image = new Image(getClass().getResourceAsStream(Constants.WEAPON2));
            weapon2.setImage(weapon2Image);
            Image weapon3Image = new Image(getClass().getResourceAsStream(Constants.WEAPON3));
            weapon3.setImage(weapon3Image);
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    private void updateCharactersLifebar() {
        updateLifeBar(creatureHealth, demiurge.getWizard());
        updateLifeBar(wizardHealth, demiurge.getDungeonManager().getCreature());
    }

    private void updateLifeBar(ProgressBar bar, Character character) {
        if (character.getLifeMax() == 0) {
            bar.setProgress(0);
            return;
        }
        double progress = (double) character.getLife() / character.getLifeMax();
        bar.setProgress(progress);
    }

    private void showInfoLabel(String message) {
        infoLabel.setText(message);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), infoLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }
}
