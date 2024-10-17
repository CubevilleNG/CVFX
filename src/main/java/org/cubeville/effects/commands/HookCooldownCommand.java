package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cubeville.commons.commands.*;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCooldownCommand extends HookCommand
{
    public HookCooldownCommand() {
        super("hook cooldown");
        addBaseParameter(new CommandParameterEventClass());
        addBaseParameter(new CommandParameterDouble());
        addParameter("id", true, new CommandParameterInteger());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);
        
        String eventClass = (String) baseParameters.get(0);
        double cooldown = (double) baseParameters.get(1);
        if(cooldown < 0) throw new CommandExecutionException("Cooldown time must be 0 or positive.");
        Registry.getInstance().setCooldown(id, eventClass, cooldown);
        CommandUtil.saveConfig();
        return null;
    }

}
