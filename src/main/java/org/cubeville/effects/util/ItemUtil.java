package org.cubeville.effects.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cubeville.commons.commands.CommandExecutionException;

public class ItemUtil
{
    public static String getItemName(ItemStack item) {
	if(item != null && item.hasItemMeta()) {
	    ItemMeta meta = item.getItemMeta();
	    if(meta.hasDisplayName()) {
                return meta.getDisplayName();
	    }
	}
        return null;
    }

    public static String getItemInMainHandName(Player player) {
        return getItemName(player.getInventory().getItemInMainHand());
    }

    public static String safeGetItemInMainHandName(Player player) throws CommandExecutionException {
        String name = getItemInMainHandName(player);
        if(name == null) throw new CommandExecutionException("No named item in main hand.");
        return name;
    }
}
