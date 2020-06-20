package me.qrashi.plugins.bedwars.Objects;

import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.Location;

import java.io.Serializable;

public class SerializableLocation implements Serializable {

    private int x;
    private int y;
    private int z;
    private int yaw = 0;
    private int pitch = 0;

    public SerializableLocation(int xc, int yc,  int zc) {
        x = xc;
        y = yc;
        z = zc;
    }
    public SerializableLocation(int xc, int yc,  int zc, int ya, int pi) {
        x = xc;
        y = yc;
        z = zc;
        yaw = ya;
        pitch = pi;
    }

    public int getPitch() {
        return pitch;
    }

    public int getYaw() {
        return yaw;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public Location getLocationYP() {
        return new Location(BedWars.getWorld(), x, y, z, yaw, pitch);
    }
    public Location getLocation() {
        return new Location(BedWars.getWorld(), x, y, z);
    }
    public void setLocation(Location loc) {
        x = (int) loc.getX();
        y = (int) loc.getY();
        z = (int) loc.getZ();
    }
    public boolean equals(SerializableLocation loc) {
        return (x == loc.getX() && y == loc.getY() && z == loc.getZ());
    }
}