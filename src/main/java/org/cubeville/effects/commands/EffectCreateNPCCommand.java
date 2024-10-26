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
import org.cubeville.effects.managers.NPCEffect;

public class EffectCreateNPCCommand extends Command
{
    public EffectCreateNPCCommand() {
        super("effect create npc");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterInteger());
        addFlag("spawn");
        addFlag("despawn");
        addFlag("teleport");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        };
        
        int id = (int) baseParameters.get(1);

        NPCEffect effect = new NPCEffect(name, id);

        if(flags.contains("spawn"))
            effect.setSpawn(true, player.getLocation());
        if(flags.contains("despawn"))
            effect.setDespawn(true);
        if(flags.contains("teleport"))
            effect.setTeleport(true);
        
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();

        return null;

    }
}
