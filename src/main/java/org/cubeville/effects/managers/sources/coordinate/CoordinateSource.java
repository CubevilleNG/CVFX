package org.cubeville.effects.managers.sources.coordinate;

import java.util.List;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

public interface CoordinateSource extends ConfigurationSerializable
{
    public List<Vector> getVertices(int step, int nr);
    public String getInfo(boolean detailed);
}
