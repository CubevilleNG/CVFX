package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;

public class HookListCommand extends HookCommand
{
    public HookListCommand() {
	super("hook list");
        addFlag("all");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        CommandResponse ret = new CommandResponse();
        if(flags.contains("all")) {
            ret.addMessage("--------------- Hooks ---------------");
            List<String> hooks = Registry.getInstance().getHookList();
            for(String h: hooks) ret.addMessage(h);
        }
        else {
            Integer id = getHooklistID(player, parameters, false);
            ret.addMessage("Hooks for hooklist " + id + ":");
            List<String> hooks = Registry.getInstance().getHookList(id);
            for(String h: hooks) ret.addMessage(h);
        }
        return ret;
    }
}
