package org.cubeville.effects.commands;

import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.effects.managers.EffectManager;

public class CommandParameterEffect implements CommandParameterType
{
    Class effectClass;

    public CommandParameterEffect(Class effectClass) {
        this.effectClass = effectClass;
    }

    public boolean isValid(String value) {
        if(EffectManager.getInstance().getEffectByName(value) == null) return false;
        return (effectClass.isInstance(EffectManager.getInstance().getEffectByName(value)));
    }

    public String getInvalidMessage(String value) {
        return "Unknown effect " + value;
    }

    public Object getValue(String value) {
        return EffectManager.getInstance().getEffectByName(value);
    }
}
