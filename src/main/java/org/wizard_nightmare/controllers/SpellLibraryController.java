package org.wizard_nightmare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.wizard_nightmare.game.character.Wizard;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.dungeon.Home;
import org.wizard_nightmare.game.dungeon.HomeNotEnoughSingaException;
import org.wizard_nightmare.game.object.SingaCrystal;
import org.wizard_nightmare.game.object.Weapon;
import org.wizard_nightmare.game.objectContainer.Chest;
import org.wizard_nightmare.game.objectContainer.CrystalCarrier;
import org.wizard_nightmare.game.objectContainer.JewelryBag;
import org.wizard_nightmare.game.objectContainer.Wearables;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.wizard_nightmare.game.spell.*;
import org.wizard_nightmare.game.spellContainer.Library;
import org.wizard_nightmare.game.util.ValueOverMaxException;

import java.util.ArrayList;
import java.util.List;

public class SpellLibraryController {
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

    public void initialize() throws ContainerUnacceptedItemException, ContainerFullException, SpellUnknowableException {
        CrystalCarrier crystalCarrier = new CrystalCarrier(3);
        crystalCarrier.add(SingaCrystal.createCrystal(3));
        Wearables wearables = new Wearables(1, 1, 2);
        Wizard wizard = new Wizard("Merlin", 10, 10, 11, 10,
                wearables, crystalCarrier, new JewelryBag(2));
        wizard.addSpell(new FireAttack());
        wizard.addItem(new Weapon(1));

        home = new Home("Home", 1, 60, 100, new Chest(4), new Library());
        home.addItem(new Weapon(2));
        setHome(home);

        setWizard(wizard);
        try {
            String imagePath = getClass().getResource("/images/libreriaHechizos.jpeg").toExternalForm();
            anchorpane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }

        spellListLibrary.add(new AirAttack());
        spellListLibrary.add(new FireAttack());
        spellListLibrary.add(new ElectricAttack());

        populateSpellContainers();
    }

    private void populateSpellContainers() {
        for (Spell spell : spellListLibrary) {
            Button spellButton = new Button(spell.toString());
            addContextMenuToButton(spellButton, spell, hechizoContainer, true); // Hechizos en la biblioteca
            hechizoContainer.getChildren().add(spellButton);
        }

        for (Spell spell : spellListWizard) {
            Button spellButton = new Button(spell.toString());
            addContextMenuToButton(spellButton, spell, inventarioContainer, false); // Hechizos aprendidos
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
                    System.out.println("No tienes suficiente Singa o energía para aprender este hechizo.");
                }
            });
            menu.getItems().add(learnSpell);
        } else {
            upgradeSpell.setOnAction(e -> {
                if (canUpgradeSpell(spell)) {
                    try {
                        spell.improve(1);
                    } catch (ValueOverMaxException ex) {
                        System.out.println("Error al mejorar el hechizo: " + ex.getMessage());
                    }
                } else {
                    System.out.println("No tienes suficiente Singa o energía para mejorar este hechizo.");
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

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public void setHome(Home home) {
        this.home = home;
    }
}
