package org.cubeville.effects.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.cubeville.effects.hooks.Hook;

@SerializableAs("RegistryHook")
public class RegistryHook<T extends Hook> implements ConfigurationSerializable
{
    private List<T> hooks;
    private Map<UUID, PlayerData> playerData;
    private long cooldownTime;
    private String permission;
    private boolean keepInInventory;
    
    protected class PlayerData {
        PlayerData(boolean hasPermission) { this.hasPermission = hasPermission; }
        private boolean hasPermission;
        private long lastUsed = 0;
    }

    public RegistryHook() {
        hooks = new ArrayList<>();
        playerData = new HashMap<>();
        cooldownTime = 0;
        keepInInventory = false;
    }

    public RegistryHook(Map<String, Object> config) {
        cooldownTime = (int) config.get("cooldownTime");
        permission = (String) config.get("permission");
        hooks = (List<T>) config.get("hooks");
        playerData = new HashMap<>();
        if(config.get("keepInInventory") != null) keepInInventory = (boolean) config.get("keepInInventory"); else keepInInventory = false;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("cooldownTime", cooldownTime);
        ret.put("permission", permission);
        ret.put("hooks", hooks);
        ret.put("keepInInventory", keepInInventory);
        return ret;
    }
    
    public List<T> getHooks() {
        return hooks;
    }

    public boolean isPermitted(UUID uuid) {
        if(permission == null && cooldownTime == 0) return true;

        PlayerData pd = playerData.get(uuid);
        if(pd == null) {
            if(permission == null) {
                pd = new PlayerData(true);
            }
            else {
                boolean hasPermission = Registry.getInstance().getPermissionList().hasPermission(permission, uuid);
                pd = new PlayerData(hasPermission);
            }
            playerData.put(uuid, pd);
        }

        boolean hasPermission = pd.hasPermission;
        
        boolean needCooldown;
        if(cooldownTime == 0) {
            needCooldown = false;
        }
        else {
            long ts = System.currentTimeMillis();
            if(ts - pd.lastUsed > cooldownTime) {
                needCooldown = false;
                pd.lastUsed = ts;
            }
            else {
                needCooldown = true;
            }
        }
        return needCooldown == false && hasPermission == true;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
    
    public void setCooldown(int cooldown) {
        this.cooldownTime = cooldown;
    }

    public long getCooldown() {
        return cooldownTime;
    }
    
    public void clearPermissionCache() {
        playerData = new HashMap<>();        
    }

    public boolean getKeepInInventory() {
        return keepInInventory;
    }

    public void setKeepInInventory(boolean keepInInventory) {
        this.keepInInventory = keepInInventory;
    }
}
