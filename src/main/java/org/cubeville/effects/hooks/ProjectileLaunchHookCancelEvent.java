package org.cubeville.effects.hooks;

import org.bukkit.entity.Projectile;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.cubeville.effects.managers.Effect;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("ProjectileLaunchHookCancelEvent")
public class ProjectileLaunchHookCancelEvent implements ProjectileLaunchHook
{
    public ProjectileLaunchHookCancelEvent() {}

    public ProjectileLaunchHookCancelEvent(Map<String, Object> config) {
    }

    public Map<String, Object> serialize() {
        return new HashMap<>();
    }

    public String getInfo() {
        return "CancelEvent";
    }

    public void process(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() + 1);
            player.updateInventory();
        }
    }

    public void runAt(Location location, Projectile projectile) {
    }
    
    public boolean usesEffect(Effect effect) {
        return false;
    }

    public boolean alwaysActive() {
        return true;
    }
}
