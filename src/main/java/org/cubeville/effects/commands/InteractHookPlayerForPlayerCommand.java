package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterOnlinePlayer;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.hooks.InteractHook;
import org.cubeville.effects.registry.Registry;

public class InteractHookPlayerForPlayerCommand extends BaseCommand
{
    public InteractHookPlayerForPlayerCommand() {
        super("interacthookplayer");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterOnlinePlayer());
        addParameter("stopat", true, new CommandParameterInteger());
        addFlag("silent");
    }

    public CommandResponse execute(CommandSender player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        String itemname = (String) baseParameters.get(0);
        String citemname = itemname.replace('&', '§');
        List<InteractHook> hooks = Registry.getInstance().getInteractHooksOfItem(citemname);

        Player forPlayer = (Player) baseParameters.get(1);

        int stopat = 0;
        if(parameters.containsKey("stopat"))
            stopat = (int) parameters.get("stopat");

        for(InteractHook hook: hooks) {
            hook.playFor(forPlayer, stopat);
        }

        if(flags.contains("silent"))
            return new CommandResponse("");
        else
            return null;
    }        
}
