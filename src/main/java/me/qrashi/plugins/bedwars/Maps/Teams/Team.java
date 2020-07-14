package me.qrashi.plugins.bedwars.Maps.Teams;


import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import org.bukkit.Location;

import java.io.Serializable;

public class Team implements Serializable {

    private final TeamColor col;
    private int teamSize;
    private Bed bed;
    private SerializableLocation spawnLoc = null;

    public Team(TeamColor color, int size, SerializableLocation spawn) {
        col = color;
        teamSize = size;
        spawnLoc = spawn;
    }
    public Team(TeamColor teamColor, int teamSize) {col = teamColor;
        this.teamSize = teamSize;}

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
        spawnLoc = new SerializableLocation(x, y, z);
    }
    public void setSpawn(SerializableLocation newloc) {
        spawnLoc = newloc;
    }

    public SerializableLocation getSpawn() {return spawnLoc;}

    public void setTeamSize(int newTeamSize) {
        teamSize = newTeamSize;
    }
}
