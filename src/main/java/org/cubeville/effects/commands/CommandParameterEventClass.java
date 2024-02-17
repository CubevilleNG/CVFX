package org.cubeville.effects.commands;

import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.effects.registry.Registry;

public class CommandParameterEventClass implements CommandParameterType
{
    public boolean isValid(String value) {
        return Registry.getInstance().getEventClasses().contains(value);
    }

    public String getInvalidMessage(String value) {
        return "Event class " + value + " does not exist!";
    }

    public Object getValue(String value) {
        return value;
    }
}
