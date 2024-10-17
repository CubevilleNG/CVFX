package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.effects.hooks.InteractHookTargetLocation;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLocation;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateInteractTargetLocationCommand extends HookCommand
{
    public HookCreateInteractTargetLocationCommand() {
        super("hook create interact targetlocation");
        addBaseParameter(new CommandParameterListEffect(EffectWithLocation.class));
        addOptionalBaseParameter(new CommandParameterListEffect(EffectWithLocation.class));
        addFlag("fixedpitch");
        addParameter("zoffset", true, new CommandParameterDouble());
        addFlag("origindir");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        List<Effect> noTargetParameters;
        if(baseParameters.size() < 2) {
            noTargetParameters = new ArrayList<>();
        }
        else {
            noTargetParameters = (List<Effect>) baseParameters.get(1);
        }

        boolean fixedPitch = flags.contains("fixedpitch");

        double zoffset = 0.0;
        if(parameters.containsKey("zoffset")) zoffset = (Double) parameters.get("zoffset");

        boolean origindir = flags.contains("origindir");
        
        Integer id = getHooklistID(player, parameters);
        Registry.getInstance().registerEvent(id, new InteractHookTargetLocation((List<Effect>) baseParameters.get(0), noTargetParameters, fixedPitch, zoffset, origindir));
        CommandUtil.saveConfig();
        return null;
    }
}
