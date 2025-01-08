package org.wizard_nightmare.game.actions;


import org.wizard_nightmare.game.character.Character;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;

public class PhysicalAttack implements Attack {

    int value;

    public PhysicalAttack(int value){ this.value = value; }

    @Override
    public int getDamage() { return value; }

    @Override
    public void attack(Character character) throws CharacterKilledException { character.drainLife(value); }

    @Override
    public String toString() { return getClass().getSimpleName() + " (" + value + ")"; }
}
