package me.qrashi.plugins.bedwars.Maps.Teams;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Utils.Utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class TeamManager implements Serializable {

    private List<Team> teams;

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
    public boolean isInList(int i) {
        return teams.size() > i;
    }
    public int getTeams() {
        return teams.size();
    }
    public int getTeamSize() {
        if(getTeams() == 0) {
            return 0;
        }
        return teams.get(0).getTeamSize();
    }
    public void setTeams(int teamNum) {
        List<Team> newteam = new ArrayList<>();
        if(teamNum > 4) {teamNum = 4;}
        IntStream.range(0, teamNum).forEachOrdered(n -> newteam.add(new Team(Utils.numToCol(n), getTeamSize())));
        teams = newteam;
    }
    public void setTeamSize(int teamSize) {
        for(Team team : teams) {
            team.setTeamSize(teamSize);
        }
    }
    public Team getTeamByInt(int teamNum) {
        return  teams.get(teamNum);
    }
    public List<Team> getTeamList() {return teams;}


}
