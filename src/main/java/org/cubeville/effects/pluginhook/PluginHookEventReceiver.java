package org.cubeville.effects.pluginhook;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface PluginHookEventReceiver
{
    public void onBlockCollisionEvent(Player player, Block block);
    public void onEntityCollisionEvent(Player player, Entity entity);
}
