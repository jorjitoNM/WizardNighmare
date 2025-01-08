package org.wizard_nightmare.game.demiurge;



import org.wizard_nightmare.game.conditions.Condition;

import java.util.ArrayList;

public class EndChecker {

    ArrayList<Condition> conditions;

    public EndChecker(){ conditions = new ArrayList<>(); }

    public void addCondition(Condition condition){ conditions.add(condition); }

    public boolean check(){
        for(Condition condition: conditions)
            if( condition.check() == false)
                return false;
        return true;
    }

}
