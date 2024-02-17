package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLivingEntity;

@SerializableAs("DamageOtherEntityHookTargetEntity")
public class DamageOtherEntityHookTargetEntity implements DamageOtherEntityHook
{
    EffectWithLivingEntity effect;
    
    public DamageOtherEntityHookTargetEntity(Effect effect) {
        this.effect = (EffectWithLivingEntity) effect;
    }

    public DamageOtherEntityHookTargetEntity(Map<String, Object> config) {
        this.effect = (EffectWithLivingEntity) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "DamageOtherEntity -> LivingEntity: " + effect.getName();
    }
    
    public void process(EntityDamageByEntityEvent event) {
        effect.play((LivingEntity) event.getEntity(), event);
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
