package org.cubeville.effects.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.Effects;
import org.cubeville.effects.hooklists.Hooklist;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.managers.EventListener;
import org.cubeville.effects.registry.Registry;

import java.util.*;

public class ConvertConfigCommand extends Command {
    
    public ConvertConfigCommand() {
        super("convertconfig");
        setPermission("fx.convertconfig");
    }
    
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        
        FileConfiguration config = Effects.getInstance().getConfig();
        if (config.get("config-version", 0).equals(2)) {
            CommandResponse response = new CommandResponse("Already up to date!");
            return response;
        }
        
        Integer nextID = (Integer) config.get("next-hooklist-id", 0);
        
        // Collect unique item names
        Registry r = Registry.getInstance();
        Set<String> allNames = new HashSet<>();
        Map<String, Object> serializedRegistry = r.serialize();
        Set<String> keys = serializedRegistry.keySet();
        for (String type : keys) {
            if (type.equals("permissionList")) continue;
            Map<Object, Object> info = (Map<Object, Object>) serializedRegistry.get(type);
            for (Object belowKey : info.keySet()) {
                String key;
                if (belowKey instanceof Integer) {
                    Integer i = (Integer) belowKey;
                    key = i.toString();
                } else {
                    key = (String) belowKey;
                }
                allNames.add(key);
            }
        }
        
        Map<String, Integer> conversionMap = new HashMap<>();
        HooklistRegistry hr = HooklistRegistry.getInstance();
        
        for (String name : allNames) {
            conversionMap.put(name, nextID);
            Hooklist hooklist = new Hooklist();
            hooklist.setDisplayName(name);
            hr.register(nextID, hooklist);
            nextID++;
            config.set("next-hooklist-id", nextID);
        }
        
        Map<String, Object> newSerializedRegistry = new HashMap<>(serializedRegistry);
        keys = newSerializedRegistry.keySet();
        
        for (String type : keys) {
            if (type.equals("permissionList")) continue;
            Map<Object, Object> info = (Map<Object, Object>) newSerializedRegistry.get(type);
            Map<Object, Object> newInfo = new HashMap<>(info);
            for (Object belowKey : info.keySet()) {
                Object obj = newInfo.get(belowKey);
                newInfo.put(conversionMap.get(belowKey), obj);
                newInfo.remove(belowKey);
            }
            newSerializedRegistry.put(type, newInfo);
        }
        serializedRegistry = newSerializedRegistry;
        Registry newRegistry = new Registry(serializedRegistry);
        config.set("Registry", newRegistry);
        config.set("config-version", 2);
        EventListener.getInstance().setRegistry(newRegistry);
        config.set("HooklistRegistry", hr);
        
        Effects.getInstance().saveConfig();
        
        return new CommandResponse("Complete!");
    }
}
