package org.cubeville.effects.managers;

import org.bukkit.Location;

public abstract class EffectWithLocation extends Effect
{
    public abstract void play(Location location);
}
