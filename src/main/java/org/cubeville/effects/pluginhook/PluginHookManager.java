package org.cubeville.effects.pluginhook;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PluginHookManager
{
    List<PluginHook> pluginHooks;
    private static PluginHookManager instance;
    int hookIdCount = 0;
    
    public PluginHookManager() {
        instance = this;
        pluginHooks = new ArrayList<>();
    }

    public int hook(World world, Vector regionMin, Vector regionMax, PluginHookEventReceiver eventReceiver) {
        int id = hookIdCount++;
        pluginHooks.add(new PluginHook(world, regionMin, regionMax, eventReceiver, id));
        return id;
    }

    public void unhook(int id) {
        for(int i = 0; i < pluginHooks.size(); i++) {
            if(pluginHooks.get(i).getId() == id) {
                pluginHooks.remove(i);
                break;
            }
        }
    }
    
    public static void onBlockCollisionEvent(Player player, Block block) {
        for(PluginHook h: instance.pluginHooks) {
            h.onBlockCollisionEvent(player, block);
        }
    }

    public static void onEntityCollisionEvent(Player player, Entity entity) {
        for(PluginHook h: instance.pluginHooks) {
            h.onEntityCollisionEvent(player, entity);
        }        
    }
}
