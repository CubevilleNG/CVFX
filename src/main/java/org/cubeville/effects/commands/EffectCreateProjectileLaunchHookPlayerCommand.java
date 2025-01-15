package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ProjectileLaunchHookPlayerEffect;

public class EffectCreateProjectileLaunchHookPlayerCommand extends Command
{
    public EffectCreateProjectileLaunchHookPlayerCommand() {
        super("effect create projectilelaunchhookplayer");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterDouble());
        addFlag("snowball");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null)
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        
        int hooklistId = (int) baseParameters.get(1);
        double speed = (double) baseParameters.get(2);
        boolean snowball = flags.contains("snowball");

        ProjectileLaunchHookPlayerEffect effect = new ProjectileLaunchHookPlayerEffect(name, hooklistId, speed, snowball);
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();

        return null;
    }
}
