package org.wizard_nightmare.game.object;


import jakarta.xml.bind.annotation.XmlRootElement;
import org.wizard_nightmare.game.Domain;

@XmlRootElement
public class Necklace extends Item {

    private Necklace(Domain d, int v) { super(d, v); }

    public static Necklace createNecklace(Domain d, int v) throws ItemCreationErrorException {
        if (d == Domain.ENERGY || d == Domain.LIFE)
            return new Necklace(d, v);
        else
            throw new ItemCreationErrorException();
    }
}
