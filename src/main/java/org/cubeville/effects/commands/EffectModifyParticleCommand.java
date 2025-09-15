package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandParameterListInteger;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectComponent;

public class EffectModifyParticleCommand extends BaseCommand {

    public EffectModifyParticleCommand() {
        super("effect modify");
        addBaseParameter(new CommandParameterEffect(ParticleEffect.class));
        ParticleCommandHelper.addCommandParameters(this);
        addParameter("component", true, new CommandParameterListInteger());
    }

    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        if(parameters.size() == 0 && flags.size() == 0) throw new CommandExecutionException("No modification parameters.");

        ParticleEffect effect = (ParticleEffect) baseParameters.get(0);
        ParticleCommandHelper.setEffectValues(effect, parameters);

        List<Integer> componentIdx = (List<Integer>) parameters.get("component");
        if(componentIdx == null && effect.getComponents().size() > 1 && ParticleCommandHelper.hasOnlyEffectValues(parameters, flags) == false) throw new CommandExecutionException("Component index mandatory for effect with more than one component!");

        if(componentIdx == null) {
            componentIdx = new ArrayList<>();
            componentIdx.add(1);
        }
        else {
            Collections.sort(componentIdx);
            int virtualSize = effect.getComponents().size();
            for(Integer i: componentIdx) {
                if(i < 1 || i > virtualSize + 1) throw new CommandExecutionException(i + " is not a valid component index!");
                if(i == virtualSize + 1) virtualSize++;
            }
        }

        CommandResponse response = new CommandResponse();

        boolean create = false;
        for(Integer i: componentIdx) {
            int currentSize = effect.getComponents().size();
            if(i == currentSize + 1) {
                effect.addComponent(new ParticleEffectComponent());
                create = true;
            }
            ParticleEffectComponent component = effect.getComponents().get(i - 1);
            try {
                Player player = null;
                if(sender instanceof Player) player = (Player) sender;
                ParticleCommandHelper.setComponentValues(component, parameters, flags, player, effect);
            }
            catch(IllegalArgumentException e) {
                response.addMessage("&cComponent " + i + ": " + e.getMessage());
            }
            
        }

        CommandUtil.saveConfig();
        
        response.addMessage("&aEffect creation/modification finished.");
        return response;
    }
}
