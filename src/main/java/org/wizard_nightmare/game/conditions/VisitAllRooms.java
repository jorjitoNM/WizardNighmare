package org.wizard_nightmare.game.conditions;



import org.wizard_nightmare.game.dungeon.Dungeon;
import org.wizard_nightmare.game.dungeon.Room;

import java.util.Iterator;

public class VisitAllRooms implements Condition{

    Dungeon dungeon;

    public VisitAllRooms(Dungeon dungeon){
        this.dungeon = dungeon;
    }

    @Override
    public boolean check() {
        Iterator it = dungeon.iterator();
        while(it.hasNext()) {
            Room room = (Room)it.next();
            if (!room.isVisited())
                return false;
        }
        return true;
    }
}
