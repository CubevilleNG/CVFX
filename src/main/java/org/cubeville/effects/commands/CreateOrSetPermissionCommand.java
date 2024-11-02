package org.cubeville.effects.commands;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.CommandParameterList;
import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterUUID;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;

public class CreateOrSetPermissionCommand extends BaseCommand
{
    public CreateOrSetPermissionCommand() {
        super("createorsetpermission");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterUUID());
        List<CommandParameterType> classargs = new ArrayList<>();
        for(int i = 0; i < 10; i++) classargs.add(new CommandParameterEventClass());
        addBaseParameter(new CommandParameterList(classargs, 1));
    }

    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {

        String permission = (String) baseParameters.get(0);

        UUID uuid = (UUID) baseParameters.get(2);
        Registry.getInstance().getPermissionList().addPermission(permission, uuid);

        List<String> eventClasses = (List<String>) baseParameters.get(3);
        int hooklistId = (int) baseParameters.get(1);

        for(String eventClass: eventClasses)
            Registry.getInstance().setPermission(hooklistId, eventClass, permission);
        
        CommandUtil.saveConfig();
        CommandUtil.clearPermissionCache();

        return null;
    }
}
        
