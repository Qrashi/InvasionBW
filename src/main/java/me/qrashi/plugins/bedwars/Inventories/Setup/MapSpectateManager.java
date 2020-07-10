package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Inventories.ItemStacks;
import me.qrashi.plugins.bedwars.Locations.Locations;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MapSpectateManager {

    //Idea: Every time you click on "spectate" a new instance is created

    private static MapSpectate currentSpectate;
    private static boolean isSpectating;
    private static PlayType prevState;

    public static void spectate(GameMap map) {
        isSpectating = true;
        prevState = BedWars.getGameManager().getPlayType();
        BedWars.getGameManager().setPlayType(PlayType.SPECTATING_MAP);
        isSpectating = true;
        currentSpectate = new MapSpectate(map);
        currentSpectate.startSpectate();
    }
    public static MapSpectate getCurrentSpectate() {
        return currentSpectate;
    }
    public static void endSpectate() {
        isSpectating = false;
        BedWars.getGameManager().setPlayType(prevState);
        for(Player player : Bukkit.getOnlinePlayers()) {
            Inventory playerInv = player.getInventory();
            playerInv.clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(Locations.spawn().getLocationYP());
            player.setHealth(20);
            player.setFoodLevel(20);
            if (player.isOp()) {
                playerInv.setItem(4, ItemStacks.getJoinItem());
            }
            player.sendTitle(MessageCreator.t("&7Teleporting..."), MessageCreator.t("&aReturning to the lobby..."), 5, 100, 20);
            WorldBorderManager.forceUpdate();
        }
    }

    public static boolean isIsSpectating() {
        return isSpectating;
    }
}
