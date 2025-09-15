package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.InteractHookParticlePlayer;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.registry.Registry;

public class HookCreateInteractParticlePlayerCommand extends HookCommand
{
    public HookCreateInteractParticlePlayerCommand() {
        super("hook create interact particleplayer");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
        addParameter("fixedpitch", true, new CommandParameterDouble());
        addParameter("speed", true, new CommandParameterDouble());
        addParameter("step", true, new CommandParameterDouble());
        addParameter("yoffset", true, new CommandParameterDouble());
        addParameter("yawoffset", true, new CommandParameterDouble());
        addParameter("ysneakshift", true, new CommandParameterDouble());
        addFlag("followplayerlocation");
        addFlag("followplayeryaw");
        addFlag("followplayerpitch");
        addFlag("disablewhenmoving");
        addFlag("disablewhenstill");
        addFlag("followplayer");
        addFlag("randomspins");
        addFlag("playerexclusive");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);

        Effect effect = (Effect) baseParameters.get(0);

        double speed = 1.0;
        if(parameters.get("speed") != null) speed = (double) parameters.get("speed");

        double step = 1.0;
        if(parameters.get("step") != null) step = (double) parameters.get("step");
        if(step < 0.05) throw new CommandExecutionException("Step parameter must be at least 0.05!");

        double yoffset = 0.0;
        if(parameters.get("yoffset") != null) yoffset = (double) parameters.get("yoffset");

        boolean fixedpitch = parameters.containsKey("fixedpitch");
        double fixedpitchval = 0.0;
        if(fixedpitch) fixedpitchval = (double) parameters.get("fixedpitch");

        double ysneakshift = 0.0;
        if(parameters.get("ysneakshift") != null) ysneakshift = (double) parameters.get("ysneakshift");

        double yawOffset = 0.0;
        if(parameters.get("yawoffset") != null) yawOffset = (double) parameters.get("yawoffset");
        
        Registry.getInstance().registerEvent(id, new InteractHookParticlePlayer(effect.getName(), yoffset, yawOffset, step, speed, fixedpitch, fixedpitchval, ysneakshift, flags.contains("followplayerlocation"), flags.contains("followplayeryaw"), flags.contains("followplayerpitch"), flags.contains("disablewhenmoving"), flags.contains("disablewhenstill"), flags.contains("followplayer"), flags.contains("randomspins"), flags.contains("playerexclusive")));
        CommandUtil.saveConfig();

        return new CommandResponse("Hook created.");
    }
}
