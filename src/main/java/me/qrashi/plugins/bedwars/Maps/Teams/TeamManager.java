package me.qrashi.plugins.bedwars.Maps.Teams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamManager implements Serializable {

    private final List<Team> teams;

    public TeamManager(List<Team> teamList) {
        teams = teamList;
    }
    public TeamManager() {teams = Collections.emptyList();
    }

    public List<Team> save() {
        return new ArrayList<>(teams);
    }

    public Team getTeamByColor(TeamColor toSearch) {
        for(Team team : teams) {
            if(team.getCol() == toSearch) {
                return team;
            }
        }
    return null;
    }


}
