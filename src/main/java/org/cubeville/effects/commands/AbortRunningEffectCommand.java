package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.Effects;
import org.cubeville.effects.managers.EffectManager;

public class AbortRunningEffectCommand extends BaseCommand
{
    public AbortRunningEffectCommand() {
        super("abort running");
        addBaseParameter(new CommandParameterInteger());
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        int id = (int) baseParameters.get(0);

        if(!EffectManager.getInstance().abortRunningEffect(id)) {
            throw new CommandExecutionException("Invalid id!");
        }

        return new CommandResponse("Effect aborted.");
    }
    
}
