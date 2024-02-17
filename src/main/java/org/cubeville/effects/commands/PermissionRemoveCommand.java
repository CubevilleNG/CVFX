package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterUUID;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;

public class PermissionRemoveCommand extends BaseCommand
{
    public PermissionRemoveCommand() {
        super("permission remove");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterUUID());
    }

    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        Registry.getInstance().getPermissionList().removePermission((String) baseParameters.get(0), (UUID) baseParameters.get(1));
        CommandUtil.saveConfig();
        CommandUtil.clearPermissionCache();
        return null;
    }
}
