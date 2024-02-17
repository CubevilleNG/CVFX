package org.cubeville.effects.commands;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.ProjectileLaunchHookCancelEvent;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HookCreateProjectileLaunchCancelEventCommand extends Command
{
    public HookCreateProjectileLaunchCancelEventCommand() {
        super("hook create projectilelaunch cancel");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Registry.getInstance().registerEvent(itemName, new ProjectileLaunchHookCancelEvent());
        CommandUtil.saveConfig();
        return null;
    }
}
