package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.InteractHookRemoveItem;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateInteractRemoveItemCommand extends HookCommand
{
    public HookCreateInteractRemoveItemCommand() {
	super("hook create interact removeitem");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);
        Registry.getInstance().registerEvent(id, new InteractHookRemoveItem());
        CommandUtil.saveConfig();
        return null;
    }
}

