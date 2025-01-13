package org.wizard_nightmare.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.DemiurgeContainerManager;
import org.wizard_nightmare.game.demiurge.DemiurgeEndChecker;

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
        try (FileWriter fileWriter = new FileWriter("data/DemiurgeContainerManager.json")) {
            gson.toJson(containerManager,fileWriter);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public DemiurgeContainerManager loadDemiurgeContainerManager() {
        Type duemiurgeContainerManagerType = new TypeToken<DemiurgeContainerManager> () {}.getType();
        try (FileReader fileReader = new FileReader("data/DemiurgeContainerManager.json")) {
            return gson.fromJson(fileReader,duemiurgeContainerManagerType);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean saveDemiurgeEndChecker(DemiurgeEndChecker endChecker) {
        try (FileWriter fileWriter = new FileWriter("data/DemiurgeEndChecker.json")) {
            gson.toJson(endChecker,fileWriter);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public DemiurgeEndChecker loadDemiurgeEndChecker () {
        Type demiurgeEndCheckerType = new TypeToken<DemiurgeEndChecker> () {}.getType();
        try (FileReader fileReader = new FileReader("data/DemiurgeEndChecker.json")) {
            return gson.fromJson(fileReader,demiurgeEndCheckerType);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean saveDemiurge(Demiurge demiurge) {
        try (FileWriter fileWriter = new FileWriter("data/Demiurge.json")) {
            gson.toJson(demiurge,fileWriter);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Demiurge loadDemiurge () {
        Type demiurge = new TypeToken<Demiurge> () {}.getType();
        try (FileReader fileReader = new FileReader("data/Demiurge.json")) {
            return gson.fromJson(fileReader,demiurge);
        } catch (IOException e) {
            return null;
        }
    }
}
