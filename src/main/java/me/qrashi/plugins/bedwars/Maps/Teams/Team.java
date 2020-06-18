package me.qrashi.plugins.bedwars.Maps.Teams;

public class Team {

    private TeamColor col;
    private int teamSize;
    private Bed bed;

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
}
