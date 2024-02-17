package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.CommandWithLocationEffect;
import org.cubeville.effects.managers.EffectManager;

public class EffectCreateCommandWithLocationCommand extends Command
{
    public EffectCreateCommandWithLocationCommand() {
        super("effect create commandwithlocation");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterString());
        setPermission("fx.createcommand");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        };

        CommandWithLocationEffect effect = new CommandWithLocationEffect(name, (String) baseParameters.get(1));
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return new CommandResponse("&aCommand effect created. &cEffect can be used and run by any admin, please be cautious with the commands you use!");
    }
}
