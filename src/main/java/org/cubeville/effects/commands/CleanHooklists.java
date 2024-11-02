package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;

public class CleanHooklists extends Command
{
    public CleanHooklists() {
        super("cleanhooklists");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        Registry.getInstance().cleanHooklists();
        CommandUtil.saveConfig();

        return null;
    }
}
