package org.wizard_nightmare.service;

import org.wizard_nightmare.data.FilesDao;
import org.wizard_nightmare.game.demiurge.Demiurge;

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

}
