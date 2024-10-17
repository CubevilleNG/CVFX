package org.cubeville.effects.commands;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.hooks.BlockBreakHookBlockLocation;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HooklistAddToItemCommand extends Command
{
    public HooklistAddToItemCommand() {
        super("hooklist addtoitem");
        addBaseParameter(new CommandParameterInteger());
    }
    
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = (Integer) baseParameters.get(0);
        if (!HooklistRegistry.getInstance().containsID(id)) throw new CommandExecutionException("Invalid hooklist ID!");
        
        ItemUtil.addHooklist(player.getInventory().getItemInMainHand(), id);
        
        return new CommandResponse("Hooklist " + id + " added.");
    }
}
