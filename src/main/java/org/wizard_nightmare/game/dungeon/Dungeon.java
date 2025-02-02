package org.wizard_nightmare.game.dungeon;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@XmlRootElement(name = "dungeon")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dungeon {
    @XmlElement(name = "room")
    private List<Room> rooms;

    public Dungeon() {
        rooms = new ArrayList<>();
    }

    public Dungeon(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) { rooms.add(room); }

    public Room getRoom(int index) { return rooms.get(index); }

    public Iterator<Room> iterator() { return rooms.iterator(); }

    public void generateCrystals(int maxElements, int maxCapacity) {
        for (Room r : rooms)
            r.generateCrystals(maxElements, maxCapacity);
    }

    public Room getSite(int ID){
        for(Room room: rooms){
            if(room.getId() == ID)
                return  room;
        }
        return null;
    }

}
