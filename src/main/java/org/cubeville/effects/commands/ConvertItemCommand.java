package org.cubeville.effects.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConvertItemCommand  extends Command {
    
    public ConvertItemCommand() {
        super("convertitem");
    }
    
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        if (itemName == null) throw new CommandExecutionException("You are not holding a named item!");
        
        Integer id = HooklistRegistry.getInstance().getIDFromName(itemName);
        if (id == null) throw new CommandExecutionException("No hooklist found with that display name!");
        
        ItemUtil.addHooklist(player.getInventory().getItemInMainHand(), id);
        return new CommandResponse("Hooklist " + id + " added!");
    }
}
