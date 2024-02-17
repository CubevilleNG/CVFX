package org.cubeville.effects.managers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public abstract class EffectWithLivingEntity extends Effect
{
    public abstract void play(LivingEntity entity, Event event);
}
