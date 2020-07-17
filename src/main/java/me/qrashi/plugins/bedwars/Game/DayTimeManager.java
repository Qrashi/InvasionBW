package me.qrashi.plugins.bedwars.Game;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Locations.Locations;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapTimeHolder;

public class DayTimeManager {

    public static void forceUpdate() {
        MapTimeHolder time;
        if(!BedWars.getGameManager().isSetUp()) {
            time = new MapTimeHolder(12500);
        } else {
            PlayType playType = BedWars.getGameManager().getPlayType();
            if(playType != PlayType.LOBBY) {
                time = BedWars.getGameManager().getMap().getTimeHolder();
            } else {
                time = new MapTimeHolder(12500);
            }
        }
        time.set();
    }
}
