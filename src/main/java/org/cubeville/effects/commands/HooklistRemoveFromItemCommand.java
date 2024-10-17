package org.cubeville.effects.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HooklistRemoveFromItemCommand extends Command
{
    public HooklistRemoveFromItemCommand() {
        super("hooklist removefromitem");
        addBaseParameter(new CommandParameterInteger());
    }
    
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = (Integer) baseParameters.get(0);
        if (!HooklistRegistry.getInstance().containsID(id)) throw new CommandExecutionException("Invalid hooklist ID!");
        
        ItemStack item = player.getInventory().getItemInMainHand();
        List<Integer> ids = ItemUtil.getHooklistIDs(item);
        
        if (ids == null || !ids.contains(id)) throw new CommandExecutionException("ID " + id + " not present on held item!");
        
        ItemUtil.removeHooklist(player.getInventory().getItemInMainHand(), id);
        
        return new CommandResponse("Hooklist " + id + " removed from item.");
    }
}
