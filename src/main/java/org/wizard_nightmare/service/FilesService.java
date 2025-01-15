package org.wizard_nightmare.service;

import org.wizard_nightmare.data.FilesDao;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.dungeon.Dungeon;

public class FilesService {

    private final FilesDao dao;

    public FilesService() {
        dao = new FilesDao();
    }

    public boolean saveDemiurge(Demiurge demiurge) {
        return dao.saveDemiurge(demiurge);
    }

    public Demiurge loadDemiurge() {
        return dao.loadDemiurge();
    }

    public boolean saveDungeon (Dungeon dungeon) {
        return dao.saveDungeon(dungeon);
    }

    public Dungeon loadDungeon() {
        return dao.loadDungeon();
    }

}
