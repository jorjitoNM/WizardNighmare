package org.wizard_nightmare.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.character.Creature;
import org.wizard_nightmare.game.character.Wizard;
import org.wizard_nightmare.game.conditions.KillAllCreaturesCondition;
import org.wizard_nightmare.game.conditions.VisitAllRoomsCondition;
import org.wizard_nightmare.game.demiurge.Demiurge;
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


public class StartController {

    @FXML
    private AnchorPane screen;
    private final int INITIAL_COMFORT = 1;
    private final int INITIAL_SINGA = 10;
    private final int INITIAL_SINGA_CAPACITY = 50;
    private final int INITIAL_CHEST_CAPACITY = 4;

    //Wizard
    private final int INITIAL_LIFE = 10;
    private final int INITIAL_LIFE_MAX = 10;
    private final int INITIAL_ENERGY = 10;
    private final int INITIAL_ENERGY_MAX = 10;
    private final int INITIAL_CRYSTAL_CARRIER_CAPACITY = 3;
    private final int INITIAL_CRYSTAL_BAG_CAPACITY = 2;
    private final int INITIAL_MAX_WEAPONS = 1;
    private final int INITIAL_MAX_NECKLACES = 1;
    private final int INITIAL_MAX_RINGS = 2;

    private final FilesService service;
    private Demiurge demiurge;
    private Dungeon dungeon;

    public StartController() {
        service = new FilesService();
    }

    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/pantalla_inicio.png").toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    public void saveGame () {
        service.saveDungeon(dungeon);
        service.saveDemiurge(demiurge);
    }

    private void loadDemiurge() throws ContainerUnacceptedItemException, ContainerFullException, SpellUnknowableException {
        demiurge = new Demiurge();
        /*-----Wizard-----*/
        System.out.println("\tAdding WIZARD to the system.");
        CrystalCarrier crystalCarrier = new CrystalCarrier(INITIAL_CRYSTAL_CARRIER_CAPACITY);
        crystalCarrier.add(SingaCrystal.createCrystal(10));
        Wearables wearables = new Wearables(INITIAL_MAX_WEAPONS, INITIAL_MAX_NECKLACES, INITIAL_MAX_RINGS);
        Wizard wizard = new Wizard("Merlin", INITIAL_LIFE, INITIAL_LIFE_MAX, INITIAL_ENERGY, INITIAL_ENERGY_MAX,
                wearables, crystalCarrier, new JewelryBag(INITIAL_CRYSTAL_BAG_CAPACITY));
        wizard.addSpell(new FireAttack());
        wizard.addItem(new Weapon(1));
        demiurge.setWizard(wizard);
        /*-----Home-----*/
        System.out.println("\tCreating HOME");
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
            System.out.println("\tCreating DUNGEON");
            dungeon = new Dungeon();
            Room room;
            int id = 0;
            System.out.println("\tCreating ROOMS");
            room = new Room(id++, "Common room connected with HOME", new RoomSet(1));
            room.addItem(Necklace.createNecklace(Domain.LIFE, 5));
            dungeon.addRoom(room);
            room = new Room(id++, "Common room in the middle", new RoomSet(0));
            Creature creature = new Creature("Big Monster", 5, 1, Domain.ELECTRICITY);
            creature.addSpell(new ElectricAttack());
            room.setCreature(creature);
            dungeon.addRoom(room);
            room = new Room(id++, "Common room and Exit", new RoomSet(0), true);
            dungeon.addRoom(room);
            System.out.println("\t\tTotal rooms in dungeon: " + id);
            demiurge.setDungeon(dungeon);
            /*-----End Conditions-----*/
            System.out.println("\tAdding END conditions.");
            demiurge.addCondition(new VisitAllRoomsCondition(dungeon));
            demiurge.addCondition(new KillAllCreaturesCondition(dungeon));
        } catch (ContainerUnacceptedItemException | ContainerFullException | SpellUnknowableException |
                 ItemCreationErrorException ignored) {

        }
    }

    public void loadSavedGame() {
        demiurge = service.loadDemiurge();
        System.out.println("\tCreating HOME");
        System.out.println("\tCreating DUNGEON");
        System.out.println("\tCreating ROOMS");
        System.out.println("\tCreating DOORS");
        System.out.println("\t\tTotal rooms in dungeon: " + dungeon.getRooms().size());
        System.out.println("\tAdding WIZARD to the system.");
        System.out.println("\tAdding END conditions.");
    }

    public void loadGameFromCustomMap() {
        try {
            loadDemiurge();
            dungeon = service.loadDungeon();
            demiurge.setDungeon(dungeon);
        } catch (ContainerUnacceptedItemException | SpellUnknowableException | ContainerFullException e) {

        }
    }

    public void exit() {
        Platform.exit();
    }
}
