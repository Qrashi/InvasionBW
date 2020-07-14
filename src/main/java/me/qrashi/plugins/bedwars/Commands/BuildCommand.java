package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.BuildMode.BuildInv;
import me.qrashi.plugins.bedwars.BuildMode.BuildModeManager;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if(BuildModeManager.isInBuild()) {
            if(commandSender instanceof Player) {
                ((Player) commandSender).openInventory(BuildInv.getBuildInv((Player) commandSender));
                return true;
            } else {
                commandSender.sendMessage("Not available in the console");
                return true;
            }
        }
        commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] Build mode isn't running!"));
        return true;
    }
}
