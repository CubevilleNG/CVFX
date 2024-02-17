package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.DamageOtherEntityHookCancelEvent;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateDamageOtherEntityCancelEventCommand extends Command
{
    public HookCreateDamageOtherEntityCancelEventCommand() {
        super("hook create damage cancel");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Registry.getInstance().registerEvent(itemName, new DamageOtherEntityHookCancelEvent());
        CommandUtil.saveConfig();
        return null;
    }

}
