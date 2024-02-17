package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLocation;

@SerializableAs("ProjectileHitHookHitLocation")
public class ProjectileHitHookHitLocation implements ProjectileHitHook
{
    EffectWithLocation effect;

    public ProjectileHitHookHitLocation(Effect effect) {
        this.effect = (EffectWithLocation) effect;
    }

    public ProjectileHitHookHitLocation(Map<String, Object> config) {
        effect = (EffectWithLocation) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "ProjectileHitLocation: " + effect.getName();
    }

    public void process(ProjectileHitEvent event) {
        effect.play(event.getEntity().getLocation());
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }

    public void projectileHitEvent() {
    }
}
