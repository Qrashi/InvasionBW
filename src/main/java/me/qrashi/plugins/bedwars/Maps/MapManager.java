package me.qrashi.plugins.bedwars.Maps;

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
    public List<GameMap> save() {
        return new ArrayList<>(mapList);
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

}
