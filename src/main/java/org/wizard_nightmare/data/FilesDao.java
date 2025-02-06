package org.wizard_nightmare.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.wizard_nightmare.game.demiurge.Demiurge;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;


public class FilesDao {

    private final Gson gson;

    public FilesDao() {
        gson = new Gson();
    }

    public boolean saveDemiurge(Demiurge demiurge) {
        try (FileWriter fileWriter = new FileWriter("data/demiurge.json")) {
            gson.toJson(demiurge, fileWriter);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Demiurge loadDemiurge() {
        Type demiurge = new TypeToken<Demiurge>() {
        }.getType();
        try (FileReader fileReader = new FileReader("data/demiurge.json")) {
            return gson.fromJson(fileReader, demiurge);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
