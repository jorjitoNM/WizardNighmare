package org.wizard_nightmare.game.objectContainer;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.Necklace;
import org.wizard_nightmare.game.object.Ring;
import org.wizard_nightmare.game.object.Weapon;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;

public class Chest extends Container{

    public Chest(int c) { super(Domain.NONE, c); }

    /**
     * Adds rings, necklaces or weapon
     * @param i the item to be added
     * @throws ContainerFullException the container is full
     * @throws ContainerUnacceptedItemException the container don't accept that item
     */
    public void add(Item i) throws ContainerUnacceptedItemException, ContainerFullException {
        if(isFull())
            throw new ContainerFullException();
        else if(i instanceof Necklace || i instanceof Ring || i instanceof Weapon) {
            items.add(i);
        }else
            throw new ContainerUnacceptedItemException();
    }


}
