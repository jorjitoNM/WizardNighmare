package org.wizard_nightmare.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;


public class FilesDao {

    private final Gson gson;

    public FilesDao() {
        gson = new Gson();
    }

    public boolean saveDemiurgeContainerManager(DemiurgeContainerManager containerManager) {
        try (FileWriter fileWriter = new FileWriter("DemiurgeContainerManager.json")) {
            gson.toJson(containerManager,fileWriter);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public DemiurgeContainerManager loadDemiurgeContainerManager() {
        Type dungeonContainerManagerType = new TypeToken<DemiurgeContainerManager> () {}.getType();
        try (FileReader fileReader = new FileReader("DemiurgeContainerManager.json")) {

        } catch (IOException e) {
            return null;
        }
    }
}
