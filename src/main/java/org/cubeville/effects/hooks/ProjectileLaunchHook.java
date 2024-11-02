package org.cubeville.effects.hooks;

import org.bukkit.entity.Projectile;
import org.bukkit.Location;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface ProjectileLaunchHook extends Hook
{
    public void process(ProjectileLaunchEvent event);
    public void runAt(Location location, Projectile projectile);
}
