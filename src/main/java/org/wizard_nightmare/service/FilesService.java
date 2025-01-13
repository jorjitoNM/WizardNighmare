package org.wizard_nightmare.service;

import org.wizard_nightmare.data.FilesDao;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;
import org.wizard_nightmare.game.demiurge.DemiurgeEndChecker;

public class FilesService {

    private final FilesDao dao;

    public FilesService() {
        dao = new FilesDao();
    }

    public boolean saveGame (DemiurgeContainerManager containerManager, DemiurgeEndChecker endChecker, Demiurge demiurge) {
        return dao.saveDemiurgeContainerManager(containerManager) && dao.saveDemiurgeEndChecker(endChecker) && dao.saveDemiurge(demiurge);
    }

    public DemiurgeContainerManager loadDemiurgeContainerManager() {
        return dao.loadDemiurgeContainerManager();
    }

    public DemiurgeEndChecker loadDemiurgeEndChecker() {
        return dao.loadDemiurgeEndChecker();
    }

    public Demiurge loadDemiurge () {
        return dao.loadDemiurge();
    }

}
