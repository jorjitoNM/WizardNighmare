package org.wizard_nightmare.game.objectContainer;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.object.Item;

public abstract class ContainerSinga extends Container {

    public ContainerSinga(int c) { super(Domain.NONE, c); }

    public int singa(){
        int singa = 0;
        for (Item i : items)
            singa += i.getValue();
        return singa;
    }

}
