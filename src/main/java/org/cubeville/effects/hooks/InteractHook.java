package org.cubeville.effects.hooks;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEvent;

public interface InteractHook extends Hook
{
    public boolean process(PlayerInteractEvent event);
    public void playAt(Location location, int stopAt, String group);
    public void playFor(Player player, int stopAtm, String group);
}
