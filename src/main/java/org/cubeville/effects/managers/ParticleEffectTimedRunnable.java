package org.cubeville.effects.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleEffectTimedRunnable extends BukkitRunnable
{
    private final JavaPlugin plugin;
    //private final Location location;
    private final ParticleEffect effect;
    private int step;
    private int ticks;
    private double stepsPerTick;
    //private double speed;
    private Player player;
    private ParticleEffectLocationCalculator locationCalculator;
    private final int runningEffectId;
    private final int stopAt;
    
    public ParticleEffectTimedRunnable(JavaPlugin plugin, Player player, ParticleEffect effect, double stepsPerTick, double speed, Location location, boolean followPlayerLocation, boolean followPlayerYaw, boolean followPlayerPitch, boolean disableWhenMoving, boolean disableWhenStill, int stopAt, boolean followPlayerLocationCalculator)
    {
        runningEffectId = EffectManager.getNewRunningEffectId();
        if(followPlayerLocationCalculator)
            locationCalculator = new FollowPlayerEffectLocationCalculator(player);
        else
            locationCalculator = new StraightParticleEffectLocationCalculator(location, speed, player, followPlayerLocation, followPlayerYaw, followPlayerPitch, disableWhenMoving, disableWhenStill);
	this.plugin = plugin;
	this.effect = effect;
	this.stepsPerTick = stepsPerTick;
        this.player = player;
        this.stopAt = stopAt;
	ticks = 0;
	step = 0;
    }

    @Override
    public void run() {
	ticks++;
        if(stopAt > 0 && ticks >= stopAt) {
            effect.abort(runningEffectId);
            this.cancel();
            return;
        }

	while(step + 1 < ticks * stepsPerTick) {
            if(!effect.play(step++, locationCalculator, player, runningEffectId)) {
                this.cancel();
                return;
            }
        }
    }
}
