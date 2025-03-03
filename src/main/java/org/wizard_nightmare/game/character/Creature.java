package org.wizard_nightmare.game.character;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Attack;
import org.wizard_nightmare.game.spell.Spell;
import org.wizard_nightmare.game.spell.SpellUnknowableException;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creature extends Character {
    private boolean viewed = false;

    public Creature(String n, int life, int hit, Domain t) { super(n, t, life, life, hit); }

    public boolean isAlive() { return getLife() > 0;}

    public void view() { viewed = true; }

    public Attack getRandomAttack() { return  getAttack((int) (Math.random() * attacks.size())); }

    public int protect(int damage, Domain d){
        int protection = 1;
        if (domain == d)
            protection /= 2;
        return damage * protection;
    }

    public void addSpell(Spell spell) throws SpellUnknowableException {
        if(spell.getDomain() == domain)
            super.addSpell(spell);
        else
            throw new SpellUnknowableException();
    }

    public String toString() {
        return name + "\tType(" + domain + ")\tLife(" + life + ")\tPunch(" + attacks.get(0).getDamage() + ")"
                + "\n\t" + memory;

    }

}
