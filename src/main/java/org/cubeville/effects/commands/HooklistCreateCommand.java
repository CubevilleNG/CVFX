package org.cubeville.effects.commands;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.util.ItemUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HooklistCreateCommand extends Command
{
    public HooklistCreateCommand() {
        super("hooklist create");
    }
    
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = HooklistRegistry.getInstance().createNewHooklist();
        
        return new CommandResponse("Hooklist " + id + " created.\nUse '/fx hooklist addtoitem " + id + "' to add the hooklist to your held item.");
    }
}