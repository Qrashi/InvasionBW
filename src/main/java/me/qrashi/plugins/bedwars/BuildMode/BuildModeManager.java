package me.qrashi.plugins.bedwars.BuildMode;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.GameState;
import me.qrashi.plugins.bedwars.Game.Manager;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.AnvilGUI.AnvilGUI;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BuildModeManager {

    private static BuildMode build;
    private static boolean isInBuild = false;

    public static void startBuild(GameMap map) {
        Manager gameManager = BedWars.getGameManager();
        gameManager.setGameMap(map);
        gameManager.setSetUp(true);
        gameManager.setGameState(GameState.INGAME);
        SerializableLocation loc = gameManager.getMap().getLocations().getLobbyspawn().getCopy();
        Location toTp = loc.setY(loc.getY() + 1).getTpLocation();
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.CREATIVE);
            MessageCreator.sendTitle(player, "&aBuildMode", "&7Building " + map.getName(), 100);
            player.setFlying(true);
            player.getInventory().clear();
            player.teleport(toTp);
        }
        WorldBorderManager.forceUpdate();
        isInBuild = true;
        build = new BuildMode(map);
    }

    public static boolean isInBuild() {
        return isInBuild;
    }

    public static BuildMode getBuild() {
        return build;
    }

    public static void reset() {
        build = null;
        isInBuild = false;
    }
    public static void rename(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                        .onComplete(((player1, s) -> {
                            if(player == player1) {
                                rename_confirm(player, s);
                            }
                            return AnvilGUI.Response.text("&7[&aBuildMode&7] Trying to rename map to \"" + s + "\"");
                        }))
                        .title("Rename your map")
                        .text("New name here")
                        .plugin(BedWars.getInstance())
                        .open(player);
            }
        }.runTaskLater(BedWars.getInstance(), 1);
    }

    private static void rename_confirm(Player player, String text) {
        if(!BedWars.getMapManager().exists(text)) {
            BedWars.getGameManager().getMap().setName(text);
            player.closeInventory();
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &aSuccesfully renamed the map to \"" + text + "&a\"!"));
        } else {
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cThis name already exists"));
            new BukkitRunnable() {
                @Override
                public void run() {
                    new AnvilGUI.Builder()
                            .onComplete(((player1, s) -> {
                                if(player == player1) {
                                    rename_confirm(player, s);
                                }
                                return AnvilGUI.Response.text("&7[&aBuildMode&7] Trying to rename map to \"" + s + "\"");
                            }))
                            .title("Rename your map")
                            .text("Name already in use!")
                            .plugin(BedWars.getInstance())
                            .open(player);
                }
            }.runTaskLater(BedWars.getInstance(), 1);
        }
    }

}
