package me.qrashi.plugins.bedwars.Utils;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;

import java.util.ArrayList;

public class MapStatistics {

    public static int getTotalPages() {
        return BedWars.getMapManager().size() / 27;
    }

    public static int totalFinishedMaps() {
        ArrayList<GameMap> maps = BedWars.getMapManager().cloneList();
        int finishedMaps = 0;
        for(GameMap map : maps) {
            if(map.getAvailable()) {
                finishedMaps++;
            }
        }
        return finishedMaps;
    }

    public static int totalMaps() {
        return BedWars.getMapManager().size();
    }
    public static int totalTeams() {
        ArrayList<GameMap> maps = BedWars.getMapManager().cloneList();
        int teams = 0;
        for(GameMap map : maps) {
            teams = teams + map.getTeamManager().getTeams();
        }
        return teams;
    }
    public static int totalPlayers() {
        ArrayList<GameMap> maps = BedWars.getMapManager().cloneList();
        int players = 0;
        for(GameMap map : maps) {
            players = players + (map.getTeamManager().getTeamSize() * map.getTeamManager().getTeams() );
        }
        return players;
    }

}
