package me.qrashi.plugins.bedwars.BoundingBoxes;

import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import org.bukkit.Bukkit;

import java.io.Serializable;

public class BoundingBox implements Serializable {
    private final double x1;
    private final double y1;
    private final double z1;
    private final double x2;
    private final double y2;
    private final double z2;
    public BoundingBox(int x1, int y1, int z1, int x2, int y2, int z2) {
        if(x1 < x2) {
            this.x1 = x1;
            this.x2 = x2;
        }else{
            this.x1 = x2;
            this.x2 = x1;
        }
        if(y1 < y2) {
            this.y1 = y1;
            this.y2 = y2;
        }else{
            this.y1 = y2;
            this.y2 = y1;
        }
        if(z1 < z2) {
            this.z1 = z1;
            this.z2 = z2;
        }else{
            this.z1 = z2;
            this.z2 = z1;
        }
    }
    public BoundingBox(SerializableLocation loc1, SerializableLocation loc2) {

        /*
        ~1 is always bigger than ~2
        */
        double x1 = loc1.getX();
        double x2 = loc2.getX();
        double y1 = loc1.getY();
        double y2 = loc2.getY();
        double z1 = loc1.getZ();
        double z2 = loc2.getZ();
        if(x1 < x2) {
            this.x1 = x1;
            this.x2 = x2;
        }else{
            this.x1 = x2;
            this.x2 = x1;
        }
        if(y1 < y2) {
            this.y1 = y1;
            this.y2 = y2;
        }else{
            this.y1 = y2;
            this.y2 = y1;
        }
        if(z1 < z2) {
            this.z1 = z1;
            this.z2 = z2;
        }else{
            this.z1 = z2;
            this.z2 = z1;
        }
    }

    public BoundingBox(SerializableLocation middle, int radius, int height) {
        /*
        Returns quadratic BoundingBox
         */
        y1 = height;
        y2 = 0;
        x1 = middle.getX() + radius;
        x2 = middle.getX() - radius;
        z1 = middle.getZ() + radius;
        z2 = middle.getZ() - radius;

    }

    public double getX1() {
        return this.x1;
    }
    public double getX2() {
        return this.x2;
    }
    public double getY1() {
        return this.y1;
    }
    public double getY2() {
        return this.y2;
    }
    public double getZ1() {
        return this.z1;
    }
    public double getZ2() {
        return this.z2;
    }
    public SerializableLocation getMiddle() {
        return new SerializableLocation(((x1 - x2) / 2) + x2, 0, ((z1 - z2) / 2) + z2);
    }
    public double getMaxRadiusFromMiddle() {
        return Math.abs(Math.max((x1 - x2), (z1 - z2)));
    }

}
