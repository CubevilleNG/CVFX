package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.DamageOtherEntityHookEventAndDamager;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.PiercingEffect;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateDamageOtherEntityEventAndDamagerCommand extends HookCommand
{
    public HookCreateDamageOtherEntityEventAndDamagerCommand() {
        super("hook create damage eventanddamager");
        addBaseParameter(new CommandParameterEffect(PiercingEffect.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        Integer id = getHooklistID(player, parameters);
        Effect effect = (Effect) baseParameters.get(0);
        Registry.getInstance().registerEvent(id, new DamageOtherEntityHookEventAndDamager(effect));
        CommandUtil.saveConfig();
        return null;
    }
}


    
