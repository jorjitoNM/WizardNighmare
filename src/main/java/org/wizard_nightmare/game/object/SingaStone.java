package org.wizard_nightmare.game.object;


import jakarta.xml.bind.annotation.XmlRootElement;
import org.wizard_nightmare.game.Domain;
@XmlRootElement
public class SingaStone extends Item{

    public SingaStone(int s, int m) {
        super(Domain.NONE, s, 0, m);
    }

}
