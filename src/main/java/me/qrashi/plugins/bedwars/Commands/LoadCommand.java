package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class LoadCommand implements CommandExecutor {

    boolean confirmed = false;
    boolean canLoad = true;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if(canLoad) {
            if(confirmed) {
                BedWars.getInstance().loadMaps();
               Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &aLoaded all map data and configurations."));
                canLoad = false;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        canLoad = true;
                    }
                }.runTaskLater(BedWars.getInstance(), 300);
                confirmed = false;
            } else {
                commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] &cIn order to not overwrite internal map data you have to use the /load command again. This is just for safety."));
                confirmed = true;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        confirmed = false;
                    }
                }.runTaskLater(BedWars.getInstance(), 300);
            }
        } else {
            commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] &cPlease wait a bit before loading again!"));
        }
        return true;
    }
}
