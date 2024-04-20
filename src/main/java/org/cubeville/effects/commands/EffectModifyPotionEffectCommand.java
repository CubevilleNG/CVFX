package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterPotionEffectType;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.managers.PotionEffectEffect;

public class EffectModifyPotionEffectCommand extends Command {

    public EffectModifyPotionEffectCommand() {
        super("effect modify");
        addBaseParameter(new CommandParameterEffect(PotionEffectEffect.class));
        addBaseParameter(new CommandParameterPotionEffectType());
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterInteger());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
    throws CommandExecutionException {

        PotionEffectEffect effect = (PotionEffectEffect) baseParameters.get(0);
        effect.modify((PotionEffectType) baseParameters.get(1),
                      (Integer) baseParameters.get(3),
                      (Integer) baseParameters.get(2));

        CommandUtil.saveConfig();
        return new CommandResponse("Effect successfully modified.");
    }

}
