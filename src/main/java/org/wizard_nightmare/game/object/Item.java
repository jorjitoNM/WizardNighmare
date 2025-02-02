package org.wizard_nightmare.game.object;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.util.Value;
import org.wizard_nightmare.game.util.ValueOverMaxException;
import org.wizard_nightmare.game.util.ValueUnderMinException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Ring.class, Necklace.class, SingaStone.class, SingaCrystal.class, Weapon.class})
public abstract class Item {

    @XmlAttribute
    Domain domain;
    @XmlElement(name = "values")
    Value value;
    @XmlElement(name = "viewed")
    boolean viewed = false;

    public Item(Domain d, int val) {
        domain = d;
        value = new Value(val);
    }

    public Item(Domain d, int val, int min, int max) {
        domain = d;
        value = new Value(val, min, max);
    }

    public void updateValue(int v) { value.updateValue(v); }

    public int getValue() { return value.getValue(); }

    public void view() { viewed = true; }

    public boolean getBounded() { return value.getBounded(); }

    public int getMinimum() { return value.getMinimum(); }
    public int getMaximum() {
        return value.getMaximum();
    }

    public int availableToMinimum() { return value.availableToMinimum(); }
    public int availableToMaximum() { return value.availableToMaximum(); }

    public void setMinimum(int min) { value.setMinimum(min); }
    public void decreaseMinimum(int min) {
        value.decreaseMinimum(min);
    }

    public void setMaximum(int max) {
        value.setMaximum(max);
    }
    public void increaseMaximum(int max) {
        value.increaseMaximum(max);
    }

    public void setToMinimum() {
        value.setToMinimum();
    }
    public void setToMaximum() { value.setToMaximum(); }

    public void decreaseValue(int val) throws ValueUnderMinException { value.decreaseValue(val); }

    public void increaseValue(int val) throws ValueOverMaxException { value.increaseValue(val); }

    public String toString() {
        if(domain == Domain.NONE)
            return getClass().getSimpleName() + value;
        else
            return getClass().getSimpleName() + value + domain;
    }

}
