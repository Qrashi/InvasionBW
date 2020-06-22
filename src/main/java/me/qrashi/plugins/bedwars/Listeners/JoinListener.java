package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.ItemStacks;
import me.qrashi.plugins.bedwars.Inventories.MainShop;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class JoinListener implements Listener {

    private boolean firstJoin = true;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(MessageCreator.t("&7[&2+&7] " + player.getName()));
        if (firstJoin) {
            BedWars.setWorld(player.getLocation().getWorld());
            firstJoin = false;
        }

        //not real
        BedWars.getMapManager().getMap(0).clear();
        InvOpener.openDelay(event.getPlayer(), MainShop.shop());

        //real listener

        Inventory playerInv = player.getInventory();
        playerInv.clear();
        if(player.isOp()) {
            playerInv.setItem(4, ItemStacks.getJoinItem());
        }
    }
}
