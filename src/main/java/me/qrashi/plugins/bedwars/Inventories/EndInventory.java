package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class EndInventory {

    private static boolean confirmed = false;

    public static Inventory getInv() {
        Inventory inv = InventoryHandeler.createInventory("&cEnd the game");
        if(confirmed) {
            inv.setItem(21, InventoryHandeler.createStack(Material.LIME_STAINED_GLASS_PANE, "&aAlready confirmed", Arrays.asList("&7The confirmation will expire in", "&c10&7 seconds.")));
            inv.setItem(23, InventoryHandeler.createStack(Material.BARRIER, "&4End this game", Arrays.asList("", "&cYou can't undo this"), "z(eg)"));
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
        Bukkit.getScheduler().runTaskLater(BedWars.getPlugin(BedWars.class), () -> {
            EndInventory.setConfirmed(false);
            if(BedWars.getGameManager().isSetUp()) {
                Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cThe game token expired."));
            }
        }, 200);
    }

    static void endGame() {
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cThe game ended."));
        if(BedWars.getGameManager().getPlayType() == PlayType.LOBBY) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(MessageCreator.kickCreator("&cBedWas has ended by an admin", "&aThe server will now reboot and you will be able to play in no time!", true));
            }
            BedWars.getPlugin(BedWars.class).reload();
        } else if (!BedWars.getGameManager().isSetUp()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(MessageCreator.kickCreator("&cBedWas has ended by an admin", "&aThe server will now reboot and you will be able to play in no time!", true));
            }
            BedWars.getInstance().reload();
        }
        Bukkit.broadcastMessage(String.valueOf(BedWars.getGameManager().isSetUp()));
    }
}
