package org.cubeville.effects.registry;

import org.cubeville.effects.hooks.DamageOtherEntityHook;

public class ProjectileDamageTracker
{
    private long launchTime;
    private RegistryHook<DamageOtherEntityHook> registryHook;
    
    public ProjectileDamageTracker(RegistryHook<DamageOtherEntityHook> registryHook) {
        this.registryHook = registryHook;
        launchTime = System.currentTimeMillis();
    }
    
    boolean isOverdue() {
        return System.currentTimeMillis() - launchTime > 30000;
    }

    RegistryHook<DamageOtherEntityHook> getRegistryHook() {
        return registryHook;
    }
}
