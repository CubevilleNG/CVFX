package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.registry.Registry;

public class PermissionListCommand extends BaseCommand
{
    public PermissionListCommand() {
        super("permission list");
        addOptionalBaseParameter(new CommandParameterString());
    }

    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        if(baseParameters.size() == 0) { // List available permissions
            List<String> permissions = new ArrayList<String>(Registry.getInstance().getPermissionList().getPermissionList());
            Collections.sort(permissions);
            for(String p: permissions) {
                sender.sendMessage(p);
            }
        }
        else {
            List<UUID> players = new ArrayList<UUID>(Registry.getInstance().getPermissionList().getPlayersByPermission((String) baseParameters.get(0)));
            Collections.sort(players);
            for(UUID p: players) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(p);
                sender.sendMessage(p.toString() + ": " + op.getName());
            }
        }
        return null;
    }
}
