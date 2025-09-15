package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("MessageEffect")
public class MessageEffect extends EffectWithLocation
{
    private String chatMessage;

    public MessageEffect(String name, String chatMessage) {
        setName(name);
        this.chatMessage = ChatColor.translateAlternateColorCodes('&', chatMessage);
    }

    public MessageEffect(Map<String, Object> config) {
        chatMessage = (String) config.get("chatMessage");
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("chatMessage", chatMessage);
        return ret;
    }

    public void play(Location location) {
        World world = location.getWorld();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(world) && player.getLocation().distance(location) <= 30) {
                player.sendMessage(chatMessage);
            }
        }
    }

    public List<String> getInfo(boolean detailed, String limit) {
        List<String> ret = getInfoBase();
        ret.add("Chat message: " + chatMessage);
        return ret;
    }

    public String getType() {
        return "Message";
    }

    public void modify(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
