package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.hooks.DamageOtherEntityHookTargetEntity;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLivingEntity;
import org.cubeville.effects.registry.Registry;
import org.cubeville.effects.util.ItemUtil;

public class HookCreateDamageOtherEntityLivingEntityCommand extends Command
{
    public HookCreateDamageOtherEntityLivingEntityCommand() {
        super("hook create damage entity");
        addBaseParameter(new CommandParameterEffect(EffectWithLivingEntity.class));
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {
        String itemName = ItemUtil.safeGetItemInMainHandName(player);
        Registry.getInstance().registerEvent(itemName, new DamageOtherEntityHookTargetEntity((EffectWithLivingEntity) baseParameters.get(0)));
        CommandUtil.saveConfig();
        return null;
    }
        
}
