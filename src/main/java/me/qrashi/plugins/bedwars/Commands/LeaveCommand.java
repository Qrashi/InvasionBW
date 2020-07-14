package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LeaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (MapSpectateManager.isIsSpectating()) {
                if(commandSender.isOp()) {
                    MapSpectateManager.endSpectate();
                } else {
                    commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] Only operators can end spectator mode!"));
                }
                return true;
            }
        commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] &cA game cannot be ended using /leave, use /endgame instead!"));
        return true;
    }
}
