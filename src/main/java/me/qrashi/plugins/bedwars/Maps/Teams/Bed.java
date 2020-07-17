package me.qrashi.plugins.bedwars.Maps.Teams;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import org.bukkit.Material;

import java.io.Serializable;

public class Bed implements Serializable {

    private SerializableLocation loc;
    private boolean destroyed;

    public Bed(SerializableLocation location) {
        loc = location;
    }
    public Bed(int x, int y, int z) {
        loc = new SerializableLocation(x, y, z);
    }

    public void destroy() {
        destroyed = true;
        BedWars.getWorld().getBlockAt(loc.getLocation()).setType(Material.AIR);
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void setSpawn(SerializableLocation loc) {
        this.loc = loc;
    }

    public SerializableLocation getLoc() {
        return loc;
    }
    public void revive() {
        destroyed = false;
        BedWars.getWorld().getBlockAt(loc.getLocation()).setType(Material.RED_BED);
    }

    public void reset() {
        destroyed = false;
    }

}
