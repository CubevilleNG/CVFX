package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Snowball;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Arrow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import org.cubeville.commons.commands.CommandParameterWorld;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterVector;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.Effects;

import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectProjectileRunnable;

public class ProjectileParticlePlayerCommand extends BaseCommand
{
    public ProjectileParticlePlayerCommand() {
        super("projectileparticleplayer");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
        addBaseParameter(new CommandParameterWorld());
        addBaseParameter(new CommandParameterVector()); // location
        addBaseParameter(new CommandParameterVector()); // direction
        addBaseParameter(new CommandParameterDouble()); // speed
        addFlag("snowball");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        ParticleEffect effect = (ParticleEffect) baseParameters.get(0);
        World world = (World) baseParameters.get(1);
        Vector location = (Vector) baseParameters.get(2);
        Vector direction = ((Vector) baseParameters.get(3)).normalize();
        double speed = (double) baseParameters.get(4);

        Location llocation = new Location(world, location.getX(), location.getY(), location.getZ(), 0.0f, 0.0f);

        Projectile projectile = CommandUtil.launchProjectile(llocation, direction, speed, flags.contains("snowball"));
        new ParticleEffectProjectileRunnable(effect, projectile).runTaskTimer(Effects.getInstance(), 1, 1);

        return null;
    }
}
