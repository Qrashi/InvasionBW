package me.qrashi.plugins.bedwars.Maps;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBox;
import me.qrashi.plugins.bedwars.Maps.Teams.Team;
import me.qrashi.plugins.bedwars.Maps.Teams.TeamManager;
import me.qrashi.plugins.bedwars.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class GameMap {
    private BoundingBox bbox;
    private String name;
    private boolean avialible;
    private LocationSaver locs;
    private TeamManager teamManager;

    public GameMap(String name, int teamNum, int teamSize, BoundingBox box) {
        construct(name, teamNum, teamSize, box);
    }
    public GameMap(String name, int teamNum, int teamSize,int x1, int y1, int z1, int x2, int y2, int z2) {
        construct(name, teamNum, teamSize, new BoundingBox(x1, y1, z1, x2, y2, z2));
    }
    private void construct(String name, int teamNum, int teamSize, BoundingBox box) {
        this.name = name;
        this.bbox = box;
        List<Team> teamList = new ArrayList<>();
        if(teamNum > 4) {teamNum = 4;}
        IntStream.range(0, teamNum).forEachOrdered(n -> {
            teamList.add(new Team(Utils.numToCol(n), teamSize));
        });
        this.teamManager = new TeamManager(teamList);
        this.avialible = false;
        this.locs = new LocationSaver();
    }

    public String getName() {
        return this.name;
    }

    public TeamManager getTeamManager() { return  teamManager; }

    public BoundingBox getBox() {
        return bbox;
    }

    public boolean getAvailable() {
        return avialible;
    }

    public void setAvailable(boolean available) {
        this.avialible = available;
    }

    public LocationSaver getLocations() {
        return locs;
    }
    public void clear() {
        BedWars.getBactions().replace(BedWars.getUtils().getMaterialList(), bbox);
    }
}
