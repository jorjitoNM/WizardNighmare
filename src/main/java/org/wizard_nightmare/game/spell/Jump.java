package org.wizard_nightmare.game.spell;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Cast;
import org.wizard_nightmare.game.dungeon.Dungeon;

public class Jump extends Spell implements Cast {

    Dungeon dungeon;

    public Jump(Dungeon dungeon) {
        super(Domain.JUMP, 1);
        this.dungeon = dungeon;
    }
    @Override
    public int cast(String param, int value) {
        return 0;
    }
}
