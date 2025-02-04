package org.wizard_nightmare.game.dungeon;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.objectContainer.Container;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;

import java.util.ArrayList;
import java.util.Iterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    int id;
    String description;
    boolean visited = false;
    boolean exit = false;
    //Container
    Container container;
    private ArrayList<Door> doors;

    public Site(int id, String description, Container container) {
        this.id = id;
        this.description = description;
        this.container = container;
        doors = new ArrayList<>();
    }

    public Site(int id, String description, Container container, boolean exit) {
        this(id, description, container);
        this.exit = exit;
    }

    public boolean isVisited() { return visited; }
    public void visit() { visited = true; }


    public void addItem(Item s) throws ContainerUnacceptedItemException, ContainerFullException { container.add(s); }


    //Doors
    public int  getNumberOfDoors() {
        return doors.size();
    }
    public void addDoor(Door p) {
        doors.add(p);
    }
    public Site openDoor(int index) {
        return doors.get(index).openFrom(this);
    }
    public Iterator<Door> iterator(){ return doors.iterator(); }

}
