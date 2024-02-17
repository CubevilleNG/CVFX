package org.cubeville.effects.hooks;

import org.bukkit.event.player.PlayerMoveEvent;

public interface MoveHook extends Hook
{
    public void process(PlayerMoveEvent event);
}
