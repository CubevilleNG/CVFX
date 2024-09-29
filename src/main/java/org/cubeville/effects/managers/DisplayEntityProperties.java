package org.cubeville.effects.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import org.cubeville.effects.managers.sources.value.ValueSource;
import org.cubeville.effects.managers.sources.value.ConstantValueSource;

@SerializableAs("DisplayEntityProperties")
public class DisplayEntityProperties implements ConfigurationSerializable
{
    private ItemStack itemData;
    private String text;

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

    public DisplayEntityProperties() {
    }

    public DisplayEntityProperties(Map<String, Object> config) {
        itemData = (ItemStack) config.get("itemData");
        if(config.get("text") != null) text = (String) config.get("text");
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
    }

    public boolean isItemDisplay() {
        return itemData != null;
    }

    public boolean isTextDisplay() {
        return text != null;
    }

    public ItemStack getItemData() {
        return itemData;
    }

    public String getText() {
        return text;
    }

    public void setItemData(ItemStack itemData) {
        this.itemData = itemData;
        this.text = null;
    }

    public void setText(String text) {
        this.text = text;
        this.itemData = null;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("itemData", itemData);
        ret.put("text", text);
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
        return ret;
    }
}
