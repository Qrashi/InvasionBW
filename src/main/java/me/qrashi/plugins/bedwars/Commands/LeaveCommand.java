package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LeaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.isOp()) {
            if (!BedWars.getGameManager().isSetUp()) {
                if (BedWars.getGameManager().getPlayType() == PlayType.SPECTATING_MAP) {
                    MapSpectateManager.endSpectate();
                }
            }
        } else {
            commandSender.sendMessage("&7[&cBedWars&7] Only operators can end spectator mode!");
        }
        return true;
    }
}
