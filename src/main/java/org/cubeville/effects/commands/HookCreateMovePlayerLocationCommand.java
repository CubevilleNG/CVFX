package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.MoveHookPlayerLocation;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLocation;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateMovePlayerLocationCommand extends Command
{
    public  HookCreateMovePlayerLocationCommand() {
        super("hook create move playerlocation");
        addBaseParameter(new CommandParameterEffect(EffectWithLocation.class));
        addFlag("fixedpitch");
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Effect effect = (Effect) baseParameters.get(0);
        boolean fixedPitch = flags.contains("fixedpitch");
        Registry.getInstance().registerEvent(itemName, new MoveHookPlayerLocation(effect, fixedPitch));
        CommandUtil.saveConfig();
        return new CommandResponse("Hook created.");
    }
}
