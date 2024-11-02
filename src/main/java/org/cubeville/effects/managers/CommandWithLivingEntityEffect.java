package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SerializableAs("CommandWithLivingEntityEffect")
public class CommandWithLivingEntityEffect extends EffectWithLivingEntity
{
    private String command;
    private boolean runAsEntity;
    
    public CommandWithLivingEntityEffect(String name, String command, boolean runAsEntity) {
        setName(name);
        this.command = command;
        this.runAsEntity = runAsEntity;
    }

    public CommandWithLivingEntityEffect(Map<String, Object> config) {
        command = (String) config.get("command");
        setName((String) config.get("name"));
        if(config.get("runAsEntity") == null)
            runAsEntity = false;
        else
            runAsEntity = (boolean) config.get("runAsEntity");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("command", command);
        ret.put("runAsEntity", runAsEntity);
        return ret;
    }

    public void play(LivingEntity entity, Event event) {
        String c = command;
        if(c.contains("%player%") && (entity instanceof Player)) {
            Player p = (Player) entity;
            c = c.replace("%player%", p.getName());
        }
        Bukkit.dispatchCommand(runAsEntity ? entity : Bukkit.getConsoleSender(), c);
    }

    public List<String> getInfo(boolean detailed, String limit) {
        List<String> ret = getInfoBase();
        ret.add("Command: " + command);
        return ret;
    }

    public String getType() {
        return "Command";
    }
}
