package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCooldownCommand extends Command
{
    public HookCooldownCommand() {
        super("hook cooldown");
        addBaseParameter(new CommandParameterEventClass());
        addBaseParameter(new CommandParameterDouble());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        String eventClass = (String) baseParameters.get(0);
        double cooldown = (double) baseParameters.get(1);
        if(cooldown < 0) throw new CommandExecutionException("Cooldown time must be 0 or positive.");
        Registry.getInstance().setCooldown(itemName, eventClass, cooldown);
        CommandUtil.saveConfig();
        return null;
    }

}
