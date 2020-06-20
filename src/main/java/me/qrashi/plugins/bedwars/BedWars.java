package me.qrashi.plugins.bedwars;

import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBox;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBoxActions;
import me.qrashi.plugins.bedwars.Listeners.JoinListener;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.FileManager;
import me.qrashi.plugins.bedwars.Utils.StackCreator;
import me.qrashi.plugins.bedwars.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BedWars extends JavaPlugin {

    private static World main;
    private static BoundingBoxActions bactions;
    private static Utils utilClass;
    private static MapManager mapManager;
    private static BedWars instance;
    private FileManager manager;

    @Override
    public void onEnable() {
        getLogger().info("Loading map data");
        instance = this;
        bactions = new BoundingBoxActions();
        utilClass = new Utils();
        manager = new FileManager();
        mapManager = new MapManager();
        //This will only be used until i will be able to save and load maps form a file.
        getLogger().info("Loading Maps from file...");
        GameMap map = new GameMap("TestMap", 2, 2, new BoundingBox(-61, 62, 24, -69, 67, 15), new SerializableLocation(-61, 62, 24));
        List<GameMap> toLoad = Collections.singletonList(map);
        //Please put the file loading in fileLoader.class
        try {
            mapManager.load((List<GameMap>) manager.loadFromFile("/maps.properties"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        getLogger().info("Finishing startup sequence");
        getLogger().info("Registering listeners");
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        listenerRegistration();

    }

    @Override
    public void onDisable() {
        getLogger().info("Saving...");
        //save
        try {
            manager.saveToFile(mapManager.save(),"/maps.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLogger().info("Saving complete, plugin shut down sequence complete.");
    }


    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new StackCreator(), this);
    }

    public static void setWorld(World world) {
        main = world;
    }

    public static World getWorld() {
        return main;
    }
    public static BoundingBoxActions getBactions()  {
        return bactions;
    }
    public static Utils getUtils() {
        return utilClass;
    }
    public static MapManager getMapManager() {
        return mapManager;
    }
    public static BedWars getInstance() {
        return instance;
    }
}
