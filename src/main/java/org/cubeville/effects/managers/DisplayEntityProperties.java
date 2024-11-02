package org.cubeville.effects.managers;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import org.cubeville.effects.managers.sources.value.ValueSource;
import org.cubeville.effects.managers.sources.value.ConstantValueSource;

@SerializableAs("DisplayEntityProperties")
public class DisplayEntityProperties implements ConfigurationSerializable
{
    private List<ItemStack> itemDataList;
    private List<String> textList;

    private List<TimeMapping> timeMappings;

    public ValueSource textBackgroundAlpha = new ConstantValueSource(255);
    public ValueSource textBackgroundRed = new ConstantValueSource(0);
    public ValueSource textBackgroundGreen = new ConstantValueSource(0);
    public ValueSource textBackgroundBlue = new ConstantValueSource(0);
    public ValueSource textOpacity = new ConstantValueSource(255);

    public ValueSource moveX = new ConstantValueSource(0);
    public ValueSource moveY = new ConstantValueSource(0);
    public ValueSource moveZ = new ConstantValueSource(0);
    public ValueSource rotateLeftX = new ConstantValueSource(0);
    public ValueSource rotateLeftY = new ConstantValueSource(1);
    public ValueSource rotateLeftZ = new ConstantValueSource(0);
    public ValueSource rotateLeftAngle = new ConstantValueSource(0);
    public ValueSource scaleX = new ConstantValueSource(1);
    public ValueSource scaleY = new ConstantValueSource(1);
    public ValueSource scaleZ = new ConstantValueSource(1);
    public ValueSource rotateRightX = new ConstantValueSource(0);
    public ValueSource rotateRightY = new ConstantValueSource(1);
    public ValueSource rotateRightZ = new ConstantValueSource(0);
    public ValueSource rotateRightAngle = new ConstantValueSource(0);

    private class TimeMapping {
        private int start;
        private int itemNo;
        
        public TimeMapping(int start, int itemNo) {
            this.start = start;
            this.itemNo = itemNo;
        }
        
        public TimeMapping(Map<String, Object> config) {
            start = (int) config.get("start");
            itemNo = (int) config.get("itemNo");
        }
        
        public Map<String, Object> serialize() {
            Map<String, Object> ret = new HashMap<>();
            ret.put("start", start);
            ret.put("itemNo", itemNo);
            return ret;
        }
        
        public int getStart() { return start; }
        public int getItemNo() { return itemNo; }
    }

    public DisplayEntityProperties() {
    }

    public DisplayEntityProperties(Map<String, Object> config) {
        if(config.get("itemData") != null) {
            setItemData((ItemStack) config.get("itemData"));
        }
        else if(config.get("itemDataList") != null) {
            itemDataList = (List<ItemStack>) config.get("itemDataList");
        }
        if(config.get("timeMappings") != null) {
            Map<Integer, Integer> tm = (Map<Integer, Integer>) config.get("timeMappings");
            timeMappings = new ArrayList<>();
            if(tm == null) {
                timeMappings.add(new TimeMapping(0, 0));
            }
            else {
                for(Map.Entry<Integer, Integer> i: tm.entrySet())
                    timeMappings.add(new TimeMapping(i.getKey(), i.getValue()));
            }
            sortTimeMappings();
        }
        if(config.get("textList") != null) textList = (List<String>) config.get("textList");
        if(config.get("moveX") != null) moveX = (ValueSource) config.get("moveX");
        if(config.get("moveY") != null) moveY = (ValueSource) config.get("moveY");
        if(config.get("moveZ") != null) moveZ = (ValueSource) config.get("moveZ");
        if(config.get("scaleX") != null) scaleX = (ValueSource) config.get("scaleX");
        if(config.get("scaleY") != null) scaleY = (ValueSource) config.get("scaleY");
        if(config.get("scaleZ") != null) scaleZ = (ValueSource) config.get("scaleZ");
        if(config.get("rotateLeftX") != null) rotateLeftX = (ValueSource) config.get("rotateLeftX");
        if(config.get("rotateLeftY") != null) rotateLeftY = (ValueSource) config.get("rotateLeftY");
        if(config.get("rotateLeftZ") != null) rotateLeftZ = (ValueSource) config.get("rotateLeftZ");
        if(config.get("rotateLeftAngle") != null) rotateLeftAngle = (ValueSource) config.get("rotateLeftAngle");
        if(config.get("rotateRightX") != null) rotateRightX = (ValueSource) config.get("rotateRightX");
        if(config.get("rotateRightY") != null) rotateRightY = (ValueSource) config.get("rotateRightY");
        if(config.get("rotateRightZ") != null) rotateRightZ = (ValueSource) config.get("rotateRightZ");
        if(config.get("rotateRightAngle") != null) rotateRightAngle = (ValueSource) config.get("rotateRightAngle");
        if(config.get("textBackgroundAlpha") != null) {
            textBackgroundAlpha = (ValueSource) config.get("textBackgroundAlpha");
            textBackgroundRed = (ValueSource) config.get("textBackgroundRed");
            textBackgroundGreen = (ValueSource) config.get("textBackgroundGreen");
            textBackgroundBlue = (ValueSource) config.get("textBackgroundBlue");
        }
        if(config.get("textOpacity") != null) {
            textOpacity = (ValueSource) config.get("textOpacity");
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        if(itemDataList != null) ret.put("itemDataList", itemDataList);
        if(textList != null) ret.put("textList", textList);
        if(timeMappings != null) {
            Map<Integer, Integer> l = new HashMap<>();
            for(TimeMapping tm: timeMappings) {
                l.put(tm.getStart(), tm.getItemNo());
            }        
            ret.put("timeMappings", l);
        }
        ret.put("moveX", moveX);
        ret.put("moveY", moveY);
        ret.put("moveZ", moveZ);
        ret.put("scaleX", scaleX);
        ret.put("scaleY", scaleY);
        ret.put("scaleZ", scaleZ);
        ret.put("rotateLeftX", rotateLeftX);
        ret.put("rotateLeftY", rotateLeftY);
        ret.put("rotateLeftZ", rotateLeftZ);
        ret.put("rotateLeftAngle", rotateLeftAngle);
        ret.put("rotateRightX", rotateRightX);
        ret.put("rotateRightY", rotateRightY);
        ret.put("rotateRightZ", rotateRightZ);
        ret.put("rotateRightAngle", rotateRightAngle);
        if(isTextDisplay()) {
            ret.put("textBackgroundAlpha", textBackgroundAlpha); 
            ret.put("textBackgroundRed", textBackgroundRed);
            ret.put("textBackgroundGreen", textBackgroundGreen);
            ret.put("textBackgroundBlue", textBackgroundBlue);
            ret.put("textOpacity", textOpacity);
        }
        
        return ret;
    }
    
    public boolean isItemDisplay() {
        return itemDataList != null;
    }

    public boolean isTextDisplay() {
        return textList != null;
    }

    public ItemStack getItemData(int step) {
        if(timeMappings.size() == 0) return null; // TODO Implement for text too
        for(int i = timeMappings.size() - 1; i > 0; i--) {
            if(timeMappings.get(i).getStart() <= step)
                return itemDataList.get(timeMappings.get(i).getItemNo());
        }
        return itemDataList.get(0); // TODO wrong, this method should not exist in this form anymore
    }

    public boolean isItemDataSwitch(int step) {
        // TODO this can be optimized since the list is (supposedly at least) sorted:
        if(timeMappings == null) return false;
        for(TimeMapping m: timeMappings) {
            if(m.getStart() == step) return true;
        }
        return false;
    }
    
    public String getText(int step) {
        if(timeMappings.size() == 0) return null;
        for(int i = timeMappings.size() - 1; i > 0; i--) {
            if(timeMappings.get(i).getStart() <= step)
                return textList.get(timeMappings.get(i).getItemNo());
        }
        return textList.get(0);
    }

    public void setItemData(ItemStack itemData) {
        itemDataList = new ArrayList<>();
        itemDataList.add(itemData);
        timeMappings = new ArrayList<>();
        timeMappings.add(new TimeMapping(0, 0));
        this.textList = null;
    }

    public void addItemData(ItemStack itemData, int startStep) {
        if(itemDataList == null) {
            setItemData(itemData);
        }
        else {
            itemDataList.add(itemData);
            timeMappings.add(new TimeMapping(startStep, itemDataList.size() - 1));
            sortTimeMappings();
            // TODO also check if the item data is already in the list in case it's equal, however that can be checked, or make own command to just add link, but for now this method shall be sufficient...
        }
    }

    private void sortTimeMappings() {
        Comparator<TimeMapping> comp = new Comparator<TimeMapping>() {
                @Override
                public int compare(TimeMapping i1, TimeMapping i2) {
                    return Integer.compare(i1.start, i2.start);
                }
            };
        Collections.sort(timeMappings, comp);
        // wtf is wrong with java 
    }
    
    public void setText(String text) {
        this.textList = new ArrayList<>();
        this.textList.add(text);
        timeMappings = new ArrayList<>();
        timeMappings.add(new TimeMapping(0, 0));
        this.itemDataList = null;
    }

    public void addText(String text, int startStep) {
        if(textList == null)
            setText(text);
        else {
            textList.add(text);
            timeMappings.add(new TimeMapping(startStep, textList.size() - 1));
            sortTimeMappings();
        }
    }
}
