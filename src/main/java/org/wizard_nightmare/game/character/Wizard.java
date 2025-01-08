package org.wizard_nightmare.game.character;


import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.actions.Attack;
import org.wizard_nightmare.game.character.exceptions.CharacterKilledException;
import org.wizard_nightmare.game.character.exceptions.WizardTiredException;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.Weapon;
import org.wizard_nightmare.game.objectContainer.Container;
import org.wizard_nightmare.game.objectContainer.CrystalCarrier;
import org.wizard_nightmare.game.objectContainer.JewelryBag;
import org.wizard_nightmare.game.objectContainer.Wearables;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerFullException;
import org.wizard_nightmare.game.objectContainer.exceptions.ContainerUnacceptedItemException;
import org.wizard_nightmare.game.spell.Spell;
import org.wizard_nightmare.game.spell.SpellUnknowableException;
import org.wizard_nightmare.game.util.Value;
import org.wizard_nightmare.game.util.ValueOverMaxException;
import org.wizard_nightmare.game.util.ValueUnderMinException;

/**
 * Wizard's attributes and related data.
 *
 */
public class Wizard extends Character {

    private final Value energy;
    private final Wearables wearables;
    private final CrystalCarrier crystalCarrier;
    private final JewelryBag jewelryBag;

    public Wizard(String n, int l, int lm, int e, int em, Wearables w, CrystalCarrier c, JewelryBag j) {
        super(n, Domain.NONE, l, lm, 1);

        energy = new Value(em, 0, e);

        wearables = w;
        crystalCarrier = c;
        jewelryBag = j;
    }

    public void drainLife(int v) throws CharacterKilledException {
        try {
            int protection = wearables.getNecklaceProtection(Domain.LIFE, v);
            life.decreaseValue(v);
        } catch (ValueUnderMinException e) {
            life.setToMinimum();
            throw new CharacterKilledException();
        }
    }

    //Energy
    public int getEnergy() {
        return energy.getValue();
    }
    public boolean hasEnoughEnergy(int checkValue) {return energy.availableToMinimum() > checkValue; }

    public void sleep(int maxRecovery) {
        recoverEnergy(maxRecovery);
    }
    public void recoverEnergy(int e) {
        try {
            energy.increaseValue(e);
        } catch (ValueOverMaxException ex) {
            energy.setToMaximum();
        }
    }
    public void drainEnergy(int e) throws WizardTiredException {
        try {
            int protection = wearables.getNecklaceProtection(Domain.ENERGY, e);
            energy.decreaseValue(protection);
        } catch (ValueUnderMinException ex) {
            energy.setToMinimum();
        }
        if (energy.getValue() <= 1)
            throw new WizardTiredException();
    }
    public int getEnergyMax() {
        return energy.getMaximum();
    }
    public void upgradeEnergyMax(int m) {
        energy.increaseMaximum(m);
    }


    //Containers
    public Container getCrystalCarrier(){ return crystalCarrier;}
    public Container getJewelryBag() { return jewelryBag; }
    public Container getWearables() { return wearables; }


    public void addItem(Item item) throws ContainerUnacceptedItemException, ContainerFullException {
        if(item instanceof Attack)
            attacks.add((Attack) item);
        wearables.add(item);
    }

    public void addSpell(Spell spell) throws SpellUnknowableException {
        if(spell instanceof Attack)
            attacks.add((Attack) spell);
        memory.add(spell);
    }

    public void checkWeapon(){
        Attack remove = null;
        for (Attack a : attacks)
            if (a instanceof Weapon) {
                remove = a;
                break;
            }

        if (remove != null)
            attacks.remove(remove);

        Weapon w = wearables.getWeapon();
        if(w != null)
            attacks.add(w);
    }

    public int protect(int damage, Domain domain){
        int newDamage = damage - wearables.getRingProtection(domain);;
        if(newDamage < 1)
            newDamage = 1;
        return  newDamage;
    }

    public String toString() {
        return name + "\tEnergy" + energy + "\tLife" + life + "\n\t"
                + crystalCarrier + "\n\t"
                + wearables + "\n\t"
                + jewelryBag + "\n\t"
                + memory + "\n\t"
                + attacks.toString();
    }

}
