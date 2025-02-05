package org.wizard_nightmare.game.spell;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Attack;
import org.wizard_nightmare.game.character.Character;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;

@EqualsAndHashCode(callSuper = true)
@Data
public class ElectricAttack extends Spell implements Attack {

    public ElectricAttack() { super(Domain.ELECTRICITY, 1); }

    @Override
    public int getDamage() { return level.getValue(); }

    @Override
    public void attack(Character character) throws CharacterKilledException {
        character.drainLife(character.protect(level.getValue(), domain));
    }

}
