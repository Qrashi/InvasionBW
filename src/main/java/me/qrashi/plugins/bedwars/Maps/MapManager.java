package me.qrashi.plugins.bedwars.Maps;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBox;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBoxActions;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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

    public boolean makeNewMap(String name) {
        /*
        Every map has got 1000 blocks of x space and 1000 blocks in y direction.
         */

        int nextMap = mapList.size() + 1;
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] Executing task &aCREATE_MAP&7, this may take some time."));
        SerializableLocation startLoc = new SerializableLocation(nextMap * 1000, 100, 0);
        SerializableLocation copyLoc = startLoc.getCopy();
        copyLoc.setY(copyLoc.getY() + 1);
        Location toTp = copyLoc.getTpLocation();
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.teleport(toTp);
        }
        BoundingBox boundingBox = new BoundingBox(startLoc, 450, 200);
        int notAir = BoundingBoxActions.checkEmpty(boundingBox);
        if(notAir > 0) {
            if(notAir > 100) {
                Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cError: Found " + notAir + " not air blocks in new maps location, aborting...\n&7[&cBedWars&7] Please contact an admin."));
                Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &aIf you want to solve this issue, search for not air blocks in this region and maybe destroy them."));
                BedWars.getGameManager().setSetUp(true);
                MapSpectateManager.spectate(new GameMap("ERROR_MAP", 1, 1, boundingBox, startLoc), true);
                return false;
            }
            Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cError: Found " + notAir + " not air blocks in new maps location."));
        }
        startLoc.getLocation().getBlock().setType(Material.DIAMOND_BLOCK);
        GameMap newMap = new GameMap(name, 2, 2, boundingBox, startLoc);
        createMap(newMap);
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] Task &aCREATE_MAP&7 finished, saving..."));
        BedWars.getInstance().saveMaps();
        BedWars.getGameManager().setGameMap(newMap);
        return true;
    }

}
