package org.wizard_nightmare.game.objectContainer;


import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.SingaCrystal;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;

/**
 * A portable storage to carry the gathered crystals till arrive to home. Maybe a bracelet or a bag...
 * The capacity is limited.
 */
public class CrystalCarrier extends ContainerSinga {

    public CrystalCarrier(int c) { super(c); }

    /**
     * Adds just a crystal
     * @param i the crystal to be added
     * @throws ContainerFullException the container is full
     * @throws ContainerUnacceptedItemException the container don't accept that item
     */
    public void add(Item i) throws ContainerUnacceptedItemException, ContainerFullException {
        if(isFull())
            throw new ContainerFullException();
        else if(i instanceof SingaCrystal) {
            items.add(i);
        }else
            throw new ContainerUnacceptedItemException();
    }

}
