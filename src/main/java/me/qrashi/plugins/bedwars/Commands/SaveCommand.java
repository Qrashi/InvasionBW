package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveCommand implements CommandExecutor {

    private boolean canSave = true;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(canSave) {
            BedWars.getInstance().saveMaps();
            commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] &aSaved all map data and configurations."));
            canSave = false;
            new BukkitRunnable() {
                @Override
                public void run() {
                    canSave = true;
                }
            }.runTaskLater(BedWars.getInstance(), 300);
        } else {
            commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] &cPlease wait a bit before saving again!"));
        }
        return true;
    }
}
