package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.cubeville.effects.Effects;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectProjectileRunnable;

@SerializableAs("ProjectileLaunchHookParticlePlayer")
public class ProjectileLaunchHookParticlePlayer implements ProjectileLaunchHook
{
    private ParticleEffect effect;

    public ProjectileLaunchHookParticlePlayer(String effectName) {
        this.effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
    }

    public ProjectileLaunchHookParticlePlayer(Map<String, Object> config) {
        effect = (ParticleEffect) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        if(effect == null) return "Effect not found!";
        return "ProjectileLaunch ParticlePlayer: " + effect.getName();
    }

    public void process(ProjectileLaunchEvent event) {
        Projectile projectile = (Projectile) event.getEntity();
        new ParticleEffectProjectileRunnable(effect, projectile).runTaskTimer(Effects.getInstance(), 1, 1);
    }

    public boolean alwaysActive() {
        return false;
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }
}
