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

    private GameMap spectatingMap;

    public MapSpectate(GameMap map) {
        spectatingMap = map;
    }

    public void startSpectate() {
        BedWars.getGameManager().setPlayType(PlayType.SPECTATING_MAP);
        SerializableLocation location = spectatingMap.getBox().getMiddle();
        location.setY(spectatingMap.getLocations().getSpecspawn().getY());
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.closeInventory();
            player.getInventory().clear();
            player.teleport(location.getLocation());
            player.sendTitle(MessageCreator.t("&7Teleporting..."), MessageCreator.t("&aSpectating " + spectatingMap.getName()), 5, 100, 20);
            WorldBorderManager.forceUpdate();
        }
    }

    public GameMap getSpectatingMap() {
        return spectatingMap;
    }
}
