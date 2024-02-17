package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.ParticleEffect;

public class EffectRemovecomponent extends Command
{
    public EffectRemovecomponent() {
        super("effect removecomponent");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
        addBaseParameter(new CommandParameterInteger(1, 25));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {
        ParticleEffect effect = (ParticleEffect) baseParameters.get(0);
        int componentIdx = (int) baseParameters.get(1) - 1;
        int currentSize = effect.getComponents().size();
        if(currentSize == 1) throw new CommandExecutionException("Effect can't have less than one component.");
        if(componentIdx > currentSize) throw new CommandExecutionException("Effect currently only has " + currentSize + " components!");
        effect.getComponents().remove(componentIdx);
        CommandUtil.saveConfig();
        return new CommandResponse("Effect component removed.");
    }
}
