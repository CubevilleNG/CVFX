package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import org.cubeville.commons.commands.*;
import org.cubeville.commons.utils.ColorUtils;

import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectWithLocation;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectComponent;
import org.cubeville.effects.managers.ParticleEffectTimelineEntry;
import org.cubeville.effects.managers.modifier.*;
import org.cubeville.effects.managers.sources.coordinate.CircleCoordinateSource;
import org.cubeville.effects.managers.sources.coordinate.ConstantCoordinateSource;
import org.cubeville.effects.managers.sources.coordinate.CoordinateSource;
import org.cubeville.effects.managers.sources.coordinate.RandomCoordinateSource;
import org.cubeville.effects.managers.sources.value.ValueSource;
import org.cubeville.effects.util.WorldEditUtils;

public class ParticleCommandHelper
{
    public static void addCommandParameters(Command command) {
        command.addParameter("description", true, new CommandParameterString());
        
        command.addParameter("particle", true, new CommandParameterEnumOrNull(Particle.class, "none"));
        command.addParameter("externaleffect", true, new CommandParameterString());

        command.addParameter("cleanup+", true, new CommandParameterString());
        command.addParameter("cleanup-", true, new CommandParameterInteger());
        
        command.addParameter("armorstandactive", true, new CommandParameterBoolean());
        command.addParameter("armorstandrotation", true, new CommandParameterValueSource());
        command.addParameter("armorstandheadposex", true, new CommandParameterValueSource());
        command.addParameter("armorstandheadposey", true, new CommandParameterValueSource());
        command.addParameter("armorstandheadposez", true, new CommandParameterValueSource());
        command.addParameter("armorstandbodyposex", true, new CommandParameterValueSource());
        command.addParameter("armorstandbodyposey", true, new CommandParameterValueSource());
        command.addParameter("armorstandbodyposez", true, new CommandParameterValueSource());
        command.addParameter("armorstandleftarmposex", true, new CommandParameterValueSource());
        command.addParameter("armorstandleftarmposey", true, new CommandParameterValueSource());
        command.addParameter("armorstandleftarmposez", true, new CommandParameterValueSource());
        command.addParameter("armorstandrightarmposex", true, new CommandParameterValueSource());
        command.addParameter("armorstandrightarmposey", true, new CommandParameterValueSource());
        command.addParameter("armorstandrightarmposez", true, new CommandParameterValueSource());
        command.addParameter("armorstandleftlegposex", true, new CommandParameterValueSource());
        command.addParameter("armorstandleftlegposey", true, new CommandParameterValueSource());
        command.addParameter("armorstandleftlegposez", true, new CommandParameterValueSource());
        command.addParameter("armorstandrightlegposex", true, new CommandParameterValueSource());
        command.addParameter("armorstandrightlegposey", true, new CommandParameterValueSource());
        command.addParameter("armorstandrightlegposez", true, new CommandParameterValueSource());
        command.addFlag("armorstandheaditem");
        command.addFlag("armorstandbodyitem");
        command.addFlag("armorstandlefthanditem");
        command.addFlag("armorstandrighthanditem");
        command.addFlag("armorstandlegsitem");
        command.addFlag("armorstandfeetitem");
        command.addParameter("armorstandarms", true, new CommandParameterBoolean());
        command.addParameter("armorstandvisible", true, new CommandParameterBoolean());
        command.addParameter("armorstandsmall", true, new CommandParameterBoolean());

        command.addFlag("itemdisplay");
        command.addParameter("additemdisplay", true, new CommandParameterInteger());
        command.addParameter("additemanim", true, new CommandParameterListInteger(2));
        command.addParameter("textdisplay", true, new CommandParameterString());
        command.addParameter("addtextdisplay", true, new CommandParameterString());
        command.addParameter("textbgalpha", true, new CommandParameterValueSource());
        command.addParameter("textbgred", true, new CommandParameterValueSource());
        command.addParameter("textbggreen", true, new CommandParameterValueSource());
        command.addParameter("textbgblue", true, new CommandParameterValueSource());
        command.addParameter("textopacity", true, new CommandParameterValueSource());
        command.addParameter("displaymovex", true, new CommandParameterValueSource());
        command.addParameter("displaymovey", true, new CommandParameterValueSource());
        command.addParameter("displaymovez", true, new CommandParameterValueSource());
        command.addParameter("displayscalex", true, new CommandParameterValueSource());
        command.addParameter("displayscaley", true, new CommandParameterValueSource());
        command.addParameter("displayscalez", true, new CommandParameterValueSource());
        command.addParameter("displaylrotx", true, new CommandParameterValueSource());
        command.addParameter("displaylroty", true, new CommandParameterValueSource());
        command.addParameter("displaylrotz", true, new CommandParameterValueSource());
        command.addParameter("displaylrotangle", true, new CommandParameterValueSource());
        command.addParameter("displayrrotx", true, new CommandParameterValueSource());
        command.addParameter("displayrroty", true, new CommandParameterValueSource());
        command.addParameter("displayrrotz", true, new CommandParameterValueSource());
        command.addParameter("displayrrotangle", true, new CommandParameterValueSource());
        
        command.addParameter("constantsource", true, new CommandParameterListVector());
        command.addParameter("constantsource+", true, new CommandParameterListVector());
        command.addParameter("constantsource-", true, new CommandParameterListInteger(1));
        command.addParameter("presetconstantsource", true, new CommandParameterString());
        command.addParameter("presetconstantsource+", true, new CommandParameterString());
        command.addParameter("constantsourcescale", true, new CommandParameterDouble());
        command.addParameter("constantsourcescalexy", true, new CommandParameterDouble());
        command.addParameter("circlesource", true, new CommandParameterListDouble(3));
        command.addParameter("circlesourcexz", true, new CommandParameterListDouble(3));
        {
            List<CommandParameterType> pl = new ArrayList<>();
            pl.add(new CommandParameterVector());
            pl.add(new CommandParameterVector());
            pl.add(new CommandParameterInteger(1, 100));
            command.addParameter("randomsource", true, new CommandParameterList(pl));
        }
        command.addParameter("duration", true, new CommandParameterInteger());
        command.addParameter("repeat", true, new CommandParameterInteger());
        command.addParameter("repeatoffset", true, new CommandParameterInteger());        
        command.addParameter("spreadx", true, new CommandParameterValueSource());
        command.addParameter("spready", true, new CommandParameterValueSource());
        command.addParameter("spreadz", true, new CommandParameterValueSource());
        command.addParameter("count", true, new CommandParameterValueSource());
        command.addParameter("rotate+", true, new CommandParameterValueSource());
        {
            List<CommandParameterType> pl = new ArrayList<>();
            pl.add(new CommandParameterValueSource());
            pl.add(new CommandParameterString());
            command.addParameter("move+", true, new CommandParameterList(pl));
            command.addParameter("move", true, new CommandParameterList(pl));
        }
        {
            List<CommandParameterType> pl = new ArrayList<>();
            pl.add(new CommandParameterValueSource());
            pl.add(new CommandParameterString());
            command.addParameter("advrotate+", true, new CommandParameterList(pl));
        }
        command.addParameter("scale+", true, new CommandParameterValueSource());
        command.addParameter("scale", true, new CommandParameterValueSource());
        {
            List<CommandParameterType> pl = new ArrayList<>();
            pl.add(new CommandParameterValueSource());
            pl.add(new CommandParameterValueSource());
            command.addParameter("scale2d+", true, new CommandParameterList(pl));
        }
        command.addParameter("modifier-", true, new CommandParameterInteger());
        command.addParameter("copymodifiers", true, new CommandParameterInteger());
        command.addFlag("clearmodifiers");
        command.addParameter("material", true, new CommandParameterEnum(Material.class));
        command.addFlag("cleartimelines");
        command.addParameter("timeline+", true, new CommandParameterListInteger(5));
        command.addParameter("timeline-", true, new CommandParameterInteger());
        command.addParameter("copytimelines", true, new CommandParameterInteger());
        command.addParameter("createtimelines", true, new CommandParameterListInteger(4));
        command.addParameter("createconttimelines", true, new CommandParameterListInteger(4));
        command.addParameter("red", true, new CommandParameterValueSource());
        command.addParameter("green", true, new CommandParameterValueSource());
        command.addParameter("blue", true, new CommandParameterValueSource());
        command.addParameter("tored", true, new CommandParameterValueSource());
        command.addParameter("togreen", true, new CommandParameterValueSource());
        command.addParameter("toblue", true, new CommandParameterValueSource());
        command.addParameter("size", true, new CommandParameterValueSource());
        command.addParameter("speed", true, new CommandParameterValueSource());
        command.addParameter("blockcollisioncheck", true, new CommandParameterBoolean());
        command.addParameter("entitycollisioncheck", true, new CommandParameterBoolean());
    }
    
    public static void setEffectValues(ParticleEffect effect, Map<String, Object> parameters) {
        if(parameters.containsKey("duration")) effect.setStepsLoop((int) parameters.get("duration"));
        if(parameters.containsKey("repeat")) effect.setRepeatCount((int) parameters.get("repeat"));
        if(parameters.containsKey("repeatoffset")) effect.setRepeatOffset((int) parameters.get("repeatoffset"));
        if(parameters.containsKey("cleanup+")) {
            String effectName = (String) parameters.get("cleanup+");
            Effect ceffect = EffectManager.getInstance().getEffectByName(effectName);
            if(ceffect == null)
                throw new IllegalArgumentException("Cleanup effect name not found!");
            effect.addCleanupEffect(ceffect);
        }

        if(parameters.containsKey("cleanup-")) { // TODO: Check if index is valid
            effect.removeCleanupEffect((int) parameters.get("cleanup-") - 1);
        }
    }

    public static boolean hasOnlyEffectValues(Map<String, Object> parameters, Set<String> flags) {
        if(flags.size() > 0) return false;
        for(String key: parameters.keySet()) {
            if(! (key.equals("duration") || key.equals("repeat") || key.equals("repeatoffset") || key.equals("cleanup+") || key.equals("cleanup-")))
                return false;
        }
        return true;
    }
    
    public static void setComponentValues(ParticleEffectComponent component, Map<String, Object> parameters, Set<String> flags, Player player, ParticleEffect parent) {
        int numberOfSources = 0;
        if(parameters.containsKey("constantsource")) numberOfSources++;
        if(parameters.containsKey("constantsource+")) numberOfSources++;
        if(parameters.containsKey("constantsource-")) numberOfSources++;
        if(parameters.containsKey("presetconstantsource")) numberOfSources++;
        if(parameters.containsKey("presetconstantsource+")) numberOfSources++;
        if(parameters.containsKey("circlesource")) numberOfSources++;
        if(parameters.containsKey("randomsource")) numberOfSources++;
        if(parameters.containsKey("circlesourcexz")) numberOfSources++;
        if(parameters.containsKey("constantsourcescale")) numberOfSources++;
        if(parameters.containsKey("constantsourcescalexy")) numberOfSources++;
        if(numberOfSources > 1) throw new IllegalArgumentException("Only one coordinate source creation or modification parameter is possible.");

        if(parameters.containsKey("constantsource")) {
            List<Vector> coords = (List<Vector>) parameters.get("constantsource");
            component.setCoordinates(new ConstantCoordinateSource(coords));
        }

        if(parameters.containsKey("constantsource+")) {
            List<Vector> coords = (List<Vector>) parameters.get("constantsource+");
            CoordinateSource source = component.getCoordinates();
            if(source instanceof ConstantCoordinateSource) {
                ConstantCoordinateSource csource = (ConstantCoordinateSource) source;
                csource.addVertices(coords);
            }
            else {
                component.setCoordinates(new ConstantCoordinateSource(coords));
            }
        }

        if(parameters.containsKey("constantsource-")) {
            List<Integer> indices = (List<Integer>) parameters.get("constantsource-");
            CoordinateSource source = component.getCoordinates();
            if(source instanceof ConstantCoordinateSource) {
                ConstantCoordinateSource csource = (ConstantCoordinateSource) source;
                csource.removeVertices(indices);
            }
            else {
                throw new IllegalArgumentException("Can't remove coordinates, source is not constant!");
            }
        }

        if(parameters.containsKey("presetconstantsource")) {
            List<Vector> coords;
            Pattern selPattern = Pattern.compile("selection\\((.*)\\)");
            Matcher selMatcher = selPattern.matcher((String) parameters.get("presetconstantsource"));
            if (parameters.get("presetconstantsource").equals("selection")) {
                coords = WorldEditUtils.getSelectionCoordinates(player);
            } else if (selMatcher.find()) {
                String matArg = selMatcher.group(1);
                coords = WorldEditUtils.getSelectionCoordinates(player, Material.matchMaterial(matArg));
            } else {
                throw new IllegalArgumentException("Invalid preset argument!");
            }
            component.setCoordinates(new ConstantCoordinateSource(coords));
        }

        if(parameters.containsKey("presetconstantsource+")) {
            List<Vector> coords;
            Pattern selPattern = Pattern.compile("selection\\((.*)\\)");
            Matcher selMatcher = selPattern.matcher((String) parameters.get("presetconstantsource+"));
            if (parameters.get("presetconstantsource+").equals("selection")) {
                coords = WorldEditUtils.getSelectionCoordinates(player);
            } else if (selMatcher.find()) {
                String matArg = selMatcher.group(1);
                coords = WorldEditUtils.getSelectionCoordinates(player, Material.matchMaterial(matArg));
            } else {
                throw new IllegalArgumentException("Invalid preset argument!");
            }
            CoordinateSource source = component.getCoordinates();
            if(source instanceof ConstantCoordinateSource) {
                ConstantCoordinateSource csource = (ConstantCoordinateSource) source;
                csource.addVertices(coords);
            }
            else {
                component.setCoordinates(new ConstantCoordinateSource(coords));
            }
        }

        if(parameters.containsKey("circlesource") || parameters.containsKey("circlesourcexz")) {
            List<Double> pars = (List<Double>) parameters.get("circlesource");
            if(pars == null) pars = (List<Double>) parameters.get("circlesourcexz");
            int startAngle = 0;
            int endAngle = 360 - pars.get(1).intValue();
            component.setCoordinates(new CircleCoordinateSource(pars.get(0), pars.get(2), pars.get(1).intValue(), 0, endAngle, parameters.containsKey("circlesourcexz")));
        }

        if(parameters.containsKey("randomsource")) {
            List<Object> pars = (List<Object>) parameters.get("randomsource");
            Vector vec1 = (Vector) pars.get(0);
            Vector vec2 = (Vector) pars.get(1);
            int count = (Integer) pars.get(2);
            component.setCoordinates(new RandomCoordinateSource(count, vec1, vec2));
        }

        if(parameters.containsKey("constantsourcescale") || parameters.containsKey("constantsourcescalexy")) {
            boolean includeZ = parameters.containsKey("constantsourcescale");
            double factor;
            if(includeZ)
                factor = (double) parameters.get("constantsourcescale");
            else
                factor = (double) parameters.get("constantsourcescalexy");
            CoordinateSource source = component.getCoordinates();
            if(source instanceof ConstantCoordinateSource) {
                ConstantCoordinateSource csource = (ConstantCoordinateSource) source;
                csource.scaleVertices(factor, includeZ);
            }
            else {
                throw new IllegalArgumentException("Can't scale coordinates, source is not constant!");
            }
        }

        if(parameters.containsKey("description")) component.setDescription((String) parameters.get("description"));

        if(parameters.containsKey("particle")) component.setParticle((Particle) parameters.get("particle"));
        if(parameters.containsKey("externaleffect")) {
            String name = (String) parameters.get("externaleffect");
            if(name.equals("none")) {
                component.setExternalEffect(null);
            }
            else {
                Effect effect = EffectManager.getInstance().getEffectByName(name);
                if(effect == null) throw new IllegalArgumentException("External effect name not found.");
                else if(!(effect instanceof EffectWithLocation)) throw new IllegalArgumentException("Only location-based effects can be used as external effect.");
                component.setExternalEffect(name, effect);
            }
        }

        if(flags.contains("itemdisplay")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) {
                component.removeDisplayEntity();
            }
            else {
                component.createOrGetDisplayEntityProperties().setItemData(item);
            }
        }

        if(parameters.containsKey("additemdisplay")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            int startStep = (int) parameters.get("additemdisplay");
            component.createOrGetDisplayEntityProperties().addItemData(item, startStep);
        }

        if(parameters.containsKey("additemanim")) {
            List<Integer> pars = (List<Integer>) parameters.get("additemanim");
            int itemcount = pars.get(0);
            int interval = pars.get(1);
            component.createOrGetDisplayEntityProperties().setItemData(player.getInventory().getItem(0));
            int duration = parent.getDuration();
            int itemno = 0;
            for(int i = interval; i < duration; i += interval) {
                component.createOrGetDisplayEntityProperties().addItemData(player.getInventory().getItem(++itemno % itemcount), i); // TODO reusing items would be better
            }
        }
        
        if(parameters.containsKey("textdisplay")) {
            String text = ColorUtils.addColor((String) parameters.get("textdisplay"));
            component.createOrGetDisplayEntityProperties().setText(text);
        }

        if(parameters.containsKey("addtextdisplay")) {
            String t = (String) parameters.get("addtextdisplay");
            int pos = t.indexOf(';');
            int startStep = Integer.valueOf(t.substring(0, pos));
            String text = ColorUtils.addColor(t.substring(pos + 1));
            component.createOrGetDisplayEntityProperties().addText(text, startStep);
        }

        if(parameters.containsKey("textbgalpha"))
            component.createOrGetDisplayEntityProperties().textBackgroundAlpha = (ValueSource) parameters.get("textbgalpha");
        if(parameters.containsKey("textbgred"))
            component.createOrGetDisplayEntityProperties().textBackgroundRed = (ValueSource) parameters.get("textbgred");
        if(parameters.containsKey("textbggreen"))
            component.createOrGetDisplayEntityProperties().textBackgroundGreen = (ValueSource) parameters.get("textbggreen");
        if(parameters.containsKey("textbgblue"))
            component.createOrGetDisplayEntityProperties().textBackgroundBlue = (ValueSource) parameters.get("textbgblue");
        if(parameters.containsKey("textopacity"))
            component.createOrGetDisplayEntityProperties().textOpacity = (ValueSource) parameters.get("textopacity");

        if(parameters.containsKey("displaymovex"))
            component.createOrGetDisplayEntityProperties().moveX = (ValueSource) parameters.get("displaymovex");
        if(parameters.containsKey("displaymovey"))
            component.createOrGetDisplayEntityProperties().moveY = (ValueSource) parameters.get("displaymovey");
        if(parameters.containsKey("displaymovez"))
            component.createOrGetDisplayEntityProperties().moveZ = (ValueSource) parameters.get("displaymovez");
        if(parameters.containsKey("displayscalex"))
            component.createOrGetDisplayEntityProperties().scaleX = (ValueSource) parameters.get("displayscalex");
        if(parameters.containsKey("displayscaley"))
            component.createOrGetDisplayEntityProperties().scaleY = (ValueSource) parameters.get("displayscaley");
        if(parameters.containsKey("displayscalez"))
            component.createOrGetDisplayEntityProperties().scaleZ = (ValueSource) parameters.get("displayscalez");
        if(parameters.containsKey("displaylrotx"))
            component.createOrGetDisplayEntityProperties().rotateLeftX = (ValueSource) parameters.get("displaylrotx");
        if(parameters.containsKey("displaylroty"))
            component.createOrGetDisplayEntityProperties().rotateLeftY = (ValueSource) parameters.get("displaylroty");
        if(parameters.containsKey("displaylrotz"))
            component.createOrGetDisplayEntityProperties().rotateLeftZ = (ValueSource) parameters.get("displaylrotz");
        if(parameters.containsKey("displaylrotangle"))
            component.createOrGetDisplayEntityProperties().rotateLeftAngle = (ValueSource) parameters.get("displaylrotangle");
        if(parameters.containsKey("displayrrotx"))
            component.createOrGetDisplayEntityProperties().rotateRightX = (ValueSource) parameters.get("displayrrotx");
        if(parameters.containsKey("displayrroty"))
            component.createOrGetDisplayEntityProperties().rotateRightY = (ValueSource) parameters.get("displayrroty");
        if(parameters.containsKey("displayrrotz"))
            component.createOrGetDisplayEntityProperties().rotateRightZ = (ValueSource) parameters.get("displayrrotz");
        if(parameters.containsKey("displayrrotangle"))
            component.createOrGetDisplayEntityProperties().rotateRightAngle = (ValueSource) parameters.get("displayrrotangle");
        
        if(parameters.containsKey("armorstandactive")) {
            if((boolean) parameters.get("armorstandactive")) {
                component.createOrGetArmorStandProperties();
            }
            else {
                component.removeArmorStand();
            }
        }

        if(parameters.containsKey("armorstandarms"))
            component.createOrGetArmorStandProperties().hasArms = (boolean) parameters.get("armorstandarms");
        if(parameters.containsKey("armorstandvisible"))
            component.createOrGetArmorStandProperties().visible = (boolean) parameters.get("armorstandvisible");
        if(parameters.containsKey("armorstandsmall"))
            component.createOrGetArmorStandProperties().small = (boolean) parameters.get("armorstandsmall");
        
        if(parameters.containsKey("armorstandrotation"))
            component.createOrGetArmorStandProperties().rotation = (ValueSource) parameters.get("armorstandrotation");
        if(parameters.containsKey("armorstandheadposex"))
            component.createOrGetArmorStandProperties().headPoseX = (ValueSource) parameters.get("armorstandheadposex");
        if(parameters.containsKey("armorstandheadposey"))
            component.createOrGetArmorStandProperties().headPoseY = (ValueSource) parameters.get("armorstandheadposey");
        if(parameters.containsKey("armorstandheadposez"))
            component.createOrGetArmorStandProperties().headPoseZ = (ValueSource) parameters.get("armorstandheadposez");
        if(parameters.containsKey("armorstandbodyposex"))
            component.createOrGetArmorStandProperties().bodyPoseX = (ValueSource) parameters.get("armorstandbodyposex");
        if(parameters.containsKey("armorstandbodyposey"))
            component.createOrGetArmorStandProperties().bodyPoseY = (ValueSource) parameters.get("armorstandbodyposey");
        if(parameters.containsKey("armorstandbodyposez"))
            component.createOrGetArmorStandProperties().bodyPoseZ = (ValueSource) parameters.get("armorstandbodyposez");
        if(parameters.containsKey("armorstandleftarmposex"))
            component.createOrGetArmorStandProperties().leftArmPoseX = (ValueSource) parameters.get("armorstandleftarmposex");
        if(parameters.containsKey("armorstandleftarmposey"))
            component.createOrGetArmorStandProperties().leftArmPoseY = (ValueSource) parameters.get("armorstandleftarmposey");
        if(parameters.containsKey("armorstandleftarmposez"))
            component.createOrGetArmorStandProperties().leftArmPoseZ = (ValueSource) parameters.get("armorstandleftarmposez");
        if(parameters.containsKey("armorstandrightarmposex"))
            component.createOrGetArmorStandProperties().rightArmPoseX = (ValueSource) parameters.get("armorstandrightarmposex");
        if(parameters.containsKey("armorstandrightarmposey"))
            component.createOrGetArmorStandProperties().rightArmPoseY = (ValueSource) parameters.get("armorstandrightarmposey");
        if(parameters.containsKey("armorstandrightarmposez"))
            component.createOrGetArmorStandProperties().rightArmPoseZ = (ValueSource) parameters.get("armorstandrightarmposez");
        if(parameters.containsKey("armorstandleftlegposex"))
            component.createOrGetArmorStandProperties().leftLegPoseX = (ValueSource) parameters.get("armorstandleftlegposex");
        if(parameters.containsKey("armorstandleftlegposey"))
            component.createOrGetArmorStandProperties().leftLegPoseY = (ValueSource) parameters.get("armorstandleftlegposey");
        if(parameters.containsKey("armorstandleftlegposez"))
            component.createOrGetArmorStandProperties().leftLegPoseZ = (ValueSource) parameters.get("armorstandleftlegposez");
        if(parameters.containsKey("armorstandrightlegposex"))
            component.createOrGetArmorStandProperties().rightLegPoseX = (ValueSource) parameters.get("armorstandrightlegposex");
        if(parameters.containsKey("armorstandrightlegposey"))
            component.createOrGetArmorStandProperties().rightLegPoseY = (ValueSource) parameters.get("armorstandrightlegposey");
        if(parameters.containsKey("armorstandrightlegposez"))
            component.createOrGetArmorStandProperties().rightLegPoseZ = (ValueSource) parameters.get("armorstandrightlegposez");

        if(flags.contains("armorstandheaditem")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            component.createOrGetArmorStandProperties().headItem = item;
        }
        
        if(flags.contains("armorstandbodyitem")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            component.createOrGetArmorStandProperties().bodyItem = item;
        }
        
        if(flags.contains("armorstandlefthanditem")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            component.createOrGetArmorStandProperties().leftHandItem = item;
        }
        
        if(flags.contains("armorstandrighthanditem")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            component.createOrGetArmorStandProperties().rightHandItem = item;
        }
        
        if(flags.contains("armorstandlegsitem")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            component.createOrGetArmorStandProperties().legsItem = item;
        }

        if(flags.contains("armorstandfeetitem")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR) item = null;
            component.createOrGetArmorStandProperties().feetItem = item;
        }
        
        if(parameters.containsKey("spreadx")) component.setSpreadX((ValueSource) parameters.get("spreadx"));
        if(parameters.containsKey("spready")) component.setSpreadY((ValueSource) parameters.get("spready"));
        if(parameters.containsKey("spreadz")) component.setSpreadZ((ValueSource) parameters.get("spreadz"));
        if(parameters.containsKey("count")) component.setCount((ValueSource) parameters.get("count"));
        if(parameters.containsKey("material")) component.setMaterial((Material) parameters.get("material"));

        if(parameters.containsKey("rotate+")) {
            CoordinateModifierRotate modifier =
                new CoordinateModifierRotate((ValueSource) parameters.get("rotate+"));
            component.addModifier(modifier);
        }

        if(parameters.containsKey("advrotate+")) {
            List<Object> pars = (List<Object>) parameters.get("advrotate+");
            String axis = (String) pars.get(1);
            if (!axis.equals("xy") && !axis.equals("xz") && !axis.equals("yz")) {
                throw new IllegalArgumentException("Invalid rotation axis! Valid options: xy, xz, yz");
            }
            CoordinateModifierAdvRotate modifier =
                    new CoordinateModifierAdvRotate((ValueSource) pars.get(0), (String) pars.get(1));
            component.addModifier(modifier);
        }

        if(parameters.containsKey("move+") || parameters.containsKey("move")) {
            boolean add = parameters.containsKey("move+");
            List<Object> pars = (List<Object>) parameters.get(add ? "move+" : "move");
            String directions = (String) pars.get(1);
            boolean x = directions.indexOf('x') >= 0;
            boolean y = directions.indexOf('y') >= 0;
            boolean z = directions.indexOf('z') >= 0;
            CoordinateModifierMove modifier =
                new CoordinateModifierMove((ValueSource) pars.get(0), x, y, z);
            if(add)
                component.addModifier(modifier);
            else
                component.replaceOrAddModifier(modifier);
        }

        if(parameters.containsKey("scale+") || parameters.containsKey("scale")) {
            boolean add = parameters.containsKey("scale+");
            CoordinateModifierScale modifier =
                new CoordinateModifierScale((ValueSource) parameters.get(add ? "scale+" : "scale"));
            if(add)
                component.addModifier(modifier);
            else
                component.replaceOrAddModifier(modifier);
        }

        if(parameters.containsKey("scale2d+")) {
            List<Object> pars = (List<Object>) parameters.get("scale2d+");
            CoordinateModifierScale2d modifier =
                new CoordinateModifierScale2d((ValueSource) pars.get(0),
                                              (ValueSource) pars.get(1));
            component.addModifier(modifier);
        }

        if(parameters.containsKey("modifier-")) {
            int index = (int) parameters.get("modifier-");
            if(index > 0 && index <= component.getModifiers().size())
                component.getModifiers().remove(index - 1);
        }

        if(flags.contains("clearmodifiers")) {
            component.deleteModifiers();
        }
        
        if(parameters.containsKey("copymodifiers")) {
            int index = (int) parameters.get("copymodifiers");
            ParticleEffectComponent origin = parent.getComponents().get(index - 1);
            for(CoordinateModifier m: origin.getModifiers()) {
                component.addModifier(m);
            }
        }

        if(parameters.containsKey("copytimelines")) {
            int index = (int) parameters.get("copytimelines");
            ParticleEffectComponent origin = parent.getComponents().get(index - 1);
            for(ParticleEffectTimelineEntry tle: origin.getTimeline()) {
                component.getTimeline().add(tle);
            }
        }
        
        if(flags.contains("cleartimelines")) {
            while(component.getTimeline().size() > 0) {
                component.getTimeline().remove(0);
            }
        }
        
        if(parameters.containsKey("timeline+")) {
            List<Integer> p = (List<Integer>) parameters.get("timeline+");
            int tstart = p.get(0);
            int tcount = p.get(1);
            int trepeat = p.get(2);
            int tlocoffset = p.get(3);
            int teffectoffset = p.get(4);
            component.getTimeline().add(new ParticleEffectTimelineEntry(tstart, trepeat, tcount, tlocoffset, teffectoffset));
        }

        if(parameters.containsKey("createtimelines") || parameters.containsKey("createconttimelines")) {
            boolean continuous = false;
            List<Integer> p;
            if(parameters.containsKey("createtimelines"))
                p = (List<Integer>) parameters.get("createtimelines");
            else {
                p = (List<Integer>) parameters.get("createconttimelines");
                continuous = true;
            }
            
            int tstart = p.get(0);
            int tinterval = p.get(1);
            int tcount = p.get(2);
            int tduration = p.get(3);
            int offset = tstart;
            int loffset = continuous ? 0 : offset;
            for(int i = 0; i < tcount; i++) {
                component.getTimeline().add(new ParticleEffectTimelineEntry(offset, 1, tduration, loffset, loffset));
                offset += tinterval;
                if(!continuous) loffset += tinterval;
            }
        }

        if(parameters.containsKey("timeline-")) {
            int index = (int) parameters.get("timeline-");
            if(index > 0 && index <= component.getTimeline().size()) {
                component.getTimeline().remove(index - 1);
            }
        }

        if(parameters.containsKey("red"))     component.setColourRed(      (ValueSource) parameters.get("red")    );
        if(parameters.containsKey("green"))   component.setColourGreen(    (ValueSource) parameters.get("green")  );
        if(parameters.containsKey("blue"))    component.setColourBlue(     (ValueSource) parameters.get("blue")   );
        if(parameters.containsKey("tored"))   component.setColourToRed(    (ValueSource) parameters.get("tored")  );
        if(parameters.containsKey("togreen")) component.setColourToGreen(  (ValueSource) parameters.get("togreen"));
        if(parameters.containsKey("toblue"))  component.setColourToBlue(   (ValueSource) parameters.get("toblue") );
        if(parameters.containsKey("size"))    component.setSize(           (ValueSource) parameters.get("size")   );
        if(parameters.containsKey("speed"))   component.setSpeed(          (ValueSource) parameters.get("speed")  );
        if(parameters.containsKey("blockcollisioncheck")) component.setBlockCollisionCheck((Boolean) parameters.get("blockcollisioncheck"));
        if(parameters.containsKey("entitycollisioncheck")) component.setEntityCollisionCheck((Boolean) parameters.get("entitycollisioncheck"));
    }
}
