package org.cubeville.effects.commands;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.EffectManager;

public class EffectListCommand extends Command
{
    public EffectListCommand() {
	super("effect list");
        addBaseParameter(new CommandParameterString()); // Filter
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        player.sendMessage("--- Effect List ---");
        List<String> effects = EffectManager.getInstance().getEffectList();
        Collections.sort(effects);
        String filter = ((String) baseParameters.get(0)).toUpperCase();
        for(String e: effects) {
            if(filter.equals("ALL") || e.toUpperCase().indexOf(filter) >= 0) {
                player.sendMessage(e);
            }
        }
        return null;
    }

}
