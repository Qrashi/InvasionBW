package me.qrashi.plugins.bedwars.Maps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class MapManager implements Serializable {

    private List<GameMap> mapList;

    public void load(List<GameMap> gameMaps) {
        mapList = gameMaps;
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
        mapList.add(map);
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

    ///}
}
