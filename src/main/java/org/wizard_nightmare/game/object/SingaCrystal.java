package org.wizard_nightmare.game.object;


import jakarta.xml.bind.annotation.XmlRootElement;
import org.wizard_nightmare.game.Domain;
@XmlRootElement
public class SingaCrystal extends Item{

    private SingaCrystal(int s) { super(Domain.NONE, s); }

    public static SingaCrystal createCrystal(int maximum) { return new SingaCrystal((int) (Math.random() * maximum + 1)); }

}
