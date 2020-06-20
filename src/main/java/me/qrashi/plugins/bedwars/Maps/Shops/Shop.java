package me.qrashi.plugins.bedwars.Maps.Shops;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import org.bukkit.Location;

import java.io.Serializable;

public class Shop implements Serializable {

    private SerializableLocation shopLocation;

    public Shop(SerializableLocation shopLocation) {
        this.shopLocation = shopLocation;
    }

    public Shop() {}

    public SerializableLocation getLoc() {
        return shopLocation;
    }

    public void setLoc(SerializableLocation toSet) {
        shopLocation = toSet;
    }

    public void spawn() {
        //stub
    }

    public void despawn() {
        //stub
    }

}
