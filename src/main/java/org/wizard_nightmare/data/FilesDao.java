package org.wizard_nightmare.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.demiurge.exceptions.ExitException;
import org.wizard_nightmare.game.dungeon.Dungeon;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;


public class FilesDao {

    private final Gson gson;

    public FilesDao() {
        gson = new Gson();
    }

    public boolean saveDemiurge(Demiurge demiurge) {
        try (FileWriter fileWriter = new FileWriter("data/demiurge.json")) {
            gson.toJson(demiurge,fileWriter);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Demiurge loadDemiurge () {
        Type demiurge = new TypeToken<Demiurge> () {}.getType();
        try (FileReader fileReader = new FileReader("data/demiurge.json")) {
            return gson.fromJson(fileReader,demiurge);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean saveDungeon(Dungeon dungeon) {
        try {
            JAXBContext context = JAXBContext.newInstance(Dungeon.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(dungeon, Files.newOutputStream(Path.of("data/dungeon.xml")));
            return true;
        } catch (JAXBException | IOException e) {
            throw new RuntimeException();
        }
    }

    public Dungeon loadDungeon() {
        try {
            JAXBContext context = JAXBContext.newInstance(Dungeon.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Dungeon) unmarshaller.unmarshal(Files.newInputStream(Path.of("data/dungeon.xml")));
        } catch (JAXBException | IOException e) {
            throw new RuntimeException();
        }
    }
}
