package me.qrashi.plugins.bedwars.Maps;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBox;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class MapManager implements Serializable {

    private ArrayList<GameMap> mapList;

    public MapManager load(ArrayList<GameMap> gameMaps) {
        mapList = gameMaps;
        return this;
    }
    public GameMap getMap(int ID) {
        return mapList.get(ID);
    }
    public ArrayList<GameMap> cloneList() {
        return new ArrayList<>(mapList);
    }

    public int size() {
        return mapList.size();
    }

    public boolean exists(String name) {
        for(GameMap map : mapList) {
            if(map.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
    return false;
    }
    public void createMap(GameMap map) {
        if(!exists(map.getName())) {
            mapList.add(map);
        }
    }

    public GameMap getMapByName(String name) {
        if (exists(name)) {
            for(GameMap map : mapList) {
                if(map.getName().equalsIgnoreCase(name)) {
                    return map;
                }
            }
        }
    return null;}

    public GameMap getMapByInt(int i) {
        if(isIntInList(i)) {
            return mapList.get(i);
        }
        return null;
    }
    public boolean isIntInList(int i) {
        return i < mapList.size();
    }

    public void makeNewMap(String name) {
        /*
        Every map has got 1000 blocks of x space and 1000 blocks in y direction.
         */
        int nextMap = mapList.size() + 1;
        SerializableLocation startLoc = new SerializableLocation(nextMap * 1000, 100, 0);
        BoundingBox boundingBox = new BoundingBox(startLoc, 500, 200);
        //BedWars
        //new GameMap(name, 2, 2, boundingBox, startLoc);
    }

}
