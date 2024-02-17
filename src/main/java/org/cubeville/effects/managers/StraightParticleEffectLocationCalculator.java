package org.cubeville.effects.managers;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.Location;

public class StraightParticleEffectLocationCalculator implements ParticleEffectLocationCalculator
{
    private double speed;
    private Location location;

    private boolean followPlayerLocation;
    private boolean followPlayerYaw;
    private boolean followPlayerPitch;
    private Player player;
    private Location initialPlayerLocation;

    StraightParticleEffectLocationCalculator(Location location, double speed, Player player, boolean followPlayerLocation, boolean followPlayerYaw, boolean followPlayerPitch) {
        this.speed = speed;
        this.location = location;
        this.player = player;
        if(player != null) initialPlayerLocation = player.getLocation();
        this.followPlayerLocation = followPlayerLocation;
        this.followPlayerYaw = followPlayerYaw;
        this.followPlayerPitch = followPlayerPitch;
    }

    public Location getLocationForStep(int step) {
        Location nloc = location.clone();
        if(followPlayerLocation) {
            nloc.setX(nloc.getX() + player.getLocation().getX() - initialPlayerLocation.getX());
            nloc.setY(nloc.getY() + player.getLocation().getY() - initialPlayerLocation.getY());
            nloc.setZ(nloc.getZ() + player.getLocation().getZ() - initialPlayerLocation.getZ());
        }
        if(followPlayerYaw) {
            float yaw = nloc.getYaw();
            yaw += player.getLocation().getYaw() - initialPlayerLocation.getYaw();
            while(yaw < 0.0) yaw += 360.0;
            while(yaw >= 360.0) yaw -= 360.0;
            nloc.setYaw(yaw);
        }
        if(followPlayerPitch) {
            float pitch = nloc.getPitch();
            pitch += player.getLocation().getPitch() - initialPlayerLocation.getPitch();
            nloc.setPitch(pitch);
        }
        Vector dist = location.getDirection();
        dist.multiply(step * speed);
        nloc.add(dist);
        
        return nloc;
    }
}
