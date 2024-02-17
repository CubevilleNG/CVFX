package org.cubeville.effects.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("PermissionList")
public class PermissionList implements ConfigurationSerializable
{
    private Map<String, Set<UUID>> permissionList;

    public PermissionList() {
        permissionList = new HashMap<>();
    }

    public PermissionList(Map<String, Object> config) {
        permissionList = new HashMap<>();
        Map<String, List<String>> configPermissionList = (Map<String, List<String>>) config.get("permissionList");
        for(String permission: configPermissionList.keySet()) {
            permissionList.put(permission, new HashSet<UUID>());
            for(String member: configPermissionList.get(permission)) {
                permissionList.get(permission).add(UUID.fromString(member));
            }
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        Map<String, List<String>> perms = new HashMap<>();
        for(String permission: permissionList.keySet()) {
            List<String> members = new ArrayList<>();
            for(UUID uuid: permissionList.get(permission)) {
                members.add(uuid.toString());
            }
            perms.put(permission, members);
        }
        ret.put("permissionList", perms);
        return ret;
    }
    
    public boolean hasPermission(String permission, UUID player) {
        if(!permissionList.containsKey(permission)) return false;
        Set<UUID> pl = permissionList.get(permission);
        return pl.contains(player);
    }

    public void addPermission(String permission, UUID player) {
        if(!permissionList.containsKey(permission)) {
            permissionList.put(permission, new HashSet<>());
        }
        permissionList.get(permission).add(player);
    }

    public void removePermission(String permission, UUID player) {
        if(!permissionList.containsKey(permission)) throw new IllegalArgumentException("Permission " + permission + " does not exist.");
        if(!permissionList.get(permission).contains(player)) throw new IllegalArgumentException("Player does not have permission.");
        permissionList.get(permission).remove(player);
        if(permissionList.get(permission).size() == 0) permissionList.remove(permission);
    }

    public Set<String> getPermissionList() {
        return permissionList.keySet();
    }

    public Set<UUID> getPlayersByPermission(String permission) {
        if(!permissionList.containsKey(permission)) return new HashSet<>();
        return permissionList.get(permission);
    }

    public boolean permissionExists(String permission) {
        return permissionList.containsKey(permission);
    }
}
