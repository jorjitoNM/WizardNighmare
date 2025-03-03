package org.wizard_nightmare.game.util;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "value")
public class Value {
    int value;
    int minimum;
    int maximum;
    boolean bounded;


    public Value(int val, int min, int max) {
        bounded = true;
        value = val;
        minimum = min;
        maximum = max;
    }

    public Value(int val) {
        bounded = false;
        value = minimum = maximum = val;
    }

    public void updateValue(int val) { if (!bounded) value = minimum = maximum = val; }

    public boolean getBounded() { return bounded; }

    public int availableToMinimum() { return value - minimum; }
    public int availableToMaximum() { return maximum - value; }

    public void setMinimum(int min) { if (bounded) minimum = min; }
    public void decreaseMinimum(int min) {
        if (bounded) minimum -= min;
    }

    public void setMaximum(int max) {
        if (bounded) maximum = max;
    }
    public void increaseMaximum(int max) {
        if (bounded) maximum += max;
    }

    public void setToMinimum() {
        value = minimum;
    }
    public void setToMaximum() { value = maximum; }

    public void decreaseValue(int val) throws ValueUnderMinException {
        if (bounded){
            if (value - val < minimum)
                throw new ValueUnderMinException();
            else
                value -= val;
        }
    }

    public void increaseValue(int val) throws ValueOverMaxException {
        if (bounded) {
            if (value + val > maximum)
                throw new ValueOverMaxException();
            else
                value += val;
        }
    }

    public String toString() {
        if (bounded)
            return "(" + minimum + ":" + value + ":" + maximum + ")";
        else
            return "(" + value + ")";

    }
}
