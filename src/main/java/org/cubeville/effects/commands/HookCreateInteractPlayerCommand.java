package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.InteractHookPlayer;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLivingEntity;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateInteractPlayerCommand extends Command
{
    public HookCreateInteractPlayerCommand() {
	super("hook create interact player");
	addBaseParameter(new CommandParameterEffect(EffectWithLivingEntity.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Effect effect = (Effect) baseParameters.get(0);
        Registry.getInstance().registerEvent(itemName, new InteractHookPlayer(effect));
        CommandUtil.saveConfig();
        return new CommandResponse("Hook created.");
    }

}
