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
    private int runIndefinitely;
    private String group;

    public ParticleEffectTimedRunnable(JavaPlugin plugin, Player player, ParticleEffect effect, double stepsPerTick, double speed, Location location, boolean followPlayerLocation, boolean followPlayerYaw, boolean followPlayerPitch, boolean disableWhenMoving, boolean disableWhenStill, int stopAt, boolean followPlayerLocationCalculator, int runIndefinitely, String group)
    {
        runningEffectId = EffectManager.getInstance().getNewRunningEffectId();
        if(followPlayerLocationCalculator)
            locationCalculator = new FollowPlayerEffectLocationCalculator(player);
        else
            locationCalculator = new StraightParticleEffectLocationCalculator(location, speed, player, followPlayerLocation, followPlayerYaw, followPlayerPitch, disableWhenMoving, disableWhenStill);
	this.plugin = plugin;
	this.effect = effect;
	this.stepsPerTick = stepsPerTick;
        this.player = player;
        this.stopAt = stopAt;
        this.runIndefinitely = runIndefinitely;
        this.group = group;
	ticks = 0;
	step = 0;
        EffectManager.getInstance().registerRunningEffect(this);
    }

    public int getId() {
        return runningEffectId;
    }

    public Effect getEffect() {
        return effect;
    }

    public void abort() {
        effect.abort(runningEffectId);
        EffectManager.getInstance().deregisterRunningEffect(this);
        this.cancel();
    }

    public Player getPlayer() {
        return player;
    }
    
    @Override
    public void run() {

	ticks++;

        if(stopAt > 0 && ticks >= stopAt) {
            effect.abort(runningEffectId);
            EffectManager.getInstance().deregisterRunningEffect(this);
            this.cancel();
            return;
        }

	while(step + 1 < ticks * stepsPerTick) {

            if(!effect.play(step++, locationCalculator, player, runningEffectId)) {
                EffectManager.getInstance().deregisterRunningEffect(this);
                this.cancel();
                return;
            }

            if(runIndefinitely > 0 && step >= runIndefinitely) {
                step = 0;
                ticks = 0;
                return;
            }

        }
    }

    public String getGroup() { return group; }
}
