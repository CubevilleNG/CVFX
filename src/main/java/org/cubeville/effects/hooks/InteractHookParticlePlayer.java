package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.cubeville.effects.Effects;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectTimedRunnable;

@SerializableAs("InteractHookParticlePlayer")
public class InteractHookParticlePlayer implements InteractHook
{
    private ParticleEffect effect;
    private double yOffset;
    private double ySneakShift;
    private double stepsPerTick;
    private double speed;
    private boolean fixedPitch;
    private double pitch;
    private double yawOffset;
    private boolean followPlayerLocation;
    private boolean followPlayerYaw;
    private boolean followPlayerPitch;
    private boolean followPlayer;
    private boolean randomSpins;
    private boolean disableWhenMoving;
    private boolean disableWhenStill;
    private boolean playerExclusive;
    
    public InteractHookParticlePlayer(String effectName, double yOffset, double yawOffset, double stepsPerTick, double speed, boolean fixedPitch, double pitch, double ySneakShift, boolean followPlayerLocation, boolean followPlayerYaw, boolean followPlayerPitch, boolean disableWhenMoving, boolean disableWhenStill, boolean followPlayer, boolean randomSpins, boolean playerExclusive) {
	this.effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
	this.yOffset = yOffset;
	this.stepsPerTick = stepsPerTick;
	this.speed = speed;
	this.fixedPitch = fixedPitch;
	this.pitch = pitch;
        this.ySneakShift = ySneakShift;
        this.followPlayerLocation = followPlayerLocation;
        this.followPlayerYaw = followPlayerYaw;
        this.followPlayerPitch = followPlayerPitch;
        this.disableWhenMoving = disableWhenMoving;
        this.disableWhenStill = disableWhenStill;
        this.followPlayer = followPlayer;
        this.randomSpins = randomSpins;
        this.yawOffset = yawOffset;
        this.playerExclusive = playerExclusive;
    }

    public InteractHookParticlePlayer(Map<String, Object> config) {
        String effectName = (String) config.get("effect");
        this.effect = (ParticleEffect) EffectManager.getInstance().getEffectByName(effectName);
        this.yOffset = (double) config.get("yOffset");
        this.stepsPerTick = (double) config.get("stepsPerTick");
        this.speed = (double) config.get("speed");
        this.fixedPitch = (boolean) config.get("fixedPitch");
        this.pitch = (double) config.get("pitch");
        if(config.get("ySneakShift") != null)
            ySneakShift = (double) config.get("ySneakShift");
        else
            ySneakShift = 0.0;
        followPlayerLocation = config.containsKey("followPlayerLocation") ? (boolean) config.get("followPlayerLocation") : false;
        followPlayerYaw = config.containsKey("followPlayerYaw") ? (boolean) config.get("followPlayerYaw") : false;
        followPlayerPitch = config.containsKey("followPlayerPitch") ? (boolean) config.get("followPlayerPitch") : false;

        if(config.get("disableWhenMoving") != null)
            disableWhenMoving = (boolean) config.get("disableWhenMoving");
        else
            disableWhenMoving = false;
        if(config.get("disableWhenStill") != null)
            disableWhenStill = (boolean) config.get("disableWhenStill");
        else
            disableWhenStill = false;

        if(config.get("followPlayer") != null)
            followPlayer = (boolean) config.get("followPlayer");
        else
            followPlayer = false;

        if(config.get("randomSpins") != null)
            randomSpins = (boolean) config.get("randomSpins");
        else
            randomSpins = true;

        if(config.get("yawOffset") != null)
            yawOffset = (double) config.get("yawOffset");
        else
            yawOffset = 0.0;
        if(config.get("playerExclusive") != null)
            playerExclusive = (boolean) config.get("playerExclusive");
        else
            playerExclusive = false;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        ret.put("yOffset", yOffset);
        ret.put("stepsPerTick", stepsPerTick);
        ret.put("speed", speed);
        ret.put("pitch", pitch);
        ret.put("fixedPitch", fixedPitch);
        ret.put("ySneakShift", ySneakShift);
        ret.put("followPlayerLocation", followPlayerLocation);
        ret.put("followPlayerYaw", followPlayerYaw);
        ret.put("followPlayerPitch", followPlayerPitch);
        ret.put("disableWhenMoving", disableWhenMoving);
        ret.put("disableWhenStill", disableWhenStill);
        ret.put("followPlayer", followPlayer);
        ret.put("randomSpins", randomSpins);
        ret.put("yawOffset", yawOffset);
        ret.put("playerExclusive", playerExclusive);
        return ret;
    }

    public String getInfo() {
        String info = "ParticlePlayer: " + effect.getName();
        if(fixedPitch) info += ", fp = " + pitch;
        if(yOffset != 0.0) info += ", yo = " + yOffset;
        if(yawOffset != 0.0) info += ", yawo = " + yawOffset;
        if(ySneakShift != 0.0) info += ", sys = " + ySneakShift;
        if(stepsPerTick != 1.0) info += ", steps = " + stepsPerTick;
        if(speed != 1.0) info += ", speed = " + speed;
        if(followPlayer) info += ", fp";
        if(followPlayerLocation) info += ", fpl";
        if(followPlayerYaw) info += ", fpy";
        if(followPlayerPitch) info += ", fpp";
        if(disableWhenMoving) info += ", dwm";
        if(disableWhenStill) info += ", dws";
        if(playerExclusive) info += ", pex";
        return info;
    }
    
    public boolean process(PlayerInteractEvent rawEvent) {
        PlayerInteractEvent event = (PlayerInteractEvent) rawEvent;
        Player player = event.getPlayer();
        playFor(player, 0, null);
        return true;
    }

    public void playFor(Player player, int stopAt, String group) {

        if(playerExclusive) EffectManager.getInstance().abortRunningEffects(player, effect);

        Location loc = player.getLocation().clone();
	loc.setY(loc.getY() + yOffset);
        loc.setYaw(loc.getYaw() + (float)yawOffset);
        if(player.isSneaking()) loc.setY(loc.getY() + ySneakShift);
	if(fixedPitch) loc.setPitch((float)pitch);

	new ParticleEffectTimedRunnable(Effects.getInstance(), player, effect, stepsPerTick, speed, loc, followPlayerLocation, followPlayerYaw, followPlayerPitch, disableWhenMoving, disableWhenStill, stopAt, followPlayer, randomSpins, 0, group).runTaskTimer(Effects.getInstance(), 1, 1);
        
    }
    
    public void playAt(Location location, int stopAt, String group) {
        Location loc = location.clone();
        loc.setY(loc.getY() + yOffset);
        if(fixedPitch) loc.setPitch((float)pitch);
        loc.setYaw(loc.getYaw() + (float)yawOffset);
	new ParticleEffectTimedRunnable(Effects.getInstance(), null, effect, stepsPerTick, speed, loc, false, false, false, false, false, stopAt, false, false, 0, group).runTaskTimer(Effects.getInstance(), 1, 1);
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect; // TODO, this needs an overhaul anyways
    }

    public boolean alwaysActive() {
        return false;
    }
}
