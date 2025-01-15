package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import org.cubeville.effects.Effects;
import org.cubeville.effects.registry.Registry;

import org.cubeville.effects.hooks.ProjectileLaunchHook;
import org.cubeville.effects.commands.CommandUtil;

@SerializableAs("ProjectileLaunchHookPlayerEffect")
public class ProjectileLaunchHookPlayerEffect extends EffectWithLocation
{
    private int hooklist;
    private double speed;
    private boolean snowball;
    
    public ProjectileLaunchHookPlayerEffect(String name, int hooklist, double speed, boolean snowball) {
        setName(name);
        this.hooklist = hooklist;
        this.speed = speed;
        this.snowball = snowball;
    }

    public ProjectileLaunchHookPlayerEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
        hooklist = (int) config.get("hooklist");
        speed = (double) config.get("speed");
        snowball = (boolean) config.get("snowball");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("hooklist", hooklist);
        ret.put("speed", speed);
        ret.put("snowball", snowball);
        return ret;
    }

    public void play(Location location) {
        List<ProjectileLaunchHook> hooks = Registry.getInstance().getProjectileLaunchHooksOfItem(hooklist);

        World world = location.getWorld();
        Vector direction = location.getDirection();

        Projectile projectile = CommandUtil.launchProjectile(location, direction, speed, snowball);

        for(ProjectileLaunchHook hook: hooks)
            hook.runAt(location, projectile);
    }

    public List<String> getInfo(boolean detailed, String limit) {
        List<String> ret = getInfoBase();
        ret.add("Hooklist: " + hooklist);
        ret.add("Speed: " + speed);
        ret.add("Type: " + (snowball ? "Snowball" : "Arrow"));
        return ret;
    }

    public String getType() {
        return "ProjectileLaunchHookPlayer";
    }
    
    
}
