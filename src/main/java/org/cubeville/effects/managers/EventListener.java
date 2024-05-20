package org.cubeville.effects.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.cubeville.effects.registry.Registry;

public class EventListener implements Listener
{
    private Registry registry;
    private Map<UUID, Long> lastPlayerMoveTime;
    private static EventListener instance;

    public static EventListener getInstance() {
        return instance;
    }
    
    public EventListener() {
	EffectManager em = EffectManager.getInstance();
	registry = new Registry();
        instance = this;
        lastPlayerMoveTime = new HashMap<>();
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public Registry getRegistry() {
        return registry;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void process(EntityDamageByEntityEvent event) {
        registry.processEntityDamageByEntityEvent(event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void process(PlayerInteractEvent event) {
        registry.processInteractEvent(event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void process(ProjectileLaunchEvent event) {
        registry.processProjectileLaunchEvent(event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void process(ProjectileHitEvent event) {
        registry.processProjectileHitEvent(event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void process(PlayerMoveEvent event) {
        registry.processMoveEvent(event);
        if(event.getTo().distance(event.getFrom()) > 0.03)
            lastPlayerMoveTime.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    public long getLastPlayerMoveTime(UUID playerId) {
        Long ret = lastPlayerMoveTime.get(playerId);
        if(ret == null) return 0;
        return ret;
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void process(BlockBreakEvent event) {
        registry.processBlockBreakEvent(event);
    }
}
