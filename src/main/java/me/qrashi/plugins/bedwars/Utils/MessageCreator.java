package me.qrashi.plugins.bedwars.Utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

public class MessageCreator {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static String t(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static TextComponent generateComponent(String message) {
        return new TextComponent(t(message));
    }
}
