package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class EndInventory {

    private static boolean confirmed = false;

    public static Inventory getInv() {
        Inventory inv = InventoryHandeler.createInventory("&cEnd the game");
        if(confirmed) {
            inv.setItem(21, InventoryHandeler.createStack(Material.LIME_STAINED_GLASS_PANE, "&aAlready confirmed", Arrays.asList("&7The confirmation will expire in", "&c10&7 seconds.")));
            inv.setItem(23, InventoryHandeler.createStack(Material.BARRIER, "&4End this game", Arrays.asList("", "&cYou can't undo this"), "z(eg)", "z(cancelend)"));
        } else {
            inv.setItem(21, InventoryHandeler.createStack(Material.LEVER, "&4Confirm to end", Arrays.asList("&7The confirmation will expire in", "&c10&7 seconds.", "&cLeft click to confirm"), "z(econf)", true));
            inv.setItem(23, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cPlease confirm using the lever", Arrays.asList("", "&f< &4Confirm using the lever")));
        }
        return inv;
    }

    public static boolean isConfirmed() {
        return confirmed;
    }

    public static void setConfirmed(boolean newConfirmed) {
        confirmed = newConfirmed;
    }
    static void confirm(Player player) {
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cA game end token was generated."));
        confirmed = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(confirmed = true) {
                    EndInventory.setConfirmed(false);
                    player.closeInventory();
                    if (!BedWars.getGameManager().isPlayTypeLocked()) {
                        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cThe game token expired."));
                    }
                }
            }
        }.runTaskLater(BedWars.getInstance(), 200);
    }

    public static void endGame(boolean error, String error_message) {
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cThe game ended."));
        if (error) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(MessageCreator.kickCreator("&4BedWas has encountered an issue", "&cERROR_CODE: " + error_message + " &4Please report at https://github.com/Fritz-CO-KG/InvasionBW/issues/new!", true));
            }
            BedWars.getPlugin(BedWars.class).reload();
        } else {
            if (BedWars.getGameManager().getPlayType() == PlayType.LOBBY) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.kickPlayer(MessageCreator.kickCreator("&cBedWas has ended by an admin", "&aThe server will now reset (not reboot) and you will be able to play in no time!", false));
                }
                BedWars.getPlugin(BedWars.class).reload();
            } else if (!BedWars.getGameManager().isSetUp()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.kickPlayer(MessageCreator.kickCreator("&cBedWas has ended by an admin", "&aThe server will now reset (not reboot) and you will be able to play in no time!", false));
                }
                BedWars.getInstance().reload();
            }
            if (BedWars.getGameManager().isSetUp()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.kickPlayer(MessageCreator.kickCreator("&cBedWas has ended by an admin", "&aThe server will now reset (not reboot) and you will be able to play in no time!", false));
                }
                BedWars.getInstance().reload();
            }
        }
    }
}
