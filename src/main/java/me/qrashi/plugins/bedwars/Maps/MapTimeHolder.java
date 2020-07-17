package me.qrashi.plugins.bedwars.Maps;

import me.qrashi.plugins.bedwars.BedWars;

public class MapTimeHolder {
    private int time;
    public MapTimeHolder() {
        time = 0;
    }
    public MapTimeHolder(int timeInGame) {
        time = timeInGame;
    }
    public void setTime(int toSet) {
        time = toSet;
    }
    public int getTime() {
        return time;
    }
    public void set() {
        BedWars.getWorld().setTime(time);
    }
}
