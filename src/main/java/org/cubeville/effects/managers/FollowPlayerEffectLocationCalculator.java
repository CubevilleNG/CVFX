package org.cubeville.effects.managers;

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

    int lastCalculatedStep = 0;
    
    FollowPlayerEffectLocationCalculator(Player player) {
        this.player = player;
        location = player.getLocation().add(new Vector(2, yoffset, 2)); // TODO move somewhere somehow...
        location.setPitch(0);
    }

    public Location getLocationForStep(int step) {
        if(step == lastCalculatedStep) return location;

        lastCalculatedStep = step;
        
        if(moving) {
            if(step - startMoveStep > 100) {
                System.out.println("Pausing: " + step);
                pausing = true;
                pauseStartStep = step;
                moving = false;
            }
            else {
                double percentage = (step - startMoveStep) / 100.0;
                if(percentage >= 0.05) {
                    location = startLocation.clone().add(targetLocation.toVector().subtract(startLocation.toVector()).multiply(percentage));
                    location.setY(startLocation.getY() + percentage * (targetLocation.getY() - startLocation.getY()));
                    // System.out.println("Moving step " + (step - startMoveStep) + ": " + location);
                    // System.out.println("From " + startLocation);
                    // System.out.println("To " + targetLocation);
                    // System.out.println("Percentage: " + ((step - startMoveStep) / 100.0));
                }
            }
        }

        else if (pausing) {
            if(step - pauseStartStep > 20) {
                pausing = false;
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
                    if(yaw < 0.0) yaw += 360.0;
                    location.setYaw((float) yaw);
                    startLocation.setYaw((float) yaw);
                }
            }
        }

        return location;
    }

}
