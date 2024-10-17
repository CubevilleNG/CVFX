package org.cubeville.effects.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.cubeville.effects.Effects;
import org.cubeville.effects.hooklists.Hooklist;
import org.cubeville.effects.hooklists.HooklistRegistry;
import org.cubeville.effects.registry.Registry;

public class CommandUtil
{
    public static void saveConfig() {
        Effects.getInstance().saveEffects();
    }

    public static void clearPermissionCache() {
        Registry.getInstance().clearPermissionCache();
    }
    
    public static Integer createNewHooklist() {
        FileConfiguration config = Effects.getInstance().getConfig();
        Integer nextID = (Integer) config.get("next-hooklist-id", 0);
        Integer id = nextID;
        Hooklist hooklist = new Hooklist();
        HooklistRegistry.getInstance().register(id, hooklist);
        nextID++;
        config.set("next-hooklist-id", nextID);
        CommandUtil.saveConfig();
        return id;
    }
}
