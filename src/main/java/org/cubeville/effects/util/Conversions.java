package org.cubeville.effects.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.cubeville.effects.managers.Effect;

public class Conversions
{
    public static String getVectorAsString(Vector vector) {
	return vector.getX() + ";" + vector.getY() + ";" + vector.getZ();
    }

    public static Vector getVectorFromString(String str) {
	String[] tk = str.split(";");
	return new Vector(Float.parseFloat(tk[0]),
			  Float.parseFloat(tk[1]),
			  Float.parseFloat(tk[2]));
    }

    public static String getLocationAsString(Location location) {
	return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static List<String> getListOfEffectNames(List<Effect> effects) {
        List<String> ret = new ArrayList<>();
        for(Effect effect: effects) ret.add(effect.getName());
        return ret;
    }

    public static String joinStringList(List<String> list, String str) {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            if(i > 0) ret.append(str);
            ret.append(list.get(i));
        }
        return ret.toString();
    }
}
