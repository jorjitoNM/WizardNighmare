package org.wizard_nightmare.game.spell;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Cast;
import org.wizard_nightmare.game.dungeon.Dungeon;

public class Recover extends Spell implements Cast {

    Dungeon dungeon;

    public Recover(Dungeon dungeon) {
        super(Domain.LIFE, 1);
        this.dungeon = dungeon;
    }
    @Override
    public int cast(String param, int value) {
        return 0;
    }
}
