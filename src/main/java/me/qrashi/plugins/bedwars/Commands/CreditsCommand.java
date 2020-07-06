package me.qrashi.plugins.bedwars.Commands;

import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreditsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(MessageCreator.t("&7[&cBedWars&7] BedWars was made by @InvasionDevs.\n&7[&cBedWars&7] AnvilGUI maintained by @WesJD (github/WesJD/AnvilGUI)"));
        return true;
    }
}
