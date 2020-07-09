package me.qrashi.plugins.bedwars.Players;

import me.qrashi.plugins.bedwars.Maps.Teams.Team;

public class PlayerData {

    private int page;
    private Team team;
    private boolean isSpectator;

    public PlayerData() {
        page = 0;
        isSpectator = false;
        team = null;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public int getPage() {
        return page;
    }

    public Team getTeam() {
        return team;
    }

    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
