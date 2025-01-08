package org.wizard_nightmare.game.spell;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.util.Value;
import org.wizard_nightmare.game.util.ValueOverMaxException;

public abstract class Spell {
    Domain domain;
    Value level;

    Spell (Domain domain, int level){
        this.domain = domain;
        this.level = new Value(level, 1, 5);
    }

    Spell (Spell spell){ this(spell.domain, spell.getLevel()); }

    public Domain getDomain() { return domain; }

    public int getLevel () { return level.getValue(); }

    public void improve(int quantity) throws ValueOverMaxException { level.increaseValue(quantity);}

    public String toString(){
        return getClass().getSimpleName() + level + domain;
    }

}
