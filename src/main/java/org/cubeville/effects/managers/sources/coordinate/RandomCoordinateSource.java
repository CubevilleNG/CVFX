package org.cubeville.effects.managers.sources.coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;
import org.cubeville.effects.util.Conversions;

@SerializableAs("RandomCoordinateSource")
public class RandomCoordinateSource implements CoordinateSource
{
    private int count;
    private Vector min;
    private Vector max;

    public RandomCoordinateSource(int count, Vector min, Vector max) {
	this.count = count;
	this.min = min;
	this.max = max;
    }

    public RandomCoordinateSource(Map<String, Object> config) {
	count = (int) config.get("count");
	min = Conversions.getVectorFromString((String) config.get("min"));
	max = Conversions.getVectorFromString((String) config.get("max"));
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	ret.put("count", count);
	ret.put("min", Conversions.getVectorAsString(min));
	ret.put("max", Conversions.getVectorAsString(max));
	return ret;
    }
    
    public List<Vector> getVertices(int step, int nr) {
	List<Vector> ret = new ArrayList<>();
	for(int i = 0; i < count; i++) {
	    ret.add(new Vector(Math.random() * (max.getX() - min.getX()) + min.getX(),
			       Math.random() * (max.getY() - min.getY()) + min.getY(),
			       Math.random() * (max.getZ() - min.getZ()) + min.getZ()));
	}
	return ret;
    }

    public String getInfo(boolean detailed) {
	return "Random " + count;
    }
}
