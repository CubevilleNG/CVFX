package org.cubeville.effects.managers;

import org.bukkit.Location;

interface ParticleEffectLocationCalculator
{
    public Location getLocationForStep(int step);
}
