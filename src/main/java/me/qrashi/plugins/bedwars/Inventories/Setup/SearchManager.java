package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Utils.AnvilGUI.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SearchManager {

    private static boolean completed = false;

    public static void startSearch(Player player, String searchText) {


        new BukkitRunnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                .onComplete(((completedPlayer, text) -> {
                     completed = true;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            completed = false;
                        }
                    }.runTaskLater(BedWars.getInstance(), 2);
                    if(completedPlayer == player) {
                        search(completedPlayer, text);
                        BedWars.getPlayerDataManager().getData(completedPlayer).setPage(0);
                    }
                    return AnvilGUI.Response.text("&7[&cBedWars&7] Searching for \"" + text + "&7\"");
                }))
                .onClose((player1 -> {
                    if(player1 == player && !completed) {
                        InvOpener.openDelay(player1, MapChooser.smartInv(player1));
                    }
                }))
                .text(searchText)
                .title("Map search")
                .plugin(BedWars.getInstance())
                .open(player);
            }
        }.runTaskLater(BedWars.getInstance(), 1);
    }

    public static void search(Player player, String text) {
        ArrayList<GameMap> matchList = new ArrayList<>();
        for(GameMap map : BedWars.getMapManager().cloneList()) {
            if(map.getName().toLowerCase().contains(text.toLowerCase())) {
                matchList.add(map);
            }
        }
        InvOpener.openDelay(player, MapChooser.smartInvSearch(player, matchList, text));
    }

}
