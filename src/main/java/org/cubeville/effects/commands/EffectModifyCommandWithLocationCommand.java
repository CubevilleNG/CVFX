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

public class EffectModifyCommandWithLocationCommand extends Command
{
    public EffectModifyCommandWithLocationCommand() {
        super("effect modify");
        addBaseParameter(new CommandParameterEffect(CommandWithLocationEffect.class));
        addBaseParameter(new CommandParameterString());
        setPermission("fx.createcommand");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        CommandWithLocationEffect effect = (CommandWithLocationEffect) baseParameters.get(0);
        effect.modify((String) baseParameters.get(1));

        CommandUtil.saveConfig();
        return new CommandResponse("Command successfully modified.");
    }
}
