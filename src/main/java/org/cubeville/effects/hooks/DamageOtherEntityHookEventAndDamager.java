package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.PiercingEffect;

@SerializableAs("DamageOtherEntityHookEventAndDamager")
public class DamageOtherEntityHookEventAndDamager implements DamageOtherEntityHook
{
    PiercingEffect effect;

    public DamageOtherEntityHookEventAndDamager(Effect effect) {
        this.effect = (PiercingEffect) effect;
    }

    public DamageOtherEntityHookEventAndDamager(Map<String, Object> config) {
        this.effect = (PiercingEffect) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "DamageOtherEntity -> EventAndDamager: " + effect.getName();
    }

    public void process(EntityDamageByEntityEvent event) {
        Player damager;
        if(event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }
        else if(event.getDamager() instanceof Projectile) {
            ProjectileSource source = ((Projectile) event.getDamager()).getShooter();
            if(source instanceof Player) {
                damager = (Player) source;
            }
            else return;
        }
        else return;
        effect.play(event, damager);
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
