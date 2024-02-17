package org.cubeville.effects.pluginhook;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PluginHook
{
    private World world;
    private Vector regionMin;
    private Vector regionMax;
    private PluginHookEventReceiver eventReceiver;
    private int id;
    
    public PluginHook(World world, Vector regionMin, Vector regionMax, PluginHookEventReceiver eventReceiver, int id) {
        this.world = world;
        this.regionMin = regionMin;
        this.regionMax = regionMax;
        this.eventReceiver = eventReceiver;
        this.id = id;
    }

    public int getId() { return id; }
    
    public void onBlockCollisionEvent(Player player, Block block) {
        if(block.getLocation().toVector().isInAABB(regionMin, regionMax)) {
            eventReceiver.onBlockCollisionEvent(player, block);
        }
    }

    public void onEntityCollisionEvent(Player player, Entity entity) {
        if(entity.getLocation().toVector().isInAABB(regionMin, regionMax)) {
            eventReceiver.onEntityCollisionEvent(player, entity);
        }
    }
}
