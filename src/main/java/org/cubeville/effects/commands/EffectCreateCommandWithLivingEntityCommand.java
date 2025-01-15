package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.CommandWithLivingEntityEffect;
import org.cubeville.effects.managers.EffectManager;

public class EffectCreateCommandWithLivingEntityCommand extends Command
{
    public EffectCreateCommandWithLivingEntityCommand() {
        super("effect create commandwithlivingentity");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterString());
        addParameter("delay", true, new CommandParameterInteger());
        addFlag("runasentity");
        setPermission("fx.createcommand");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        };

        int delay = 0;
        if(parameters.containsKey("delay"))
            delay = (int) parameters.get("delay");
        
        CommandWithLivingEntityEffect effect = new CommandWithLivingEntityEffect(name, (String) baseParameters.get(1), flags.contains("runasentity"), delay);
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return new CommandResponse("&aCommand effect created. &cEffect can be used and run by any admin, please be cautious with the commands you use!");
    }
}
