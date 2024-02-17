package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.CommandWithLocationEffect;
import org.cubeville.effects.managers.EffectManager;

public class EffectSave extends Command
{
    private String folder;
    
    public EffectSave(String folder) {
        super("effect save");
        addBaseParameter(new CommandParameterString());
        this.folder = folder;
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) == null) {
            throw new CommandExecutionException("Effect " + name + " not found.");
        };

        YamlConfiguration file = new YamlConfiguration();
        file.set("effect", EffectManager.getInstance().getEffectByName(name));
        try {
            file.save(folder + player.getUniqueId().toString());
            return(new CommandResponse("Effect saved."));
        }
        catch(Exception e) {
            throw new CommandExecutionException("Save failed.");
        }
    }
}
