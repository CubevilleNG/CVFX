package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.player.PlayerMoveEvent;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLocation;

@SerializableAs("MoveHookPlayerLocation")
public class MoveHookPlayerLocation implements MoveHook
{
    EffectWithLocation effect;
    boolean fixedPitch = false;
    
    public MoveHookPlayerLocation(Effect effect, boolean fixedPitch) {
	this.effect = (EffectWithLocation) effect;
        this.fixedPitch = fixedPitch;
    }

    public MoveHookPlayerLocation(Map<String, Object> config) {
        effect = (EffectWithLocation) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
        if(config.containsKey("fixedPitch")) {
            fixedPitch = (boolean)config.get("fixedPitch");
        }
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        ret.put("fixedPitch", fixedPitch);
        return ret;
    }

    public String getInfo() {
        return "PlayerLocation: " + effect.getName() + (fixedPitch ? " (fixed pitch)" : "");
    }
    
    public void process(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        if(fixedPitch) loc.setPitch(0);
	effect.play(loc);
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }
}
