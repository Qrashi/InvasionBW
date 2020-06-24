package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.ItemStacks;
import me.qrashi.plugins.bedwars.Inventories.MainShop;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Inventories.SetupManager;
import me.qrashi.plugins.bedwars.Locations.Locations;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.GameMode;
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
        if(!SetupManager.getModeLocked()) {
            playerInv.clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(Locations.spawn().getLocationYP());
            player.setHealth(20);
            player.setFoodLevel(20);
            if (player.isOp()) {
                playerInv.setItem(4, ItemStacks.getJoinItem());
            }
        } else {
            if(BedWars.getGameManager().getPlayType() == PlayType.LOBBY) {
                playerInv.clear();
            }
            //team item?
        }

        //"rejoin" listeners
        if(SetupManager.getModeLocked() && BedWars.getGameManager().getPlayType() == PlayType.LOBBY) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendTitle(MessageCreator.t("&6Editing the lobby"), MessageCreator.t("&aHave fun!"), 10, 150, 20);
        }
    }
}
