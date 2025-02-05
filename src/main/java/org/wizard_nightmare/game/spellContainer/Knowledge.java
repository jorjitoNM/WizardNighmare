package org.wizard_nightmare.game.spellContainer;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.wizard_nightmare.game.spell.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basic aggregation of elements that is limited in capacity
 */
@Data
@AllArgsConstructor
public abstract class Knowledge {

    List<Spell> spells;

    public Knowledge() { spells = new ArrayList<>(); }

    public int size() { return spells.size(); }

    public Spell get(int index){ return spells.get(index); }

    public void add(Spell i) { spells.add(i); }

    public boolean exists ( Spell spell) {
        for (Spell s: spells)
            if (s.getClass().getName().equals(spell.getClass().getName()))
                return true;
        return false;
    }

    public Iterator iterator(){ return spells.iterator(); }

    public String toString() {
        String exit = getClass().getSimpleName() + " -> ";
        for (Spell spell : spells)
            exit = exit.concat(spell + " ");
        return exit;
    }

}
