package org.cubeville.effects.hooklists;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.cubeville.effects.Effects;
import org.cubeville.effects.commands.CommandUtil;
import org.enginehub.linbus.stream.token.LinToken;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HooklistRegistry implements ConfigurationSerializable {
    
    private static HooklistRegistry instance;
    private Map<Integer, Hooklist> hooklistMap;
    
    public HooklistRegistry() {
        hooklistMap = new HashMap<>();
        instance = this;
    }
    
    public HooklistRegistry(Map<String, Object> config) {
        hooklistMap = new HashMap<>();
        for (String string : config.keySet()) {
            try {
                hooklistMap.put(Integer.parseInt(string), (Hooklist) config.get(string));
            } catch (NumberFormatException e) {
            }
        }
        instance = this;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        for (Integer id : hooklistMap.keySet()) {
            map.put(id.toString(), hooklistMap.get(id));
        }
        return map;
    }
    
    public static HooklistRegistry getInstance() {
        return instance;
    }
    
    public Integer createNewHooklist() {
        FileConfiguration config = Effects.getInstance().getConfig();
        Integer nextID = (Integer) config.get("next-hooklist-id", 0);
        Integer id = nextID;
        Hooklist hooklist = new Hooklist();
        register(id, hooklist);
        nextID++;
        config.set("next-hooklist-id", nextID);
        CommandUtil.saveConfig();
        return id;
    }
    
    public void register(Integer id, Hooklist hooklist) {
        hooklistMap.put(id, hooklist);
    }
    
    public void deregister(Integer id, Hooklist hooklist) {
        hooklistMap.put(id, hooklist);
    }
    
    public boolean containsID(Integer id) {
        return hooklistMap.containsKey(id);
    }
    
    public Hooklist getHooklist(Integer id) {
        return hooklistMap.get(id);
    }
    
    @Nullable
    public Integer getIDFromName(String name) {
        for (Integer id : hooklistMap.keySet()) {
            String displayName = hooklistMap.get(id).getDisplayName();
            if (displayName == null) {
                continue;
            }
            if (displayName.equals(name)) {
                return id;
            }
        }
        return null;
    }
}
