package org.wizard_nightmare.game.conditions;

import org.wizard_nightmare.game.object.Item;

public class FindObjectCondition implements Condition{
    Item item;

    public FindObjectCondition(Item item) { this.item = item; }

    @Override
    public boolean check() { return item.isViewed(); }
}
