package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.BlockBreakHookParticlePlayer;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateBlockBreakParticlePlayerCommand extends HookCommand {

    public HookCreateBlockBreakParticlePlayerCommand() {
        super("hook create blockbreak particleplayer");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
        addParameter("speed", true, new CommandParameterDouble());
        addParameter("step", true, new CommandParameterDouble());
        addParameter("yoffset", true, new CommandParameterDouble());
        addParameter("pitch", true, new CommandParameterDouble());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);

        Effect effect = (Effect) baseParameters.get(0);
        double speed = 1.0;
        if(parameters.get("speed") != null) speed = (double) parameters.get("speed");
        double step = 1.0;
        if(parameters.get("step") != null) step = (double) parameters.get("step");
        double yoffset = 0.0;
        if(parameters.get("yoffset") != null) yoffset = (double) parameters.get("yoffset");
        double pitch = 0.0;
        if(parameters.get("pitch") != null) pitch = (double) parameters.get("pitch");
        
        Registry.getInstance().registerEvent(id, new BlockBreakHookParticlePlayer(effect.getName(), yoffset, speed, step, pitch));
        CommandUtil.saveConfig();

        return new CommandResponse("Hook created.");        
    }
}
