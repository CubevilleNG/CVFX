package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.ProjectileLaunchHookParticlePlayer;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateProjectileLaunchParticlePlayerCommand extends HookCommand
{
    public HookCreateProjectileLaunchParticlePlayerCommand() {
        super("hook create projectilelaunch particleplayer");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);
        Effect effect = (Effect) baseParameters.get(0);
        Registry.getInstance().registerEvent(id, new ProjectileLaunchHookParticlePlayer(effect.getName()));
        CommandUtil.saveConfig();
        return new CommandResponse("Hook created");
    }
}
