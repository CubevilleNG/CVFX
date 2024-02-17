package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.BrokenWandEffect;

public class EffectCreateBrokenWandEffectCommand extends Command
{
    public EffectCreateBrokenWandEffectCommand() {
        super("effect create brokenwand");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterDouble());
        addBaseParameter(new CommandParameterDouble());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        };

        BrokenWandEffect effect = new BrokenWandEffect(name,
                                                       (Integer) baseParameters.get(1),
                                                       (Integer) baseParameters.get(2),
                                                       (Double) baseParameters.get(3),
                                                       (Double) baseParameters.get(4));
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return null;
    }

}
