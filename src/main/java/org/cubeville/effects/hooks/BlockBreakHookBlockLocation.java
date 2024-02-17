package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.block.BlockBreakEvent;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLocation;

@SerializableAs("BlockBreakHookBlockLocation")
public class BlockBreakHookBlockLocation implements BlockBreakHook
{
    EffectWithLocation effect;

    public BlockBreakHookBlockLocation(Effect effect) {
        this.effect = (EffectWithLocation) effect;
    }

    public BlockBreakHookBlockLocation(Map<String, Object> config) {
        effect = (EffectWithLocation) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "BlockLocation: " + effect.getName();
    }

    public void process(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        loc.setX(loc.getX() + 0.5);
        loc.setY(loc.getY() + 0.5);
        loc.setZ(loc.getZ() + 0.5);
        effect.play(loc);
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
