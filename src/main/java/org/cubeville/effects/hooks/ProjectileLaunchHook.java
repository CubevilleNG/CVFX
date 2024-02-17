package org.cubeville.effects.hooks;

import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface ProjectileLaunchHook extends Hook
{
    public void process(ProjectileLaunchEvent event);
}
