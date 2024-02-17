package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.InteractHookPlayerLocation;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLocation;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateInteractPlayerLocationCommand extends Command
{
    public  HookCreateInteractPlayerLocationCommand() {
        super("hook create interact playerlocation");
        addBaseParameter(new CommandParameterEffect(EffectWithLocation.class));
        addFlag("fixedpitch");
        addParameter("yoffset", true, new CommandParameterDouble());
        addParameter("offset", true, new CommandParameterDouble());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Effect effect = (Effect) baseParameters.get(0);
        boolean fixedPitch = flags.contains("fixedpitch");
        double yoffset = 0.0;
        if(parameters.containsKey("yoffset")) yoffset = (Double) parameters.get("yoffset");
        double offset = 0.0;
        if(parameters.containsKey("offset")) offset = (Double) parameters.get("offset");
        
        Registry.getInstance().registerEvent(itemName, new InteractHookPlayerLocation(effect, fixedPitch, yoffset, offset));
        CommandUtil.saveConfig();
        return new CommandResponse("Hook created.");
    }
}

    
