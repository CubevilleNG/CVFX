package org.cubeville.effects.hooklists;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Hooklist implements ConfigurationSerializable
{
    private String displayName = null;
    
    public Hooklist() {}
    
    public Hooklist(Map<String, Object> config) {
        if (config.containsKey("displayName")) {
            displayName = (String) config.get("displayName");
        }
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        if (displayName != null) {
            map.put("displayName", displayName);
        }
        return map;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
