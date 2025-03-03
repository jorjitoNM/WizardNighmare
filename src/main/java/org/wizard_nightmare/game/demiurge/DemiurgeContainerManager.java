package org.wizard_nightmare.game.demiurge;


import lombok.Getter;
import lombok.Setter;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.objectContainer.Container;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerInvalidExchangeException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;

@Getter
public class DemiurgeContainerManager {

    Container wearables;
    Container bag;
    @Setter
    Container site;

    public DemiurgeContainerManager(Container w, Container b, Container s){
        wearables = w;
        bag = b;
        site = s;
    }

    public void deleteItem(Container c, int aIndex) {
        c.remove(aIndex);
    }

    public void addItem(Container a, int aIndex, Container b) throws ContainerUnacceptedItemException, ContainerFullException {
        Item aItem = a.get(aIndex);
        b.add(aItem);
        a.remove(aIndex);
    }

    public void exchangeItem(Container a, int aIndex, Container b, int bIndex) throws ContainerInvalidExchangeException {
        Item aItem = a.get(aIndex);
        Item bItem = b.get(bIndex);

        if (aItem.getClass().equals(bItem.getClass())){
            a.remove(aIndex);
            b.remove(bIndex);

            try {
                a.add(bItem);
                b.add(aItem);
            } catch (ContainerFullException | ContainerUnacceptedItemException ignored) {
            }
        }else{
            throw new ContainerInvalidExchangeException();
        }
    }
}
