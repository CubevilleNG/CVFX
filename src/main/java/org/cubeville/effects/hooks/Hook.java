package org.cubeville.effects.hooks;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.cubeville.effects.managers.Effect;

public interface Hook extends ConfigurationSerializable
{
    public String getInfo();
    public boolean usesEffect(Effect effect);
    public boolean alwaysActive();
}
