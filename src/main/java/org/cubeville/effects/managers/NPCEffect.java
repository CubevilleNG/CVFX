package org.cubeville.effects.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;

@SerializableAs("NPCEffect")
public class NPCEffect extends EffectWithLocation
{
    private int npcId;
    boolean spawn = false;
    Location spawnLocation;
    boolean despawn = false;
    boolean teleport = false;

    HashMap<String, ItemStack> equipment;
    boolean clearEquipment = false;
    
    public NPCEffect(String name, int npcId) {
        setName(name);
        this.npcId = npcId;
    }

    public NPCEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
        spawn = (boolean) config.get("spawn");
        if(spawn)
            spawnLocation = (Location) config.get("spawnLocation");
        despawn = (boolean) config.get("despawn");
        teleport = (boolean) config.get("teleport");
        equipment = (HashMap<String, ItemStack>) config.get("equipment");
        if(config.get("clearEquipment") != null) clearEquipment = (boolean) config.get("clearEquipment");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("spawn", spawn);
        if(spawn)
            ret.put("spawnLocation", spawnLocation);
        ret.put("despawn", despawn);
        ret.put("teleport", teleport);
        if(equipment != null) ret.put("equipment", equipment);
        ret.put("clearEquipment", clearEquipment);
        return ret;
    }

    public void setSpawn(boolean spawn, Location spawnLocation) {
        this.spawn = spawn;
        if(spawn)
            this.spawnLocation = spawnLocation;
        else
            this.spawnLocation = null;
    }

    public void setDespawn(boolean despawn) {
        this.despawn = despawn;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public void setEquipment(EquipmentSlot slot, ItemStack item) {
        if(item == null) {
            if(equipment != null) {
                if(equipment.keySet().size() == 1)
                    equipment = null;
                else
                    equipment.remove(slot.toString());
            }
        }
        else {
            if(equipment == null)
                equipment = new HashMap<>();
            equipment.put(slot.toString(), item);
        }
    }

    public void setClearEquipment(boolean clearEquipment) { this.clearEquipment = clearEquipment; }
    public boolean isClearEquipment() { return clearEquipment; }
    
    public boolean isSpawn() { return spawn; }
    public boolean isDespawn() { return despawn; }

    public void play(Location location) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
        if(npc == null) throw new RuntimeException("Unable to find NPC with id " + npcId);

        if(spawn) {
            if(npc.isSpawned()) {
                if(!teleport)
                    npc.teleport(spawnLocation, TeleportCause.PLUGIN);
                else {
                    npc.teleport(location, TeleportCause.PLUGIN);
                }
            }
            else {
                if(teleport) {
                    npc.spawn(location);
                }
                else
                    npc.spawn(spawnLocation);
            }
        }

        if(npc.isSpawned() && clearEquipment) {
            // removeTrait for some reason won't work!?
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, null);
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE, null);
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HAND, null);
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, null);
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS, null);
            npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.OFF_HAND, null);
        }
        
        if(npc.isSpawned() && equipment != null) {
            for(String slot: equipment.keySet()) {
                npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.valueOf(slot), equipment.get(slot));
            }
        }
        
        else if(teleport && npc.isSpawned()) {
            npc.teleport(location, TeleportCause.PLUGIN);
        }

        if(despawn && npc.isSpawned()) npc.despawn();
    }

    public List<String> getInfo(boolean detailed, String limit) {
        List<String> ret = getInfoBase();
        ret.add("Spawn: " + (spawn ? "true" : "false"));
        ret.add("Despawn: " + (despawn ? "true" : "false"));
        ret.add("Teleport: " + (teleport ? "true" : "false"));
        ret.add("Clear equipment: " + (clearEquipment ? "true" : "false"));
                                       
        if(equipment != null) {
            ret.add("Equipment:");
            for(String slot: equipment.keySet()) {
                ret.add(slot + ": " + equipment.get(slot).getType());
            }
        }

        return ret;
    }

    public String getType() {
        return "NPC";
    }

}
