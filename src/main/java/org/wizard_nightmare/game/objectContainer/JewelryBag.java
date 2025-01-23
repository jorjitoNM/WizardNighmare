package org.wizard_nightmare.game.objectContainer;


import jakarta.xml.bind.annotation.XmlRootElement;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.Necklace;
import org.wizard_nightmare.game.object.Ring;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;

@XmlRootElement
public class JewelryBag extends Container{

    public JewelryBag(int c) { super(Domain.NONE, c); }

    /**
     * Adds just a ring or a necklace
     * @param i the item to be added
     * @throws ContainerFullException the container is full
     * @throws ContainerUnacceptedItemException the container don't accept that item
     */
    public void add(Item i) throws ContainerUnacceptedItemException, ContainerFullException {
        if(isFull())
            throw new ContainerFullException();
        else if(i instanceof Necklace || i instanceof Ring) {
            items.add(i);
        }else
            throw new ContainerUnacceptedItemException();
    }

}
