package org.cubeville.effects.commands;

import org.cubeville.effects.Effects;
import org.cubeville.effects.registry.Registry;

public class CommandUtil
{
    public static void saveConfig() {
        Effects.getInstance().saveEffects();
    }

    public static void clearPermissionCache() {
        Registry.getInstance().clearPermissionCache();
    }
}
