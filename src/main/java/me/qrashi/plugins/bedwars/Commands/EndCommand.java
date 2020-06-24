package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.EndInventory;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(BedWars.getGameManager().isSetUp()) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    Player player = (Player) sender;
                    player.openInventory(EndInventory.getInv());
                } else {
                    sender.sendMessage(MessageCreator.t("&cWolltest den anderen grad den spielspaß verderben wa?"));
                }
            } else {
                sender.sendMessage("vlt iwan mal in der console...");
            }
        } else {
            sender.sendMessage(MessageCreator.t("&7[&cBedWars&7] &cThere is no game running!"));
        }
        return true;
    }
}
