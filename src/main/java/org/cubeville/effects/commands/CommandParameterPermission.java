package org.cubeville.effects.commands;

import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.effects.registry.Registry;

public class CommandParameterPermission implements CommandParameterType
{
    public boolean isValid(String value) {
        return Registry.getInstance().getPermissionList().permissionExists(value);
    }

    public String getInvalidMessage(String value) {
        return "Permission " + value + " does not exist!";
    }

    public Object getValue(String value) {
        return value;
    }
}
