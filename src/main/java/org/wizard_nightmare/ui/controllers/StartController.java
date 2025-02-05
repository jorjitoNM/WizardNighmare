package org.wizard_nightmare.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.character.Creature;
import org.wizard_nightmare.game.character.Wizard;
import org.wizard_nightmare.game.conditions.KillAllCreaturesCondition;
import org.wizard_nightmare.game.conditions.VisitAllRoomsCondition;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;
import org.wizard_nightmare.game.demiurge.DemiurgeDungeonManager;
import org.wizard_nightmare.game.demiurge.DemiurgeHomeManager;
import org.wizard_nightmare.game.dungeon.Door;
import org.wizard_nightmare.game.dungeon.Dungeon;
import org.wizard_nightmare.game.dungeon.Home;
import org.wizard_nightmare.game.dungeon.Room;
import org.wizard_nightmare.game.object.ItemCreationErrorException;
import org.wizard_nightmare.game.object.Necklace;
import org.wizard_nightmare.game.object.SingaCrystal;
import org.wizard_nightmare.game.object.Weapon;
import org.wizard_nightmare.game.objectContainer.*;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.wizard_nightmare.game.spell.AirAttack;
import org.wizard_nightmare.game.spell.ElectricAttack;
import org.wizard_nightmare.game.spell.FireAttack;
import org.wizard_nightmare.game.spell.SpellUnknowableException;
import org.wizard_nightmare.game.spellContainer.Knowledge;
import org.wizard_nightmare.game.spellContainer.Library;
import org.wizard_nightmare.service.FilesService;
import org.wizard_nightmare.ui.common.Constants;


public class StartController implements DemiurgeConsumer {

    @FXML
    private Label infoLabel;
    @FXML
    private AnchorPane screen;

    private final int INITIAL_COMFORT = 1;
    private final int INITIAL_SINGA = 10;
    private final int INITIAL_SINGA_CAPACITY = 50;
    private final int INITIAL_CHEST_CAPACITY = 4;

    private final int INITIAL_LIFE = 5;
    private final int INITIAL_LIFE_MAX = 10;
    private final int INITIAL_ENERGY = 5;
    private final int INITIAL_ENERGY_MAX = 10;
    private final int INITIAL_CRYSTAL_CARRIER_CAPACITY = 3;
    private final int INITIAL_CRYSTAL_BAG_CAPACITY = 2;
    private final int INITIAL_MAX_WEAPONS = 1;
    private final int INITIAL_MAX_NECKLACES = 1;
    private final int INITIAL_MAX_RINGS = 2;

    private final FilesService service;
    private Demiurge demiurge;

    public StartController() {
        service = new FilesService();
    }

    public void initialize() {
        try {

            String imagePath = getClass().getResource(Constants.START_IMAGE).toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    public void saveGame() {
        service.saveDemiurge(demiurge);
    }

    private void loadDemiurge() throws ContainerUnacceptedItemException, ContainerFullException, SpellUnknowableException {
        demiurge = new Demiurge();
        /*-----Wizard-----*/
        infoLabel.setText("\tAdding WIZARD to the system.");
        CrystalCarrier crystalCarrier = new CrystalCarrier(INITIAL_CRYSTAL_CARRIER_CAPACITY);
        crystalCarrier.add(SingaCrystal.createCrystal(10));
        Wearables wearables = new Wearables(INITIAL_MAX_WEAPONS, INITIAL_MAX_NECKLACES, INITIAL_MAX_RINGS);
        Wizard wizard = new Wizard("Merlin", INITIAL_LIFE, INITIAL_LIFE_MAX, INITIAL_ENERGY, INITIAL_ENERGY_MAX,
                wearables, crystalCarrier, new JewelryBag(INITIAL_CRYSTAL_BAG_CAPACITY));
        wizard.addSpell(new FireAttack());
        wizard.addItem(new Weapon(1));
        demiurge.setWizard(wizard);
        /*-----Home-----*/
        infoLabel.setText("\tCreating HOME");
        Knowledge library = new Library();
        library.add(new ElectricAttack());
        library.add(new FireAttack());
        library.add(new AirAttack());
        Home home = new Home("Home", INITIAL_COMFORT, INITIAL_SINGA, INITIAL_SINGA_CAPACITY, new Chest(INITIAL_CHEST_CAPACITY), library);
        home.addItem(new Weapon(2));
        demiurge.setHome(home);
    }

    public void loadGame() {
        try {
            loadDemiurge();
            /*-----Dungeon-----*/
            infoLabel.setText("Creating DUNGEON");
            demiurge.setDungeon(new Dungeon());
            Room room;
            int id = 0;
            infoLabel.setText("\tCreating ROOMS");
            room = new Room(id++, "Common room connected with HOME", new RoomSet(1));
            room.addItem(Necklace.createNecklace(Domain.LIFE, 5));
            demiurge.getDungeon().addRoom(room);
            room = new Room(id++, "Common room in the middle", new RoomSet(0));
            Creature creature = new Creature("Big Monster", 5, 1, Domain.ELECTRICITY);
            creature.addSpell(new ElectricAttack());
            room.setCreature(creature);
            demiurge.getDungeon().addRoom(room);
            room = new Room(id++, "Common room and Exit", new RoomSet(0), true);
            demiurge.getDungeon().addRoom(room);
            infoLabel.setText("Total rooms in dungeon: " + id);
            infoLabel.setText("Creating DOORS");
            new Door(demiurge.getHome(),  demiurge.getDungeon().getRoom(0));
            new Door( demiurge.getDungeon().getRoom(0),  demiurge.getDungeon().getRoom(1));
            new Door( demiurge.getDungeon().getRoom(1),  demiurge.getDungeon().getRoom(2));
            /*-----End Conditions-----*/
            infoLabel.setText("Adding END conditions.");
            demiurge.addCondition(new VisitAllRoomsCondition( demiurge.getDungeon()));
            demiurge.addCondition(new KillAllCreaturesCondition( demiurge.getDungeon()));
            demiurge.setContainerManager(new DemiurgeContainerManager(demiurge.getWizard().getWearables(), demiurge.getWizard().getJewelryBag(), demiurge.getHome().getContainer()));
            demiurge.setHomeManager(new DemiurgeHomeManager(demiurge.getDungeonConfiguration(), demiurge.getWizard(), demiurge.getHome(), demiurge.getContainerManager()));
            demiurge.setDungeonManager(new DemiurgeDungeonManager(demiurge.getDungeonConfiguration(), demiurge.getWizard(), demiurge.getHome(), demiurge.getContainerManager(), demiurge.getEndChecker()));
            demiurge.nextDay();
        } catch (ContainerUnacceptedItemException | ContainerFullException | SpellUnknowableException |
                 ItemCreationErrorException ignored) {
        }
        App.cambiarPantalla(demiurge, "/screens/home.fxml");
    }

    public void loadSavedGame() {
        demiurge = service.loadDemiurge();
        infoLabel.setText("Creating HOME");
        infoLabel.setText("Creating DUNGEON");
        infoLabel.setText("Creating ROOMS");
        infoLabel.setText("Creating DOORS");
        infoLabel.setText("Total rooms in dungeon: " +  demiurge.getDungeon().getRooms().size());
        infoLabel.setText("Adding WIZARD to the system.");
        infoLabel.setText("Adding END conditions.");
    }

    public void exit() {
        saveGame();
        Platform.exit();
    }

    @Override
    public void loadScreenData(Demiurge demiurge) {
        this.demiurge = demiurge;
    }
}
