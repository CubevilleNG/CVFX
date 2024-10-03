package org.cubeville.effects.managers;

import java.util.Random;

import org.bukkit.util.Vector;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class FollowPlayerEffectLocationCalculator implements ParticleEffectLocationCalculator
{
    Player player;
    Location location;

    double yoffset = 0.5;
    
    boolean moving = false;
    Location targetLocation;
    Location startLocation;
    int moveStep;
    int startMoveStep;
    
    boolean pausing = false;
    int pauseStartStep;

    int startIdleStep = 0;
    double idleYawTurn = 0.0;
    double idleStartYaw = 0.0;
    
    int lastCalculatedStep = 0;

    Random random = new Random();
    
    FollowPlayerEffectLocationCalculator(Player player) {
        this.player = player;
        location = player.getLocation();

        {
            float yaw = location.getYaw();
            yaw += 180.0f;
            if(yaw >= 360.0f) yaw -= 360.0f;
            location.setYaw(yaw);

            location.setPitch(0);
        }

        {
            float yaw = (float) Math.toRadians(player.getLocation().getYaw());
            double ox = -4 * Math.sin(yaw);
            double oz = 4 * Math.cos(yaw);
            location.add(ox, 0, oz);
        }
    }

    public Location getLocationForStep(int step) {
        if(step <= lastCalculatedStep) return location; // TODO this can happen with timelines actually
        
        lastCalculatedStep = step;
        
        if(moving) {
            if(step - startMoveStep > 100) {
                pausing = true;
                pauseStartStep = step;
                moving = false;
            }
            else {
                double percentage = (step - startMoveStep) / 100.0;
                if(percentage >= 0.05) {
                    location = startLocation.clone().add(targetLocation.toVector().subtract(startLocation.toVector()).multiply(percentage));
                    location.setY(startLocation.getY() + percentage * (targetLocation.getY() - startLocation.getY()));
                }
            }
        }

        else if (pausing) {
            if(step - pauseStartStep > 20) {
                pausing = false;
                startIdleStep = step;
            }
        }

        else {
            if(player.getLocation().distance(location) > 8.0) {
                startMoveStep = step;
                startLocation = location.clone();
                Vector flydir = player.getLocation().subtract(startLocation).toVector();
                flydir.setY(0);
                double distance = 0.0;
                Vector startloc = startLocation.toVector();
                startloc.setY(0);
                Vector ploc = player.getLocation().toVector();
                ploc.setY(0);
                distance = ploc.distance(startloc) - 4;
                if(distance > 10.0 && distance < 25.0) distance = 5.0;
                else if(distance > 25.0) distance = ploc.distance(startloc) - 10.0;

                if(distance > 0.0) {
                    moving = true;
                    flydir = flydir.normalize().multiply(distance);
                    targetLocation = location.clone().add(flydir);
                    targetLocation.setY(player.getLocation().getY() + 0.5);
                    Vector direction = targetLocation.toVector().subtract(location.toVector());
                    direction.setY(0);
                    double yaw = Math.toDegrees(Math.atan2(direction.getZ(), direction.getX())) - 90.0;
                    while (yaw < 0.0) yaw += 360.0;
                    while (yaw >= 360.0) yaw -= 360.0;
                    location.setYaw((float) yaw);
                    startLocation.setYaw((float) yaw);
                    return location;
                }
            }

            int ph = (step - startIdleStep) % 50;
            if(ph == 1) {
                Vector direction = player.getLocation().toVector().subtract(location.toVector());
                double idleTargetYaw = Math.toDegrees(Math.atan2(direction.getZ(), direction.getX())) - 90.0;
                if(idleTargetYaw < 0.0) idleTargetYaw += 360.0;
                idleStartYaw = location.getYaw();
                idleYawTurn = idleTargetYaw - idleStartYaw;

                if(idleYawTurn > 180.0) idleYawTurn -= 360.0;
                if(idleYawTurn < -180.0) idleYawTurn += 360.0;

                int rnd = random.nextInt(100);
                if(rnd < 10) {
                    if(idleYawTurn <= .1) idleYawTurn += 360.0;
                }
                else if(rnd < 20 && idleYawTurn >= -.1)
                    idleYawTurn -= 360.0;
            }
            else if(ph >= 2 && ph <= 11) {
                double p = (ph - 1) / 10.0;
                double yaw = idleStartYaw + idleYawTurn * p;
                if(yaw >= 360.0) yaw -= 360.0;
                if(yaw < 0.0) yaw += 360.0;
                location.setYaw((float) yaw);
            }
        }

        return location;
    }

}
