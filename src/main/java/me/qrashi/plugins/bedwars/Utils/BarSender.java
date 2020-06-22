package me.qrashi.plugins.bedwars.Utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class BarSender {

    public void sendToAll(String bar) {
        TextComponent toSend = MessageCreator.generateComponent(bar);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, toSend);

        }
    }
    public void sendPerms(String normalPlayer, String opPlayer) {
        TextComponent toNormal = MessageCreator.generateComponent(normalPlayer);
        TextComponent toOp = MessageCreator.generateComponent(opPlayer);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.isOp()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, toOp);
            }
            else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, toNormal);
            }

        }
    }

}
