package org.cubeville.effects.util;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerUtil
{
    public static LivingEntity findTargetEntity(Player player, List<Entity> entityList, double targetWidth, double maxDist) {
        LivingEntity ret = null;
        double maxDistSquared = maxDist * maxDist;
        double minAngle = 1000.0;
        for(Entity e: entityList) {
            if(!e.isDead()) {
                double distsq = e.getLocation().distanceSquared(player.getLocation());
                if(distsq <= maxDistSquared) {
                    if(e instanceof LivingEntity) {
                        if(e.getType() == EntityType.PLAYER || e.getType() == EntityType.VILLAGER || e.getType() == EntityType.PIG || e.getType() == EntityType.COW || e.getType() == EntityType.SHEEP || e.getType() == EntityType.CHICKEN || e.getType() == EntityType.HORSE || e.getType() == EntityType.MUSHROOM_COW || e.getType() == EntityType.WOLF || e.getType() == EntityType.OCELOT || e.getType() == EntityType.DONKEY) {

                            double blockdist = Math.sqrt(distsq);
                            
                            {
                                double pitch = Math.toRadians(player.getLocation().getPitch());
                                double vdist = Math.sin(pitch) * blockdist;
                                double targetMinY = e.getLocation().getY();
                                double targetMaxY = e.getLocation().getY() + 1.9;
                                double hitY = player.getLocation().getY() + 1.65 - vdist;
                                if(hitY < targetMinY || hitY > targetMaxY) continue;
                            }
                            
                            {
                                Vector targetDirection = e.getLocation().subtract(player.getLocation()).toVector();
                                Vector targetDirectionXZ = targetDirection.clone();
                                targetDirectionXZ.setY(0);
                                
                                Vector playerDirection = player.getLocation().getDirection();
                                Vector playerDirectionXZ = playerDirection.clone();
                                playerDirectionXZ.setY(0);
                                
                                double angleXZ = playerDirectionXZ.angle(targetDirectionXZ);
                                double maxAngleXZ = Math.atan(targetWidth / 2.0 / blockdist);

                                if(angleXZ > maxAngleXZ) continue;
                                if(angleXZ >= minAngle) continue;
                            }

                            ret = (LivingEntity) e;
                        }
		    }
		}
	    }
        }
        return ret;
    }
}
