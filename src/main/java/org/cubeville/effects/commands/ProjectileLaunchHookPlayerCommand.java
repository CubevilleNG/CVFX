package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Projectile;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterWorld;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterVector;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.hooks.ProjectileLaunchHook;

public class ProjectileLaunchHookPlayerCommand extends BaseCommand
{
    public ProjectileLaunchHookPlayerCommand() {
        super("projectilelaunchhookplayer");
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterWorld());
        addBaseParameter(new CommandParameterVector());
        addBaseParameter(new CommandParameterVector());
        addBaseParameter(new CommandParameterDouble());
        addFlag("snowball");
        addFlag("silent");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        Integer id = (Integer) baseParameters.get(0);
        List<ProjectileLaunchHook> hooks = Registry.getInstance().getProjectileLaunchHooksOfItem(id);

        World world = (World) baseParameters.get(1);

        Vector locvec = (Vector) baseParameters.get(2);
        Location location = new Location(world, locvec.getX(), locvec.getY(), locvec.getZ(), 0, 0);

        Vector direction = ((Vector) baseParameters.get(3)).normalize();
        double speed = (double) baseParameters.get(4);

        Projectile projectile = CommandUtil.launchProjectile(location, direction, speed, flags.contains("snowball"));
        for(ProjectileLaunchHook hook: hooks) {
            hook.runAt(location, projectile);
        }
        
        return flags.contains("silent") ? new CommandResponse("") : null;
    }
}
