package org.cubeville.effects.managers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class EffectWithEntityDamagedByPlayer extends Effect
{
    public abstract void play(LivingEntity entity, Player damager);
}
