package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MapSpectate {

    private final GameMap spectatingMap;

    public MapSpectate(GameMap map) {
        spectatingMap = map;
    }

    public void startSpectate(boolean creative) {
        BedWars.getGameManager().setPlayType(PlayType.SPECTATING_MAP);
        SerializableLocation location = spectatingMap.getBox().getMiddle();
        location.setY(spectatingMap.getLocations().getSpecspawn().getY());
        Location toTp = location.getTpLocation();
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            if(creative) {
                player.setGameMode(GameMode.CREATIVE);
                player.setFlying(true);
            }
            player.closeInventory();
            player.getInventory().clear();
            player.teleport(toTp);
            if(creative) {
                player.sendTitle(MessageCreator.t("&7Teleporting..."), MessageCreator.t("&cInspecting not empty map"), 5, 40, 20);
            } else {
                player.sendTitle(MessageCreator.t("&7Teleporting..."), MessageCreator.t("&aSpectating " + spectatingMap.getName()), 5, 40, 20);
            }
            WorldBorderManager.forceUpdate();
        }
    }

    public GameMap getSpectatingMap() {
        return spectatingMap;
    }
}
