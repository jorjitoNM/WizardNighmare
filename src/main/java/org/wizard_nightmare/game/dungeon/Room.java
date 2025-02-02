package org.wizard_nightmare.game.dungeon;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.character.Creature;
import org.wizard_nightmare.game.object.SingaCrystal;
import org.wizard_nightmare.game.objectContainer.CrystalFarm;
import org.wizard_nightmare.game.objectContainer.RoomSet;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "room")
@XmlAccessorType(XmlAccessType.NONE)
public class Room extends Site {
    @XmlElement(name = "farm")
    private CrystalFarm farm;
    //Creature
    @XmlElement(name = "creature")
    private Creature creature = null;

    public Room(int id, String desc, RoomSet container) {
        super(id, desc, container);
        farm = new CrystalFarm(0);
    }

    public Room(int id, String desc, RoomSet container, boolean e) {
        super(id, desc, container, e);
        farm = new CrystalFarm(0);
    }

    //Crystals
    public void generateCrystals(int maxElements, int maxCapacity) { farm.grow(maxElements, maxCapacity); }
    public boolean isEmpty() { return farm.isEmpty(); }
    public SingaCrystal gather() { return farm.gather(); }


    public boolean isAlive(){
        if(creature == null)
            return false;
        return creature.isAlive();
    }


    public String toString() {

        String exit = "ID(" + id + ") Exit(" + this.exit + ") " + description;
        if (creature != null)
            exit = exit.concat("\n\tCreature: " + creature);
        exit = exit.concat("\n\tCrystalFarm[" + farm.toString() + "]");
        exit = exit.concat("\n\tItems" + container.toString());
        return exit;
    }


}
