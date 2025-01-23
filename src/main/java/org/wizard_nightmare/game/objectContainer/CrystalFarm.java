package org.wizard_nightmare.game.objectContainer;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.SingaCrystal;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;

/**
 * A portable storage to carry the gathered crystals till arrive to home. Maybe a bracelet or a bag...
 * The capacity is limited.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class CrystalFarm extends ContainerSinga {

    public CrystalFarm(int c) { super(c); }

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

    public void grow(int maxElements, int maxCapacity) {
        items.clear();
        updateValue((int) (Math.random() * maxElements + 1));
        for (int i = 0; i < getValue(); i++)
            items.add(SingaCrystal.createCrystal(maxCapacity));
    }

    public SingaCrystal gather() { return (SingaCrystal) items.remove(0); }

}
