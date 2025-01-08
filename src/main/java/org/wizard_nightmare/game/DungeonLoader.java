package org.wizard_nightmare.game;


import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.DungeonConfiguration;

public interface DungeonLoader {

    public void load(Demiurge demiurge, DungeonConfiguration dungeonConfiguration);

}
