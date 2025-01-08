package org.wizard_nightmare.game.spell;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Cast;

public class DrainEnergy extends Spell implements Cast {

    public DrainEnergy() { super(Domain.ENERGY, 1); }

    @Override
    public int cast(String param, int value) {
        return 0;
    }
}
