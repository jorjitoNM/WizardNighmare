package org.wizard_nightmare.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.character.Wizard;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.dungeon.Home;
import org.wizard_nightmare.game.dungeon.HomeNotEnoughSingaException;
import org.wizard_nightmare.game.spell.Spell;
import org.wizard_nightmare.game.util.ValueOverMaxException;

import java.util.ArrayList;
import java.util.List;

public class SpellLibraryController implements DemiurgeConsumer {
    @FXML
    private Label labelHechizosError;

    @FXML
    private HBox inventarioContainer;
    @FXML
    private HBox hechizoContainer;
    @FXML
    private AnchorPane anchorpane;

    private List<Spell> spellListLibrary = new ArrayList<>();
    private List<Spell> spellListWizard = new ArrayList<>();
    private Wizard wizard;
    private Home home;
    private Demiurge demiurge;

    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/libreriaHechizos.jpeg").toExternalForm();
            anchorpane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    private void loadSpells() {
        home = demiurge.getHome();
        wizard = demiurge.getWizard();
        spellListLibrary.addAll(home.getLibrary().getSpells());
        populateSpellContainers();
    }

    private void populateSpellContainers() {
        for (Spell spell : spellListLibrary) {
            Button spellButton = new Button(spell.toString());
            addContextMenuToButton(spellButton, spell, hechizoContainer, true);
            hechizoContainer.getChildren().add(spellButton);
        }

        for (Spell spell : spellListWizard) {
            Button spellButton = new Button(spell.toString());
            addContextMenuToButton(spellButton, spell, inventarioContainer, false);
            inventarioContainer.getChildren().add(spellButton);
        }
    }

    private void addContextMenuToButton(Button button, Spell spell, HBox container, boolean isInLibrary) {
        ContextMenu menu = new ContextMenu();
        MenuItem learnSpell = new MenuItem("Learn Spell");
        MenuItem upgradeSpell = new MenuItem("Upgrade Spell");

        if (isInLibrary) {
            learnSpell.setOnAction(e -> {
                if (canLearnSpell()) {
                    spellListWizard.add(spell);
                    spellListLibrary.remove(spell);
                    container.getChildren().remove(button);
                    try {
                        wizard.drainEnergy(10);
                    } catch (WizardTiredException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        home.useSinga(51);
                    } catch (HomeNotEnoughSingaException ex) {
                        throw new RuntimeException(ex);
                    }
                    Button learnedSpellButton = new Button(spell.toString());
                    addContextMenuToButton(learnedSpellButton, spell, inventarioContainer, false);
                    inventarioContainer.getChildren().add(learnedSpellButton);
                } else {
                    labelHechizosError.setVisible(true);
                    labelHechizosError.setText("You do not have enough Singa or energy to learn this spell");
                }
            });
            menu.getItems().add(learnSpell);
        } else {
            upgradeSpell.setOnAction(e -> {
                if (canUpgradeSpell(spell)) {
                    try {
                        spell.improve(1);
                    } catch (ValueOverMaxException ex) {
                        labelHechizosError.setVisible(true);
                        labelHechizosError.setText("Error upgrading spell: "+ex.getMessage());
                    }
                } else {
                    labelHechizosError.setVisible(true);
                    labelHechizosError.setText("You do not have enough Singa or energy to upgrade");
                }
            });
            menu.getItems().add(upgradeSpell);
        }

        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                menu.show(button, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private boolean canLearnSpell() {
        return wizard.hasEnoughEnergy(wizard.getEnergy()) && home.getSinga() >= 51;
    }

    private boolean canUpgradeSpell(Spell spell) {
        int spellLevel = spell.getLevel();
        int costSinga = 10 * spellLevel;
        int costEnergy = 5;

        return wizard.hasEnoughEnergy(costEnergy) && costSinga <= home.getSinga();
    }

    public void returnBack(ActionEvent actionEvent) {
        App.cambiarPantalla(demiurge, "/screens/home.fxml");
    }

    @Override
    public void setDemiurge(Demiurge demiurge) {
        this.demiurge = demiurge;
        loadSpells();
    }
}