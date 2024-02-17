package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.PiercingEffect;

public class EffectCreatePiercingCommand extends Command
{
    public EffectCreatePiercingCommand() {
        super("effect create piercing");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterEnum(PiercingEffect.PiercingType.class));
        addBaseParameter(new CommandParameterDouble());
        addFlag("stats");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        }
        PiercingEffect effect = new PiercingEffect(name, (PiercingEffect.PiercingType) baseParameters.get(1), (double) baseParameters.get(2), flags.contains("stats"));
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return null;
    }
}
