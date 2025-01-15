package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import org.cubeville.effects.Effects;

@SerializableAs("CommandWithLivingEntityEffect")
public class CommandWithLivingEntityEffect extends EffectWithLivingEntity
{
    private String command;
    private boolean runAsEntity;
    private int delay;

    public CommandWithLivingEntityEffect(String name, String command, boolean runAsEntity, int delay) {
        setName(name);
        this.command = command;
        this.runAsEntity = runAsEntity;
        this.delay = delay;
    }

    public CommandWithLivingEntityEffect(Map<String, Object> config) {
        command = (String) config.get("command");
        setName((String) config.get("name"));
        if(config.get("runAsEntity") == null)
            runAsEntity = false;
        else
            runAsEntity = (boolean) config.get("runAsEntity");
        if(config.get("delay") == null)
            delay = 0;
        else
            delay = (int) config.get("delay");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("command", command);
        ret.put("runAsEntity", runAsEntity);
        ret.put("delay", delay);
        return ret;
    }

    public void play(LivingEntity entity, Event event) {
        String c = command;
        if(c.contains("%player%") && (entity instanceof Player)) {
            Player p = (Player) entity;
            c = c.replace("%player%", p.getName());
        }
        if(delay == 0)
            Bukkit.dispatchCommand(runAsEntity ? entity : Bukkit.getConsoleSender(), c);
        else {
            final String cmd = c;
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand(runAsEntity ? entity : Bukkit.getConsoleSender(), cmd);
                }
            }.runTaskLater(Effects.getInstance(), delay);
        }
    }

    public List<String> getInfo(boolean detailed, String limit) {
        List<String> ret = getInfoBase();
        ret.add("Command: " + command);
        ret.add("Run as entity: " + (runAsEntity ? "Yes" : "No"));
        ret.add("Delay: " + delay);
        return ret;
    }

    public String getType() {
        return "Command";
    }
}
