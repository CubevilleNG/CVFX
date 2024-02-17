package org.cubeville.effects.managers;

import org.bukkit.Location;

public class StaticParticleEffectLocationCalculator implements ParticleEffectLocationCalculator
{
    private Location location;

    StaticParticleEffectLocationCalculator(Location location) {
        this.location = location;
    }

    public Location getLocationForStep(int step) {
        return location;
    }
}
