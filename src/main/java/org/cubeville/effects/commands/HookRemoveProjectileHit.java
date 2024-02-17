package org.cubeville.effects.commands;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HookRemoveProjectileHit extends Command
{
    public HookRemoveProjectileHit() {
        super("hook remove projectilehit");
        addBaseParameter(new CommandParameterInteger());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Registry.getInstance().deregisterProjectileHitEvent(itemName, (int) baseParameters.get(0));
        CommandUtil.saveConfig();
        return null;
    }
}
