package org.wizard_nightmare.game.character;


import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Attack;
import org.wizard_nightmare.game.actions.PhysicalAttack;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;
import org.wizard_nightmare.game.spell.Spell;
import org.wizard_nightmare.game.spell.SpellUnknowableException;
import org.wizard_nightmare.game.spellContainer.Knowledge;
import org.wizard_nightmare.game.spellContainer.Memory;
import org.wizard_nightmare.game.util.Value;
import org.wizard_nightmare.game.util.ValueOverMaxException;
import org.wizard_nightmare.game.util.ValueUnderMinException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Wizard's attributes and related data.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Character {
    @XmlElement(name = "name")
    String name;
    @XmlAttribute
    Domain domain;
    @XmlElement(name = "values")
    Value life;
    //Spells
    @XmlElement(name = "knowledge")
    Knowledge memory;
    @XmlTransient
    ArrayList<Attack> attacks;

    /**
     * @param n name
     * @param l Initial life
     * @param lm Maximum life
     */
    public Character(String n, Domain d, int l, int lm, int hit) {
        name = n;
        domain = d;
        life = new Value(l, 0, lm);
        memory = new Memory();
        attacks = new ArrayList<>();
        attacks.add(new PhysicalAttack(hit));
    }

    //Life
    public String lifeInfo(){ return getClass().getSimpleName() + " -> " + life; }
    public int getLife() { return life.getValue(); }
    public int getLifeMax() {
        return life.getMaximum();
    }
    public void upgradeLifeMax(int m) {
        life.increaseMaximum(m);
    }
    public void recoverLife(int v) throws ValueOverMaxException { life.increaseValue(v); }
    public void drainLife(int v) throws CharacterKilledException {
        try {
            life.decreaseValue(v);
        } catch (ValueUnderMinException e) {
            life.setToMinimum();
            throw new CharacterKilledException();
        }
    }

    public abstract int protect(int damage, Domain domain);


    public void addSpell(Spell spell) throws SpellUnknowableException {
        if(spell instanceof Attack)
            attacks.add((Attack) spell);
        memory.add(spell);
    }
    public Spell getSpell(int index){ return memory.get(index); }
    public boolean existsSpell(Spell spell) { return memory.exists(spell); }


    //Attacks
    public Iterator<Attack> getAttacksIterator() { return attacks.iterator(); }
    public int getNumberOfAttacks() { return attacks.size(); }
    public Attack getAttack(int index) { return attacks.get(index); }




}
