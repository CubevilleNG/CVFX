package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.Effects;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ParticleEffectTimedRunnable;

public class ListRunningEffectsCommand extends BaseCommand
{
    public ListRunningEffectsCommand() {
        super("list running");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        CommandResponse response = new CommandResponse("&eCurrently running effects (id, name, group):");

        List<ParticleEffectTimedRunnable> l = EffectManager.getInstance().getRunningEffects();
        if(l.size() == 0) throw new CommandExecutionException("No running effects.");

        for(ParticleEffectTimedRunnable runnable: l) {
            String properties = "";
            if(runnable.getGroup() != null) properties += "Group: " + runnable.getGroup();
            if(runnable.getPlayer() != null) {
                if(properties.length() > 0) properties += ", ";
                properties += runnable.getPlayer().getDisplayName();
            }
            if(properties.length() > 0) properties = " (" + properties + ")";
            response.addMessage(runnable.getId() + ": " + runnable.getEffect().getName() + properties);
        }

        return response;
    }

}
