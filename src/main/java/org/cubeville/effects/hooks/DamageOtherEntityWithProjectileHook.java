package org.cubeville.effects.hooks;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@SerializableAs("DamageOtherEntityWithProjectileHook")
public interface DamageOtherEntityWithProjectileHook extends Hook
{
    public void process(EntityDamageByEntityEvent event);
}
