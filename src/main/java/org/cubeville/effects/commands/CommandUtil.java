package org.cubeville.effects.commands;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.configuration.file.FileConfiguration;
import org.cubeville.effects.Effects;
import org.cubeville.effects.hooklists.Hooklist;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.registry.Registry;

public class CommandUtil
{
    public static void saveConfig() {
        Effects.getInstance().saveEffects();
    }

    public static void clearPermissionCache() {
        Registry.getInstance().clearPermissionCache();
    }

    public static Integer createNewHooklist() {
        FileConfiguration config = Effects.getInstance().getConfig();
        Integer nextID = (Integer) config.get("next-hooklist-id", 0);
        Integer id = nextID;
        Hooklist hooklist = new Hooklist();
        HooklistRegistry.getInstance().register(id, hooklist);
        nextID++;
        config.set("next-hooklist-id", nextID);
        CommandUtil.saveConfig();
        return id;
    }

    public static Projectile launchProjectile(Location location, Vector direction, double speed, boolean snowball) {

        World world = location.getWorld();

        if(snowball) {
            world.playSound(location, Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
            Snowball s = world.spawn(location, Snowball.class);
            s.setShooter(null);
            s.setVelocity(direction.multiply(speed));
            s.setGravity(true);
            return s;
        }

        else {
            world.playSound(location, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
            Arrow arrow = world.spawnArrow(location, direction, (float) speed, 0.0f);
            arrow.setShooter(null);
            arrow.setCritical(false);
            arrow.setGravity(true);
            arrow.setPierceLevel(0);
            return arrow;
        }

    }
    
}
