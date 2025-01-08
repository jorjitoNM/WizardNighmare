package org.wizard_nightmare.game.conditions;


import org.wizard_nightmare.game.character.Creature;

public class FindCreatureCondition implements Condition{
    Creature creature;

    public FindCreatureCondition(Creature creature) { this.creature = creature; }

    @Override
    public boolean check() {
        return creature.isViewed();
    }
}
