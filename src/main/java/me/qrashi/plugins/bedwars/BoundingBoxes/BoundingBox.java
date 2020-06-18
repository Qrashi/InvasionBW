package me.qrashi.plugins.bedwars.BoundingBoxes;

public class BoundingBox {
    private final int x1;
    private final int y1;
    private final int z1;
    private final int x2;
    private final int y2;
    private final int z2;
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
    public int getX1() {
        return this.x1;
    }
    public int getX2() {
        return this.x2;
    }
    public int getY1() {
        return this.y1;
    }
    public int getY2() {
        return this.y2;
    }
    public int getZ1() {
        return this.z1;
    }
    public int getZ2() {
        return this.z2;
    }

}
