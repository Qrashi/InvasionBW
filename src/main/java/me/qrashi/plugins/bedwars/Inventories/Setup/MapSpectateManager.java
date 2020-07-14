package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Commands.EndCommand;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Inventories.EndInventory;
import me.qrashi.plugins.bedwars.Inventories.InventoryHandeler;
import me.qrashi.plugins.bedwars.Inventories.ItemStacks;
import me.qrashi.plugins.bedwars.Locations.Locations;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class MapSpectateManager {

    //Idea: Every time you click on "spectate" a new instance is created

    private static MapSpectate currentSpectate;
    private static boolean isSpectating;
    private static PlayType prevState;
    private static boolean creative_mode;

    public static void spectate(GameMap map, boolean creative) {
        isSpectating = true;
        prevState = BedWars.getGameManager().getPlayType();
        BedWars.getGameManager().setPlayType(PlayType.SPECTATING_MAP);
        isSpectating = true;
        currentSpectate = new MapSpectate(map);
        currentSpectate.startSpectate(creative);
        creative_mode = creative;
    }
    public static MapSpectate getCurrentSpectate() {
        return currentSpectate;
    }
    public static void endSpectate() {
        if(creative_mode) {
            isSpectating = false;
            BedWars.getGameManager().setPlayType(prevState);
            EndInventory.endGame(true, "MAP_NOT_EMPTY");
        } else {
        isSpectating = false;
        BedWars.getGameManager().setPlayType(prevState);
        for(Player player : Bukkit.getOnlinePlayers()) {
            Inventory playerInv = player.getInventory();
            playerInv.clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(Locations.spawn().getLocationYP());
            player.setHealth(20);
            player.setFoodLevel(20);
            creative_mode = false;
            if (player.isOp()) {
                playerInv.setItem(4, ItemStacks.getJoinItem());
            }
            player.sendTitle(MessageCreator.t("&7Teleporting..."), MessageCreator.t("&aReturning to the lobby..."), 5, 40, 20);
            WorldBorderManager.forceUpdate();
        }
        }
    }

    public static boolean isIsSpectating() {
        return isSpectating;
    }
    public static boolean getCreative() {return creative_mode;}
}
