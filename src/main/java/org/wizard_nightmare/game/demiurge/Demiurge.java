package org.wizard_nightmare.game.demiurge;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.wizard_nightmare.game.DungeonLoader;
import org.wizard_nightmare.game.character.Wizard;
import org.wizard_nightmare.game.conditions.Condition;
import org.wizard_nightmare.game.dungeon.Dungeon;
import org.wizard_nightmare.game.dungeon.Home;

@Getter
@Setter
@Data
public class Demiurge {
    private int day;
    private Dungeon dungeon;
    private Home home;
    private Wizard wizard;

    DungeonConfiguration dungeonConfiguration;
    DemiurgeHomeManager homeManager;
    DemiurgeContainerManager containerManager;
    DemiurgeDungeonManager dungeonManager;
    DemiurgeEndChecker endChecker;

    public Demiurge() {
        dungeonConfiguration = new DungeonConfiguration();
        endChecker = new DemiurgeEndChecker();

        dungeonConfiguration.put("basicEnergyConsumption", 1);

        dungeonConfiguration.put("comfortModifierForEnergy", 10);

        dungeonConfiguration.put("basicIncrease", 1);
        dungeonConfiguration.put("stoneIncrease", 50);

        dungeonConfiguration.put("basicUpgradeCost", 10);
        dungeonConfiguration.put("comfortUpgradeCost", 100);

        dungeonConfiguration.put("singaPerLifePointCost", 3);

        //Crystal regeneration
        dungeonConfiguration.put("crystalsPerDay", 3);
        dungeonConfiguration.put("singaPerCrystal", 10);

        //Creature encounters
        dungeonConfiguration.put("minimumForRunAway", 30);
        dungeonConfiguration.put("fightSuccessHigh", 20);
        dungeonConfiguration.put("fightSuccessMedium", 50);
        dungeonConfiguration.put("fightSuccessLow", 80);
    }

    public void addCondition(Condition condition){ endChecker.addCondition(condition); }

    public void loadEnvironment(DungeonLoader dungeonLoader) {
        dungeonLoader.load(this, dungeonConfiguration);
        containerManager = new DemiurgeContainerManager(wizard.getWearables(), wizard.getJewelryBag(), home.getContainer());
        homeManager =  new DemiurgeHomeManager(dungeonConfiguration, wizard, home, containerManager);
        dungeonManager = new DemiurgeDungeonManager(dungeonConfiguration, wizard, home, containerManager, endChecker);
        nextDay();
    }

    public void nextDay() {
        wizard.sleep(home.getComfort() * dungeonConfiguration.get("comfortModifierForEnergy"));
        dungeon.generateCrystals(dungeonConfiguration.get("crystalsPerDay"), dungeonConfiguration.get("singaPerCrystal"));
        day++;
    }

}
