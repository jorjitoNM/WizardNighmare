package org.wizard_nightmare.game.spell;


import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wizard_nightmare.game.Domain;
import org.wizard_nightmare.game.util.Value;
import org.wizard_nightmare.game.util.ValueOverMaxException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType
public abstract class Spell {
    @XmlAttribute
    Domain domain;
    @XmlElement(name = "values")
    Value level;

    Spell (Domain domain, int level){
        this.domain = domain;
        this.level = new Value(level, 1, 5);
    }

    Spell (Spell spell){ this(spell.domain, spell.getLevel()); }

    public int getLevel () { return level.getValue(); }

    public void improve(int quantity) throws ValueOverMaxException { level.increaseValue(quantity);}

    public String toString(){
        return getClass().getSimpleName() + level + domain;
    }

}
