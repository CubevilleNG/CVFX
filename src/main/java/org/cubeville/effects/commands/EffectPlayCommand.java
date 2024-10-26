package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import org.cubeville.commons.commands.CommandParameterVector;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.Effects;

import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLocation;

public class EffectPlayCommand extends BaseCommand
{
    public EffectPlayCommand() {
        super("effect play");
        addBaseParameter(new CommandParameterEffect(EffectWithLocation.class));
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterVector());
        addBaseParameter(new CommandParameterDouble());
        addBaseParameter(new CommandParameterDouble());
        addFlag("silent");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        EffectWithLocation effect = (EffectWithLocation) baseParameters.get(0);
        
        String worldName = (String) baseParameters.get(1);
        World world = Bukkit.getServer().getWorld(worldName);
        if(world == null)
            throw new CommandExecutionException("Invalid worldname!");

        Vector pos = (Vector) baseParameters.get(2);
        double yaw = (double) baseParameters.get(3);
        double pitch = (double) baseParameters.get(4);

        Location loc = new Location(world, pos.getX(), pos.getY(), pos.getZ(), (float) yaw, (float) pitch);

        effect.play(loc);

        return flags.contains("silent") ? new CommandResponse("") : null;
    }
}
