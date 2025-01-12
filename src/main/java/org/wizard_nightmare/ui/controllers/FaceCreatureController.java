package org.wizard_nightmare.ui.controllers;

import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.wizard_nightmare.game.character.Wizard;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;
import org.wizard_nightmare.game.character.exceptions.WizardNotEnoughEnergyException;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;
import org.wizard_nightmare.game.demiurge.DemiurgeDungeonManager;
import org.wizard_nightmare.game.demiurge.DemiurgeEndChecker;
import org.wizard_nightmare.game.demiurge.DungeonConfiguration;
import org.wizard_nightmare.game.dungeon.Room;
import org.wizard_nightmare.game.dungeon.Site;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FaceCreatureController {

    @FXML
    private AnchorPane screen;
    @FXML
    private AnchorPane creature;
    @FXML
    private AnchorPane wizardFX;
    @FXML
    private ProgressBar creatureHealth;
    @FXML
    private ProgressBar wizardHealth;
    @FXML
    private GridPane weapons;
    private DemiurgeDungeonManager dungeonManager;
    private DungeonConfiguration dc;
    private Wizard wizard;
    private Site site;
    private DemiurgeContainerManager dcm;
    private DemiurgeEndChecker checker;
    private DoubleProperty progressValueCreature;
    private DoubleProperty progressValueWizard;

    public FaceCreatureController(DungeonConfiguration dc, Wizard wizard, Site site, DemiurgeContainerManager dcm, DemiurgeEndChecker checker) {
        dungeonManager = new DemiurgeDungeonManager(dc, wizard, site, dcm, checker);
        setCharactersLifebar();
        Room currentRoom = (Room) site;
        progressValueCreature = new SimpleDoubleProperty(currentRoom.getCreature().getLife());
        progressValueWizard = new SimpleDoubleProperty(wizard.getLife());
        try (InputStream input = new FileInputStream("assets/images/habitacion_lucha.png");) {
            Image image = new Image(input);
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background background = new Background(backgroundimage);
            screen.setBackground(background);
        } catch (IOException e) {

        }
    }

    private void setCharactersLifebar() {
        creatureHealth.progressProperty().bind(progressValueCreature);
        creatureHealth.progressProperty().bind(progressValueWizard);
    }

    public void exitRoom() throws CharacterKilledException {
        if (dungeonManager.canRunAway()) //desplazar al mago a su casa o a la habitacion anterior
            return;
        else{
            dungeonManager.creatureAttack();
            progressValueWizard.set(wizard.getLife());
            creatureAttack();
        }
    }

    private void creatureAttack() {
        creature.setVisible(false);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.play();
        pause.setOnFinished(event -> {
            creature.setVisible(true);
        });
    }

    public void useItem(MouseEvent mouseEvent) {
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (clickedNode != null && clickedNode != weapons) {
            Integer column = GridPane.getColumnIndex(clickedNode);
            try {
                if (dungeonManager.priority()) {
                    System.out.println("Wizard has priority");
                    if (dungeonManager.wizardAttack(wizard.getAttack(column)))
                        wizardAttack();
                    if (dungeonManager.creatureAttack())
                        creatureAttack();
                } else {
                    System.out.println("Creature has priority");
                    if (dungeonManager.creatureAttack())
                        creatureAttack();
                    if (dungeonManager.wizardAttack(wizard.getAttack(column)))
                        wizardAttack();
                }
            } catch (WizardTiredException e) {
                throw new RuntimeException(e);
            } catch (WizardNotEnoughEnergyException e) {
                throw new RuntimeException(e);
            } catch (CharacterKilledException e) {
                throw new RuntimeException(e);
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
}
