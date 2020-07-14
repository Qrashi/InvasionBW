package me.qrashi.plugins.bedwars.BuildMode;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;

public class BuildMode {

    private final GameMap buildMap;

    public BuildMode(GameMap map) {
        buildMap = map;
    }

    public GameMap getBuildMap() {
        return buildMap;
    }
}
