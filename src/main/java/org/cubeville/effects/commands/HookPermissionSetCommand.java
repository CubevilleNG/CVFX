package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookPermissionSetCommand extends HookCommand
{
    public HookPermissionSetCommand() {
        super("hook permission set");
        addBaseParameter(new CommandParameterEventClass());
        addBaseParameter(new CommandParameterPermission());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);
        
        String eventClass = (String) baseParameters.get(0);
        String permission = (String) baseParameters.get(1);
        Registry.getInstance().setPermission(id, eventClass, permission);
        CommandUtil.saveConfig();
        CommandUtil.clearPermissionCache();
        return null;
    }
}
