package org.cubeville.effects.commands;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.Effects;
import org.cubeville.effects.hooklists.Hooklist;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class HookCommand extends Command {
    
    public HookCommand(String fullCommand) {
        super(fullCommand);
        addParameter("id", true, new CommandParameterInteger());
    }
    
    protected Integer getHooklistID(Player player, Map<String, Object> parameters, Boolean createNew) throws CommandExecutionException {
        Integer id;
        if (!parameters.containsKey("id")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null) throw new CommandExecutionException("You are not holding an item!");
            if (item.getType() == null) throw new CommandExecutionException("You are not holding an item!");
            id = ItemUtil.getFirstHooklistID(item);
            if (id == null) {
                if (createNew) {
                    id = HooklistRegistry.getInstance().createNewHooklist();
                    ItemUtil.addHooklist(item, id);
                    player.sendMessage("Created new hooklist " + id);
                } else {
                    throw new CommandExecutionException("There are no hooklists on this item!");
                }
            }
        } else {
            id = (Integer) parameters.get("id");
            if (!HooklistRegistry.getInstance().containsID(id)) throw new CommandExecutionException("Invalid hooklist ID!");
        }
        
        return id;
    }
    
    protected Integer getHooklistID(Player player, Map<String, Object> parameters) throws CommandExecutionException {
        return getHooklistID(player, parameters, true);
    }
}
