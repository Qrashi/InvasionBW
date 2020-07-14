package me.qrashi.plugins.bedwars.BuildMode;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.InventoryHandeler;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.Spawners.Spawner;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class BuildInv {

    @org.jetbrains.annotations.NotNull
    public static Inventory getBuildInv(Player player) {
        Inventory inv = InventoryHandeler.createInventory("&aBuild tools");
        inv.setItem(20 ,InventoryHandeler.createStack(Material.RED_BED, "&aManage teams", Arrays.asList("&7Inspect team settings", "", "&7This includes:", "&f> &7Team size", "&f> &7How many teams there are", "&f> &7Team spawnpoints", "&f> &7Team beds", "", "&aLeft click to open"), "o(teamman)"));
        inv.setItem(22 ,InventoryHandeler.createStack(Material.GOLD_INGOT, "&6Manage spawners", Arrays.asList("&7Inspect spawner settings", "", "&7This includes:", "&f> &7Setting the location of", "&f> &6Gold spawners", "&f> &7&lIron Spawners", "&f> &cBronze spawners", "&f> &bShops", "", "&aLeft click to open"), "o(spawnman)"));
        inv.setItem(24 ,InventoryHandeler.createStack(Material.CREEPER_SPAWN_EGG, "&7Manage spawnpoints", Arrays.asList("&7Inspect spawnpoint settings", "", "&7This includes:", "&f> &7Setting the spectator spawnpoint", "&f> &7Setting the map-spectating spawnpoint", "", "&aLeft click to open"), "o(spawnpman)"));
        inv.setItem(31, InventoryHandeler.createStack(Material.ANVIL, "&cRename map", Arrays.asList("&7Rename the current map.", "&aLeft click to open"), "b(rename)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cClose", "o(close)"));
        return inv;
    }

    public static Inventory getTeamManager() {
        Inventory inv = InventoryHandeler.createInventory("Team manager");
        inv.setItem(20, InventoryHandeler.createStack(Material.PAPER, "&7Team count", Arrays.asList("&7Current teams: &a" + BedWars.getGameManager().getMap().getTeamManager().getTeams(), "", "&a&l+ &aLeft click to increase &l+", "&c&l- &cRight click to decrease &l-"), "b(team+)", "b(team-)"));
        inv.setItem(22, InventoryHandeler.createStack(Material.PAPER, "&7Team size", Arrays.asList("&7Current team size: &a" + BedWars.getGameManager().getMap().getTeamManager().getTeamSize(), "", "&a&l+ &aLeft click to increase &l+", "&c&l- &cRight click to decrease &l-"), "b(size+)", "b(size-)"));
        inv.setItem(24, InventoryHandeler.createStack(Material.RED_BED, "&5Team spawns", Arrays.asList("&7Inspect team spawnpoint settings", "", "&7This includes:", "&f> &7Setting the spawn of teams", "&f> Setting the beds of a team"), "b(teamsett)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(buildinv)"));
        return inv;
    }
    public static Inventory getSpawnermanager() {
        GameMap map = BedWars.getGameManager().getMap();
        Inventory inv = InventoryHandeler.createInventory("Spawners manager");

        List<String> gold_list = Arrays.asList("&7Manage gold spawners:", "&aLeft click to create a gold spawner", "&c&l- &cRight click to delete &cALL&c gold spawners &l-", "", "&bList of all gold spawners:");
        for(Spawner spawner : map.getLocations().getSpawners()) {
            if(spawner.getType() == SpawnerType.GOLD) {
                SerializableLocation loc = spawner.getLoc();
                gold_list.add("&f> " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            }
        }

        List<String> iron_list = Arrays.asList("&7Manage iron spawners:", "&aLeft click to create a iron spawner", "&c&l- &cRight click to delete &cALL&c iron spawners &l-", "", "&bList of all iron spawners:");
        for(Spawner spawner : map.getLocations().getSpawners()) {
            if(spawner.getType() == SpawnerType.IRON) {
                SerializableLocation loc = spawner.getLoc();
                gold_list.add("&f> " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            }
        }

        List<String> bronze_list = Arrays.asList("&7Manage bronze spawners:", "&aLeft click to create a bronze spawner", "&c&l- &cRight click to delete &cALL&c bronze spawners &l-", "", "&bList of all bronze spawners:");
        for(Spawner spawner : map.getLocations().getSpawners()) {
            if(spawner.getType() == SpawnerType.BRONZE) {
                SerializableLocation loc = spawner.getLoc();
                gold_list.add("&f> " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            }
        }

        inv.setItem(20, InventoryHandeler.createStack(Material.GOLD_BLOCK, "&6Gold spawners", gold_list, "b(create_gold)", "b(delete_gold)"));
        inv.setItem(22, InventoryHandeler.createStack(Material.IRON_BLOCK, "&fIron spawners", iron_list, "b(create_iron)", "b(delete_iron)"));
        inv.setItem(24, InventoryHandeler.createStack(Material.BROWN_TERRACOTTA, "&cBronze spawners", bronze_list, "b(create_bronze)", "b(delete_bronze)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(buildinv)"));
        return inv;
    }
    public static Inventory getSpawnPointInv() {
        Inventory inv = InventoryHandeler.createInventory("Manage Spawnpoints");
        inv.setItem(21, InventoryHandeler.createStack(Material.WHITE_STAINED_GLASS_PANE, "&fLobby spawn", Arrays.asList("&7If you choose to spectate or", "&7build a map you will be teleported to this location", "&aLeft click to set this location"), "b(lobbyloc)"));
        inv.setItem(23, InventoryHandeler.createStack(Material.WHITE_STAINED_GLASS_PANE, "&fSpectator spawn", Arrays.asList("&7If you die in bedwars", "&7you will be teleported to this location", "&aLeft click to set this location"), "b(specloc)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(buildinv)"));
        return inv;
    }
}
