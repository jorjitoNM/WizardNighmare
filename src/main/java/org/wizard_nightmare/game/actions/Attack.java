package org.wizard_nightmare.game.actions;


import org.wizard_nightmare.game.character.Character;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;

public interface Attack {
    public int getDamage();

    public void attack(Character character) throws CharacterKilledException;
}
