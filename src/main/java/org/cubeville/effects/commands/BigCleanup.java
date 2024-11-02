package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;

public class BigCleanup extends Command
{
    public BigCleanup() {
        super("bigcleanup");
        addBaseParameter(new CommandParameterString());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        
        String prefix = (String) baseParameters.get(0);

        EffectManager em = EffectManager.getInstance();
        List<String> effectNames = new ArrayList<>();
        for(Effect e: em.getEffects()) {
            effectNames.add(e.getName());
        }

        for(String e: effectNames) {
            if(! e.startsWith(prefix)) {
                em.removeEffect(em.getEffectByName(e));
            }
        }

        // Now the hooks that have no effects anymore
        
        CommandUtil.saveConfig();
        
        
        // Now iterate over all hooks
        //   Iterate over all existing effects
        //     if a hook is used by the effect, continue with next hook
        //   delete hook

        return new CommandResponse("Cleanup done.");
    }

}
