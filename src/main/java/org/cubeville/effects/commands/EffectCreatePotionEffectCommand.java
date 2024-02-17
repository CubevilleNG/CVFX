package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterBoolean;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterPotionEffectType;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.PotionEffect;
import org.cubeville.effects.managers.EffectManager;

public class EffectCreatePotionEffectCommand extends Command
{
    public EffectCreatePotionEffectCommand() {
        super("effect create potioneffect");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterPotionEffectType());
        addBaseParameter(new CommandParameterInteger());
        addBaseParameter(new CommandParameterInteger());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) throw new CommandExecutionException("Effect with name " + name + " already exists!");
        PotionEffectType effectType = (PotionEffectType) baseParameters.get(1);
        PotionEffect effect = new PotionEffect(name, effectType, (int) baseParameters.get(3), (int) baseParameters.get(2));
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return null;
    }
}
