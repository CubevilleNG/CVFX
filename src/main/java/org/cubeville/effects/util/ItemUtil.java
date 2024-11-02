package org.cubeville.effects.util;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.effects.Effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ItemUtil
{
    private static final PersistentDataType INT_ARRAY = PersistentDataType.INTEGER_ARRAY;
    
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
    
    public static boolean hasHooklist(ItemStack item) {
        if (item == null) return false;
        if (item.getItemMeta() == null) return false;
        NamespacedKey nsKey = new NamespacedKey(Effects.getInstance(), "hooklists");
        return item.getItemMeta().getPersistentDataContainer().has(nsKey);
    }
    
    public static List<Integer> getHooklistIDs(ItemStack item) {
        if (item == null) return null;
        if (item.getItemMeta() == null) return null;
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey nsKey = new NamespacedKey(Effects.getInstance(), "hooklists");
        
        if (pdc.has(nsKey, PersistentDataType.INTEGER_ARRAY)) {
            return IntStream.of(pdc.get(nsKey, PersistentDataType.INTEGER_ARRAY)).boxed().toList();
        } else {
            return null;
        }
    }

    public static Integer getFirstHooklistID(ItemStack item) {
        if (item == null) return null;
        List<Integer> ids = getHooklistIDs(item);
        if (ids == null) return null;
        return new ArrayList<>(ids).get(0);
    }

    public static void addHooklist(ItemStack item, Integer id) {
        NamespacedKey nsKey = new NamespacedKey(Effects.getInstance(), "hooklists");
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        List<Integer> val = new ArrayList<>();
        List<Integer> existingAbstract = getHooklistIDs(item);
        if (existingAbstract != null) {
            val = new ArrayList<>(existingAbstract);
        }
        val.add(id);
        pdc.set(nsKey, PersistentDataType.INTEGER_ARRAY, val.stream().mapToInt(i -> i).toArray());
        item.setItemMeta(meta);
    }
    
    public static void removeHooklist(ItemStack item, Integer id) {
        NamespacedKey nsKey = new NamespacedKey(Effects.getInstance(), "hooklists");
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        List<Integer> val = new ArrayList<>();
        List<Integer> existingAbstract = getHooklistIDs(item);
        if (existingAbstract != null) {
            val = new ArrayList<>(existingAbstract);
        }
        val.remove(id);
        if (!val.isEmpty()) {
            pdc.set(nsKey, PersistentDataType.INTEGER_ARRAY, val.stream().mapToInt(i -> i).toArray());
        } else {
            pdc.remove(nsKey);
        }
        item.setItemMeta(meta);
    }
}
