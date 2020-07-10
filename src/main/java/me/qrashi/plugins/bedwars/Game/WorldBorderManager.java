package me.qrashi.plugins.bedwars.Game;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectate;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import me.qrashi.plugins.bedwars.Locations.Locations;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldBorderManager {

    public static void forceUpdate() {
        WorldBorder wb = BedWars.getWorld().getWorldBorder();
        if(MapSpectateManager.isIsSpectating()) {
            wb.setCenter(MapSpectateManager.getCurrentSpectate().getSpectatingMap().getBox().getMiddle().getLocation());
            wb.setSize(MapSpectateManager.getCurrentSpectate().getSpectatingMap().getBox().getMaxRadiusFromMiddle() + 10);
            return;
        }
        if(!BedWars.getGameManager().isSetUp()) {
            wb.setCenter(Locations.spawn().getLocation());
            wb.setSize(150);
            return;
        }
    }
}