package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("NPCTeleportEffect")
public class NPCTeleportEffect extends EffectWithLocation
{
    private int npcId;

    public NPCTeleportEffect(String name, int npcId) {
        setName(name);
        this.npcId = npcId;
    }

    public NPCTeleportEffect(Map<String, Object> config) {
        npcId = (int) config.get("npcId");
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = getSerializationBase();
        ret.put("npcId", npcId);
        return ret;
    }

    public void play(Location location) {
        // NPC.teleport(location);
    }

    public List<String> getInfo(boolean detailed, String limit) {
	List<String> ret = getInfoBase();
	ret.add("NPC Id: " + npcId);
	return ret;
    }

    public String getType() {
        return "NPCTeleport";
    }
            
}
