package me.qrashi.plugins.bedwars.Utils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import me.qrashi.plugins.bedwars.Maps.Teams.Bed;

import javax.xml.transform.Templates;
import java.io.*;
import java.util.List;
import java.util.Map;

public final class FileManager {

    public void saveToFile(MapManager toSave, String name) throws IOException {
        String filePath = BedWars.getPlugin(BedWars.class).getDataFolder().getPath() + name;
        File file = new File(filePath);
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(toSave);
        s.close();
    }

    public MapManager loadFromFile(String name) throws IOException, ClassNotFoundException {
        String filePath = BedWars.getPlugin(BedWars.class).getDataFolder().getPath() + name;
        File file = new File(filePath);
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream s = new ObjectInputStream(f);
        MapManager toReturn = (MapManager) s.readObject();
        s.close();
        return toReturn;
    }

}
