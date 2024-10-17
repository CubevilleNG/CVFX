package org.cubeville.effects.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.util.ItemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HooklistGetCommand extends Command
{
    public HooklistGetCommand() {
        super("hooklist get");
    }
    
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        ItemStack item = player.getInventory().getItemInMainHand();
        List<Integer> ids = ItemUtil.getHooklistIDs(item);
        
        if (ids == null || ids.isEmpty()) throw new CommandExecutionException("There are no hooklists on this item!");
        
        List<String> retList = new ArrayList<>();
        retList.add("Hooklists on item:");
        HooklistRegistry hr = HooklistRegistry.getInstance();
        
        for (Integer id : ids) {
            if (hr.containsID(id) && hr.getHooklist(id).getDisplayName() != null) {
                retList.add(" - " + id + " (" + hr.getHooklist(id).getDisplayName() + "§r)");
            } else {
                retList.add(" - " + id);
            }
        }
        
        return new CommandResponse(String.join("\n", retList));
        
    }
}
