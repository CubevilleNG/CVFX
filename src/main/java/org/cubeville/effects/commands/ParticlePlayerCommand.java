package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterVector;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.Effects;

import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectTimedRunnable;

public class ParticlePlayerCommand extends BaseCommand
{
    public ParticlePlayerCommand() {
        super("particleplayer");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterDouble()); // stepsPerTick
        addBaseParameter(new CommandParameterDouble()); // speed
        addBaseParameter(new CommandParameterString()); // world
        addBaseParameter(new CommandParameterVector()); // location
        addBaseParameter(new CommandParameterDouble()); // yaw
        addBaseParameter(new CommandParameterDouble()); // pitch
        addParameter("stopat", true, new CommandParameterInteger());
	addFlag("silent");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        ParticleEffect effect;
        {
            Effect e = EffectManager.getInstance().getEffectByName(name);
            if(e == null) throw new CommandExecutionException("Effect " + name + " does not exist!");
            if(!(e instanceof ParticleEffect)) throw new CommandExecutionException("Effect " + name + " is not a particle effect!");
            effect = (ParticleEffect) e;
        }
        double stepsPerTick = (Double) baseParameters.get(1);
        double speed = (Double) baseParameters.get(2);
        String worldName = (String) baseParameters.get(3);
        Vector location = (Vector) baseParameters.get(4);
        float yaw = (float) ((double) baseParameters.get(5));
        float pitch = (float) ((double) baseParameters.get(6));

        World world = Bukkit.getWorld(worldName);
        if(world == null) throw new CommandExecutionException("World " + world + " does not exist!");
        Location loc = new Location(world, location.getX(), location.getY(), location.getZ(), yaw, pitch);

        int stopat = 0;
        if(parameters.containsKey("stopat"))
            stopat = (int) parameters.get("stopat");

        new ParticleEffectTimedRunnable(Effects.getInstance(), null, effect, stepsPerTick, speed, loc, false, false, false, false, false, stopat, false).runTaskTimer(Effects.getInstance(), 1, 1);

	if(flags.contains("silent")) {
	    return new CommandResponse("");
	}
	else {
	    return null;
	}
    }
}
