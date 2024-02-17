package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.List;

import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;

public class CommandParameterListEffect implements CommandParameterType
{
    Class effectClass;
    
    public CommandParameterListEffect(Class effectClass) {
        this.effectClass = effectClass;
    }

    public boolean isValid(String value) {
        String[] v = value.split(";");
        for(int i = 0; i < v.length; i++) {
            if(EffectManager.getInstance().getEffectByName(v[i]) == null) return false;
            if(!effectClass.isInstance(EffectManager.getInstance().getEffectByName(v[i]))) return false;
        }
        return true;
    }

    public String getInvalidMessage(String value) {
        return "Effect not found or wrong type!";
    }

    public Object getValue(String value) {
        String[] v = value.split(";");
        List<Effect> ret = new ArrayList<>();
        for(int i = 0; i < v.length; i++) {
            ret.add(EffectManager.getInstance().getEffectByName(v[i]));
        }
        return ret;
    }
}
