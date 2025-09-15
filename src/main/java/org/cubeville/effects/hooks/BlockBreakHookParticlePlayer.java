package org.cubeville.effects.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.block.BlockBreakEvent;

import org.cubeville.effects.Effects;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectTimedRunnable;

@SerializableAs("BlockBreakHookParticlePlayer")
public class BlockBreakHookParticlePlayer implements BlockBreakHook
{
    private ParticleEffect effect;
    private double yOffset;
    private double stepsPerTick;
    private double speed;
    private double pitch;
    private List<Material> blockTypes;
    
    public BlockBreakHookParticlePlayer(String effectName, double yOffset, double stepsPerTick, double speed, double pitch) {
        effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
	this.yOffset = yOffset;
	this.stepsPerTick = stepsPerTick;
	this.speed = speed;
	this.pitch = pitch;
        blockTypes = new ArrayList<>();
        blockTypes.add(Material.ACACIA_LEAVES);
    }

    public BlockBreakHookParticlePlayer(Map<String, Object> config) {
        effect = (ParticleEffect) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
        yOffset = (double) config.get("yOffset");
        stepsPerTick = (double) config.get("stepsPerTick");
        speed = (double) config.get("speed");
        pitch = (double) config.get("pitch");
        blockTypes = new ArrayList<>();
        blockTypes.add(Material.ACACIA_LEAVES);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        ret.put("yOffset", yOffset);
        ret.put("stepsPerTick", stepsPerTick);
        ret.put("speed", speed);
        ret.put("pitch", pitch);
        return ret;
    }

    public String getInfo() {
        return "BlockLocationParticlePlayer: " + effect.getName();
    }

    public void process(BlockBreakEvent event) {
        if(!blockTypes.contains(event.getBlock().getType())) return;
        Location loc = event.getBlock().getLocation();
        loc.setX(loc.getX() + 0.5);
        loc.setY(loc.getY() + 0.5);
        loc.setZ(loc.getZ() + 0.5);
	loc.setY(loc.getY() + yOffset);
	loc.setPitch((float)pitch);
        loc.setYaw(event.getPlayer().getLocation().getYaw());
        new ParticleEffectTimedRunnable(Effects.getInstance(), event.getPlayer(), effect, stepsPerTick, speed, loc, false, false, false, false, false, 0, false, false, 0, null).runTaskTimer(Effects.getInstance(), 1, 1);
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
