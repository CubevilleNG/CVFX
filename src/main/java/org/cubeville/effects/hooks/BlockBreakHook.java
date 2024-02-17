package org.cubeville.effects.hooks;

import org.bukkit.event.block.BlockBreakEvent;

public interface BlockBreakHook extends Hook
{
    public void process(BlockBreakEvent event);
}
