package org.cubeville.effects.hooks;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.cubeville.effects.registry.ProjectileTrackerAction;

public interface ProjectileHitHook extends Hook, ProjectileTrackerAction
{
    public void process(ProjectileHitEvent event);
}
