package org.wizard_nightmare.game.dungeon;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Dungeon {
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
