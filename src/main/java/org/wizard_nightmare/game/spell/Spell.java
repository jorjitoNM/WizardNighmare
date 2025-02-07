package org.wizard_nightmare.game.spell;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.util.Value;
import org.wizard_nightmare.game.util.ValueOverMaxException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Spell {
    Domain domain;
    Value level;

    Spell(Domain domain, int level) {
        this.domain = domain;
        this.level = new Value(level, 1, 5);
    }

    Spell(Spell spell) {
        this(spell.domain, spell.getLevel());
    }

    public int getLevel() {
        return level.getValue();
    }

    public int getMinimun() {
        return level.getMinimum();
    }

    public int getMax() {
        return level.getMaximum();
    }

    public Boolean getBounded() {
        return level.getBounded();
    }

    public void improve(int quantity) throws ValueOverMaxException {
        level.increaseValue(quantity);
    }

    public String toString() {
        return getClass().getSimpleName() + level + domain;
    }

}
