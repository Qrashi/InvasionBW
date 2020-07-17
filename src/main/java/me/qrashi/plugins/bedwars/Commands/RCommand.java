package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Bukkit.broadcastMessage(MessageCreator.t("&cWARNING: Reloading plugins, please wait!"));
        Bukkit.reload();
        return true;
    }
}
