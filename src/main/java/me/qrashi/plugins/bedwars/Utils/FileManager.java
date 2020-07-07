package me.qrashi.plugins.bedwars.Utils;

import com.google.gson.Gson;
import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBox;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public final class FileManager {

    private Gson gson = new Gson();

    public void saveMapManager(MapManager toSave, String name) throws IOException {
        String filePath = BedWars.getPlugin(BedWars.class).getDataFolder().getPath() + name;
        File file = new File(filePath);
        generateIfNonexistent(file);
        String save = gson.toJson(toSave);
        Files.write(Paths.get(file.getAbsolutePath()), save.getBytes());
    }

    public MapManager loadMapManager(String name) throws IOException, ClassNotFoundException {
        String filePath = BedWars.getPlugin(BedWars.class).getDataFolder().getPath() + name;
        File file = new File(filePath);
        generateIfNonexistent(file);
        InputStream is = new FileInputStream(file);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }

        String fileAsString = sb.toString();
        if(fileAsString.length() < 1) {
            ArrayList<GameMap> toLoad = new ArrayList<>();
            toLoad.add(new GameMap("&cError while importing maps!", 0, 0, new BoundingBox(-0, 0, 0, 0, 0, 0), new SerializableLocation(0, 0, 0)));
            return new MapManager().load(toLoad);
        } else {
            return gson.fromJson(fileAsString, MapManager.class);
        }
    }

    boolean generateIfNonexistent(File file) throws IOException {
        file.getParentFile().mkdirs();
        return file.createNewFile();
    }

}
