package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.CommandParameterOnlinePlayer;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.Effects;

import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLivingEntity;

public class EffectPlayForPlayerCommand extends BaseCommand
{
    public EffectPlayForPlayerCommand() {
        super("effect playforplayer");
        addBaseParameter(new CommandParameterEffect(EffectWithLivingEntity.class));
        addBaseParameter(new CommandParameterOnlinePlayer());
        addFlag("silent");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        EffectWithLivingEntity effect = (EffectWithLivingEntity) baseParameters.get(0);

        effect.play((Player) baseParameters.get(1), null);

        return flags.contains("silent") ? new CommandResponse("") : null;
    }
}
