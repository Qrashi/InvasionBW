package me.qrashi.plugins.bedwars.Maps.Teams;


import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.Location;

public class Team {

    private TeamColor col;
    private int teamSize;
    private Bed bed;
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public Team(TeamColor color, int size) {
        col = color;
        teamSize = size;
    }

    public TeamColor getCol() {
        return col;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void updateBed(Bed bed) {
        this.bed = bed;
    }

    public Bed getBed() {
        return bed;
    }

    public void setSpawn(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Location getSpawnLoc() {
        return new Location(BedWars.getWorld(), x, y, z);
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
