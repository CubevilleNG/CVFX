package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterBoolean;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.managers.NPCEffect;

import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;

public class EffectModifyNPCCommand extends Command {
    public EffectModifyNPCCommand() {
        super("effect modify");
        setPermission("fx.npc");
        addBaseParameter(new CommandParameterEffect(NPCEffect.class));
        addParameter("spawn", true, new CommandParameterBoolean());
        addParameter("teleport", true, new CommandParameterBoolean());
        addParameter("despawn", true, new CommandParameterBoolean());
        addParameter("equip", true, new CommandParameterEnum(EquipmentSlot.class));
        addParameter("clearequipment", true, new CommandParameterBoolean());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        NPCEffect effect = (NPCEffect) baseParameters.get(0);

        if(parameters.containsKey("spawn")) {
            effect.setSpawn((boolean) parameters.get("spawn"), player.getLocation());
        }

        if(parameters.containsKey("despawn")) {
            effect.setDespawn((boolean) parameters.get("despawn"));
        }

        if(parameters.containsKey("teleport")) {
            effect.setTeleport((boolean) parameters.get("teleport"));
        }

        if(parameters.containsKey("equip")) {
            EquipmentSlot slot = (EquipmentSlot) parameters.get("equip");
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            effect.setEquipment(slot, item);
        }

        if(parameters.containsKey("clearequipment")) {
            effect.setClearEquipment((boolean) parameters.get("clearequipment"));
        }
            
        CommandUtil.saveConfig();

        return null;
    }
}
