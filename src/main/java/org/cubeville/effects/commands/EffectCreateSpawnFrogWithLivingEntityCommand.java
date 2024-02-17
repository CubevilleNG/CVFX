package org.cubeville.effects.commands;

import org.bukkit.entity.Frog;
import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.SpawnFrogWithLivingEntityEffect;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EffectCreateSpawnFrogWithLivingEntityCommand extends Command {

    public EffectCreateSpawnFrogWithLivingEntityCommand() {
        super("effect create spawnfrogwithlivingentity");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterString());
        setPermission("fx.spawnentity");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        }
        String varient = (String) baseParameters.get(1);
        SpawnFrogWithLivingEntityEffect effect;
        if(varient.equalsIgnoreCase("cold")) {
            effect = new SpawnFrogWithLivingEntityEffect(name, Frog.Variant.COLD);
        } else if(varient.equalsIgnoreCase("temperate")) {
            effect = new SpawnFrogWithLivingEntityEffect(name, Frog.Variant.TEMPERATE);
        } else if(varient.equalsIgnoreCase("warm")) {
            effect = new SpawnFrogWithLivingEntityEffect(name, Frog.Variant.WARM);
        } else {
            throw new CommandExecutionException("Valid varients are cold, temperate, or warm!");
        }
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return new CommandResponse("&aCommand effect created. &cEffect can be used and run by any admin, please be cautious with the spawns that you use!");
    }
}
