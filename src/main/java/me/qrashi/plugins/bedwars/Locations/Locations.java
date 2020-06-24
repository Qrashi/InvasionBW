package me.qrashi.plugins.bedwars.Locations;

import me.qrashi.plugins.bedwars.Objects.SerializableLocation;

public class Locations {

    private static SerializableLocation spawnLoc = new SerializableLocation(0.5, 64.815, 0.5, 90, 0);

    public static SerializableLocation spawn() {
        return spawnLoc;
    }
}
