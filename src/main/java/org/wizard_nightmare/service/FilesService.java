package org.wizard_nightmare.service;

import org.wizard_nightmare.data.FilesDao;

public class FilesService {

    private final FilesDao dao;

    public FilesService() {
        dao = new FilesDao();
    }

    public boolean saveGame () {
        dao.saveDungeonContainerManager();
        return false;
    }


}
