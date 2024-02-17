package org.cubeville.effects.hooks;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@SerializableAs("DamageOtherEntityHook")
public interface DamageOtherEntityHook extends Hook
{
    public void process(EntityDamageByEntityEvent event);
}
