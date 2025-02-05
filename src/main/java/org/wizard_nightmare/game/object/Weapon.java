package org.wizard_nightmare.game.object;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Attack;
import org.wizard_nightmare.game.character.Character;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;
public class Weapon extends Item implements Attack {

    public Weapon(int v) { super(Domain.NONE, v); }

    @Override
    public int getDamage() { return value.getValue(); }

    @Override
    public void attack(Character character) throws CharacterKilledException {
        character.drainLife(value.getValue());
    }

}
