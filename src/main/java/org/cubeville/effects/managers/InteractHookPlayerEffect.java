package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import org.cubeville.effects.Effects;
import org.cubeville.effects.hooks.InteractHook;
import org.cubeville.effects.registry.Registry;

@SerializableAs("InteractHookPlayerEffect")
public class InteractHookPlayerEffect extends EffectWithLocation
{
    private int hooklist;
    private int stopat;
    
    public InteractHookPlayerEffect(String name, int hooklist, int stopat) {
        setName(name);
        this.hooklist = hooklist;
        this.stopat = stopat;
    }

    public InteractHookPlayerEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
        hooklist = (int) config.get("hooklist");
        stopat = (int) config.get("stopat");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("hooklist", hooklist);
        ret.put("stopat", stopat);
        return ret;
    }

    public void play(Location location) {
        List<InteractHook> hooks = Registry.getInstance().getInteractHooksOfItem(hooklist);
        for(InteractHook hook: hooks)
            hook.playAt(location, stopat, null);
    }

    public List<String> getInfo(boolean detailed, String limit) {
        List<String> ret = getInfoBase();
        ret.add("Hooklist: " + hooklist);
        ret.add("Stop at: " + stopat);
        return ret;
    }

    public String getType() {
        return "InteractHookPlayer";
    }
}
