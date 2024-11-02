package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLocation;

@SerializableAs("ProjectileLaunchHookPlayerLocation")
public class ProjectileLaunchHookPlayerLocation implements ProjectileLaunchHook
{
    EffectWithLocation effect;

    public ProjectileLaunchHookPlayerLocation(Effect effect) {
        this.effect = (EffectWithLocation) effect;
    }

    public ProjectileLaunchHookPlayerLocation(Map<String, Object> config) {
        effect = (EffectWithLocation) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "ProjectileLaunch PlayerLocation: " + effect.getName();
    }

    public void process(ProjectileLaunchEvent event) {
        ProjectileSource shooter = ((Projectile) event.getEntity()).getShooter();
        effect.play(((Player)shooter).getLocation());
    }

    public void runAt(Location location, Projectile projectile) {
        effect.play(location);
    }
    
    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
