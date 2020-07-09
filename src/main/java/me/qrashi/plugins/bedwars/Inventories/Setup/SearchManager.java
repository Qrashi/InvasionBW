package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Utils.AnvilGUI.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SearchManager {


    public static void startSearch(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                .onComplete(((completedPlayer, text) -> {
                    if(completedPlayer == player) {
                        search(completedPlayer, text);
                    }
                    return AnvilGUI.Response.text("&7[&cBedWars&7] Searching for \"" + text + "&7\"");
                }))
                .text("Enter search term")
                .title("Map search")
                .plugin(BedWars.getInstance())
                .open(player);
            }
        }.runTaskLater(BedWars.getInstance(), 1);
    }

    private static void search(Player player, String text) {
        ArrayList<GameMap> matchList = new ArrayList<>();
        for(GameMap map : BedWars.getMapManager().cloneList()) {
            if(map.getName().toLowerCase().contains(text.toLowerCase())) {
                matchList.add(map);
            }
        }
        InvOpener.openDelay(player, MapChooser.smartInvSearch(player, matchList, text));
    }

}
