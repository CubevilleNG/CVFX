package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookListCommand extends Command
{
    public HookListCommand() {
	super("hook list");
        addFlag("all");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        if(flags.contains("all")) {
            player.sendMessage("--------------- Hook List ---------------");
            List<String> hooks = Registry.getInstance().getHookList();
            for(String h: hooks) player.sendMessage(h);
        }
        else {
            String itemName = ItemUtil.safeGetItemInMainHandName(player);
            player.sendMessage("Hook list for item " + itemName + ":");
            List<String> hooks = Registry.getInstance().getHookList(itemName);
            for(String h: hooks) player.sendMessage(h);
        }
        return null;
    }
}
