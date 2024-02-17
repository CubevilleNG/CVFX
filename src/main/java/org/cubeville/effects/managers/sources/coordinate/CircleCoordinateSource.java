package org.cubeville.effects.managers.sources.coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

@SerializableAs("CircleCoordinateSource")
public class CircleCoordinateSource implements CoordinateSource
{
    private double radius;
    private double offset;
    private int startAngle;
    private int endAngle;
    private int stepAngle;
    private boolean xz;
    
    public CircleCoordinateSource(double radius, double offset, int stepAngle, int startAngle, int endAngle, boolean xz) {
	this.radius = radius;
	this.startAngle = startAngle;
	this.endAngle = endAngle;
	this.stepAngle = stepAngle;
        this.offset = offset;
	this.xz = xz;
    }

    public CircleCoordinateSource(Map<String, Object> config) {
	radius = (double) config.get("radius");
	startAngle = (int) config.get("startAngle");
	endAngle = (int) config.get("endAngle");
	stepAngle = (int) config.get("stepAngle");
	xz = (boolean) config.get("xz");
        offset = 0.0;
        if(config.get("offset") != null) offset = (double) config.get("offset");
    }

    public Map<String, Object> serialize() {
	Map<String, Object> ret = new HashMap<>();
	ret.put("radius", radius);
	ret.put("startAngle", startAngle);
	ret.put("endAngle", endAngle);
	ret.put("stepAngle", stepAngle);
	ret.put("xz", xz);
        ret.put("offset", offset);
	return ret;
    }
    
    public List<Vector> getVertices(int step, int nr) {
	List<Vector> ret = new ArrayList<>();
        int dungocrazy = 0;
	for(double r = startAngle; r <= endAngle; r += stepAngle) {
	    double x = Math.cos(Math.toRadians(r)) * radius;
	    double y = Math.sin(Math.toRadians(r)) * radius;
	    if(xz) {
		ret.add(new Vector(x, offset, y));
	    }
	    else {
		ret.add(new Vector(x, y, offset));
	    }
            if(++dungocrazy >= 100) break;
	}
	return ret;
    }

    public String getInfo(boolean detailed) {
	return "Circle " + startAngle + "-" + endAngle + "," + stepAngle + "; r=" + radius + (xz ? " xz" : "xy") + ", o" + offset;
    }
}
