package me.qrashi.plugins.bedwars.Maps.Shops;

import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.Location;

public class Shop {

    private Location loc = new Location(BedWars.getWorld(), 0, 0, 0);

    public Shop(Location shopLocation) {
        loc.setX(shopLocation.getX());
        loc.setY(shopLocation.getY());
        loc.setZ(shopLocation.getZ());
    }

    public boolean isLocationSet() {
        return (loc.getY() != 0 && loc.getX() != 0 && loc.getZ() != 0);
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location toSet) {
        loc = toSet;
    }

    public void spawn() {
        //stub
    }

    public void despawn() {
        //stub
    }

}
