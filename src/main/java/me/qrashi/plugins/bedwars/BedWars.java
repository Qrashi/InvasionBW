package me.qrashi.plugins.bedwars;

import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBox;
import me.qrashi.plugins.bedwars.BoundingBoxes.BoundingBoxActions;
import me.qrashi.plugins.bedwars.Commands.*;
import me.qrashi.plugins.bedwars.Game.Manager;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Inventories.InventoryHandeler;
import me.qrashi.plugins.bedwars.Inventories.Setup.SetupManager;
import me.qrashi.plugins.bedwars.Listeners.*;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Players.PlayerDataManager;
import me.qrashi.plugins.bedwars.Utils.BarSender;
import me.qrashi.plugins.bedwars.Utils.FileManager;
import me.qrashi.plugins.bedwars.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public final class BedWars extends JavaPlugin {

    private static World main;
    private static BoundingBoxActions actions;
    private static Utils utilClass;
    private static MapManager mapManager;
    private static BedWars instance;
    private FileManager manager;
    private static BarSender sender;
    private static Manager gameManager;
    private static Logger logger;
    private static PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("Loading map data");
        instance = this;
        actions = new BoundingBoxActions();
        utilClass = new Utils();
        manager = new FileManager();
        sender = new BarSender();
        gameManager = new Manager();
        mapManager = new MapManager();
        playerDataManager = new PlayerDataManager();


        //This will only be used until i will be able to save and load maps form a file.
        //logger.info("Loading Maps from file...");
        //GameMap map = new GameMap("TestMap", 2, 2, new BoundingBox(-61, 62, 24, -69, 67, 15), new SerializableLocation(-61, 62, 24));
        //GameMap map2 = new GameMap("TestMap 2", 4, 2, new BoundingBox(-61, 62, 24, -69, 67, 15), new SerializableLocation(-61, 62, 24));
        //ArrayList<GameMap> toLoad = new ArrayList<GameMap>();
        //toLoad.add(map);
        //mapManager.load(toLoad);
        //mapManager.createMap(map2);
        //mapManager.getMapByName("TestMap 2").setAvailable(true);
        //Please put the file loading in fileLoader.class
        loadMaps();
        logger.info("Finishing startup sequence");
        logger.info("Registering listeners");
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        listenerRegistration();
        commandRegistration();
        logger.info("Startup sequence completed");
        for(Player player : Bukkit.getOnlinePlayers()) {
            setWorld(player.getLocation().getWorld());
        }
    }

    @Override
    public void onDisable() {
        logger.info("Started maps saving to file.");
        saveMaps();
        logger.info("Saving complete, plugin shut down sequence complete.");
    }

    private void commandRegistration() {
        Objects.requireNonNull(getCommand("endgame")).setExecutor(new EndCommand());
        Objects.requireNonNull(getCommand("credits")).setExecutor(new CreditsCommand());
        Objects.requireNonNull(getCommand("save")).setExecutor(new SaveCommand());
        Objects.requireNonNull(getCommand("load")).setExecutor(new LoadCommand());
        Objects.requireNonNull(getCommand("leave")).setExecutor(new LeaveCommand());
    }
    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryHandeler(), this);
        pluginManager.registerEvents(new BlockListeners(), this);
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new FoodListener(), this);
        pluginManager.registerEvents(playerDataManager, this);
        //pluginManager.registerEvents(new DeathListener(), this);
    }
    public static void setWorld(World world) {
        main = world;
    }
    public static BarSender getBarSender() {
        return sender;
    }

    public static Logger getLogging() {
        return logger;
    }

    public static Manager getGameManager() {
        return gameManager;
    }
    public static World getWorld() {
        return main;
    }
    public static BoundingBoxActions getActions()  {
        return actions;
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
    public static PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public void reload() {
        gameManager.reset();
        SetupManager.reset();
        logger.info("Reloading plugin...");
        saveMaps();
        loadMaps();
    }

    public void saveMaps() {
        logger.info("Saving...");
        try {
            manager.saveMapManager(mapManager, "/maps.json");
            logger.info("Saved maps!");
        } catch (IOException e) {
            logger.warning("Error while saving maps!");
            e.printStackTrace();
        }
        logger.info("Saving complete!");
    }
    public void loadMaps() {
        logger.info("Loading maps...");
        try {
            mapManager = manager.loadMapManager("/maps.json");
            logger.info("Successfully loaded maps.");
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("Error while loading map data.");
            e.printStackTrace();
        }
        if(mapManager == null) {
            mapManager = new MapManager();
            ArrayList<GameMap> toLoad = new ArrayList<>();
            toLoad.add(new GameMap("&cError while importing maps!", 0, 0, new BoundingBox(-0, 0, 0, 0, 0, 0), new SerializableLocation(0, 0, 0)));
            mapManager.load(toLoad);
            logger.warning("Loaded backup maps...");
        }
        logger.info("Maps loaded.");
    }

}
