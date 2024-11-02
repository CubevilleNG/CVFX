package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.InteractHookPlayerEffect;

public class EffectCreateInteractHookPlayerCommand extends Command
{
    public EffectCreateInteractHookPlayerCommand() {
        super("effect create interacthookplayer");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterInteger());
        addParameter("stopat", true, new CommandParameterInteger());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null)
            throw new CommandExecutionException("Effect with name " + name + " already exists!");

        int hooklistId = (int) baseParameters.get(1);
        
        int stopat = 0;
        if(parameters.containsKey("stopat"))
            stopat = (int) parameters.get("stopat");

        InteractHookPlayerEffect effect = new InteractHookPlayerEffect(name, hooklistId, stopat);
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();

        return null;
    }
        
}
        
