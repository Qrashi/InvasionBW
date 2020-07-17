package me.qrashi.plugins.bedwars.BuildMode;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.InventoryHandeler;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.Spawners.Spawner;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import me.qrashi.plugins.bedwars.Maps.Teams.Team;
import me.qrashi.plugins.bedwars.Maps.Teams.TeamManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuildInv {

    @org.jetbrains.annotations.NotNull
    public static Inventory getBuildInv(Player player) {
        Inventory inv = InventoryHandeler.createInventory("&aBuild tools");
        inv.setItem(13, getMapStatusItem());
        inv.setItem(20 ,InventoryHandeler.createStack(Material.RED_BED, "&aManage teams", Arrays.asList("&7Inspect team settings", "", "&7This includes:", "&f> &7Team size", "&f> &7How many teams there are", "&f> &7Team spawnpoints", "&f> &7Team beds", "", "&aLeft click to open"), "o(teamman)"));
        inv.setItem(22 ,InventoryHandeler.createStack(Material.GOLD_INGOT, "&6Manage spawners", Arrays.asList("&7Inspect spawner settings", "", "&7This includes:", "&f> &7Setting the location of", "&f> &6Gold spawners", "&f> &7&lIron Spawners", "&f> &cBronze spawners", "&f> &bShops", "", "&aLeft click to open"), "o(spawnman)"));
        inv.setItem(24 ,InventoryHandeler.createStack(Material.CREEPER_SPAWN_EGG, "&7Manage spawnpoints", Arrays.asList("&7Inspect spawnpoint settings", "", "&7This includes:", "&f> &7Setting the spectator spawnpoint", "&f> &7Setting the map-spectating spawnpoint", "&f> &7Setting the &3inGame time", "", "&aLeft click to open"), "o(spawnpman)"));
        inv.setItem(31, InventoryHandeler.createStack(Material.ANVIL, "&cRename map", Arrays.asList("&7Rename the current map.", "&aLeft click to open"), "b(rename)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cClose", "o(close)"));
        return inv;
    }

    private static ItemStack getMapStatusItem() {
        Material status = Material.PURPLE_STAINED_GLASS_PANE;
        String name = "&cOops, something unexpected happened!";
        ArrayList<String> lore = new ArrayList<>();
        ArrayList<String> errors = new ArrayList<>();
        GameMap map = BedWars.getGameManager().getMap();
        TeamManager teamManager = map.getTeamManager();
        boolean spawner_ok = map.getLocations().getSpawners().size() > 2;
        boolean teams_ok = true;
        for(Team team : teamManager.getTeamList()) {
            if(team.getBed() == null || team.getSpawn() == null) {
                teams_ok = false;
                errors.add("");
                errors.add("&f> &cUnfinished team: " + Utils.getTeamName(team));
                if (team.getBed() == null) {
                    errors.add("&f>> &cBed not set!");
                }
                if(team.getSpawn() == null) {
                    errors.add("&f>> &cSpawnpoint not set!");
                }
                //if(team.getSpawn().equals(map.getLocations().getLobbyspawn())) {
                  //  errors.add("&f>> &cTeam spawn same as lobbyspawn!");
                //}
                errors.add("");

            }
        }
        if(spawner_ok && teams_ok) {
            status = Material.LIME_STAINED_GLASS_PANE;
            name = "&aReady to finish";
            lore.add("&7This map has been checked and was found to be &aready&7!");
            lore.add("");
            lore.add("&7You may now click here to mark this map as \"&aavialible&7\"!");
            lore.add("&cDoing so &lwill end the game&c and &lkick all players!");
            lore.add("&a&lThanks for building an awesome map!");
            return InventoryHandeler.createStack(status, name, lore, "b(finish)", true);
        } else {
            status = Material.RED_STAINED_GLASS_PANE;
            name = "&cSome things are missing!";
            lore.add("&7This map has been checked and was found to be &cmissing some things&7!");
            lore.add("");
            lore.add("&cThings not ready yet:");
            lore.addAll(errors);
            lore.add("&cEnd of error report");
            return InventoryHandeler.createStack(status, name, lore);
        }
    }

    public static Inventory getTeamManager() {
        Inventory inv = InventoryHandeler.createInventory("Team manager");
        inv.setItem(20, InventoryHandeler.createStack(Material.PAPER, "&7Team count", Arrays.asList("&7Current teams: &a" + BedWars.getGameManager().getMap().getTeamManager().getTeams(), "", "&a&l+ &aLeft click to increase &l+", "&c&l- &cRight click to decrease &l-"), "b(team+)", "b(team-)"));
        inv.setItem(22, InventoryHandeler.createStack(Material.PAPER, "&7Team size", Arrays.asList("&7Current team size: &a" + BedWars.getGameManager().getMap().getTeamManager().getTeamSize(), "", "&a&l+ &aLeft click to increase &l+", "&c&l- &cRight click to decrease &l-"), "b(size+)", "b(size-)"));
        inv.setItem(24, InventoryHandeler.createStack(Material.RED_BED, "&5Team spawns", Arrays.asList("&7Inspect team spawnpoint settings", "", "&7This includes:", "&f> &7Setting the spawn of teams", "&f> &7Setting the beds of a team"), "b(teamsett)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(buildinv)"));
        return inv;
    }
    public static Inventory getSpawnermanager() {
        GameMap map = BedWars.getGameManager().getMap();
        Inventory inv = InventoryHandeler.createInventory("Spawners manager");

        ArrayList<String> gold_list = new ArrayList<>();
        gold_list.add("&7Manage gold spawners:"); gold_list.add("&aLeft click to create a gold spawner"); gold_list.add("&c&l- &cRight click to delete &cALL&c gold spawners &l-"); gold_list.add( "");gold_list.add("&bList of all gold spawners:");
        for(Spawner spawner : map.getLocations().getSpawners()) {
            if(spawner.getType() == SpawnerType.GOLD) {
                SerializableLocation loc = spawner.getLoc();
                gold_list.add("&f> " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            }
        }

        ArrayList<String> iron_list = new ArrayList<>();
        iron_list.add("&7Manage iron spawners:"); iron_list.add("&aLeft click to create a iron spawner"); iron_list.add("&c&l- &cRight click to delete &cALL&c iron spawners &l-"); iron_list.add( "");iron_list.add("&bList of all iron spawners:");
        for(Spawner spawner : map.getLocations().getSpawners()) {
            if(spawner.getType() == SpawnerType.IRON) {
                SerializableLocation loc = spawner.getLoc();
                iron_list.add("&f> " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            }
        }

        ArrayList<String> bronze_list = new ArrayList<>();
        bronze_list.add("&7Manage bronze spawners:"); bronze_list.add("&aLeft click to create a bronze spawner"); bronze_list.add("&c&l- &cRight click to delete &cALL&c bronze spawners &l-"); bronze_list.add( "");bronze_list.add("&bList of all bronze spawners:");
        for(Spawner spawner : map.getLocations().getSpawners()) {
            if(spawner.getType() == SpawnerType.BRONZE) {
                SerializableLocation loc = spawner.getLoc();
                bronze_list.add("&f> " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            }
        }

        inv.setItem(20, InventoryHandeler.createStack(Material.GOLD_BLOCK, "&6Gold spawners", gold_list, "b(create_gold)", "b(delete_gold)"));
        inv.setItem(22, InventoryHandeler.createStack(Material.IRON_BLOCK, "&fIron spawners", iron_list, "b(create_iron)", "b(delete_iron)"));
        inv.setItem(24, InventoryHandeler.createStack(Material.BROWN_TERRACOTTA, "&cBronze spawners", bronze_list, "b(create_bronze)", "b(delete_bronze)"));
        inv.setItem(30, InventoryHandeler.createStack(Material.BARRIER, "&cClear first spot", Arrays.asList("&7Left click to clear your", "&7first inventory slot", "&a&l+ &aLeft click"), "b(1clear)"));
        inv.setItem(32, InventoryHandeler.createStack(Material.RED_WOOL, "&cClear a spawner", Arrays.asList("&7Left click to get a &lspawner despawner", "&7right click on a block to delete a potential spawner", "&a&l+ &aLeft click"), "b(despawner)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(buildinv)"));
        return inv;
    }
    public static Inventory getSpawnPointInv() {
        Inventory inv = InventoryHandeler.createInventory("Manage Spawnpoints");
        SerializableLocation specspawn = BedWars.getGameManager().getMap().getLocations().getSpecspawn();
        String specspawn_loc = "&f> &7" + specspawn.getX() + " | " + specspawn.getY() + " | " + specspawn.getZ();
        SerializableLocation lobbyspawn = BedWars.getGameManager().getMap().getLocations().getLobbyspawn();
        String lobbyspawn_loc = "&f> &7" + lobbyspawn.getX() + " | " + lobbyspawn.getY() + " | " + specspawn.getZ();
        inv.setItem(20, InventoryHandeler.createStack(Material.WITHER_SKELETON_SKULL, "&fLobby spawn", Arrays.asList("&7If you choose to spectate or", "&7build a map you will be teleported to this location", "&a&l+ &aLeft click&7 to &bset&7 this &blocation", "&a&l+ &aRight click&7 to &bteleport", "", "&bCurrent location:", specspawn_loc), "b(lobbyloc)", "b(lobbyloc)"));
        inv.setItem(22, InventoryHandeler.createStack(Material.CLOCK, "&7Set ingame time", Collections.singletonList("&a&l+ &aLeft click&7 to set the game time"), "b(settime)"));
        inv.setItem(24, InventoryHandeler.createStack(Material.SKELETON_SKULL, "&fSpectator spawn", Arrays.asList("&7If you die in bedwars", "&7you will be teleported to this location", "&a&l+ &aLeft click&7 to &bset&7 this &blocation", "&a&l+ &aRight click&7 to &bteleport", "", "&bCurrent location:", lobbyspawn_loc), "b(specloc)", "b(lobbyloc)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(buildinv)"));
        return inv;
    }
    public static Inventory getTeamManagerTeamsInv() {
        Inventory inv = InventoryHandeler.createInventory("Team manager");
        int slot = 9;
        for(int team = 0; team < 18; team++) {
            inv.setItem(slot, makeTeamItem(team));
            slot++;
        }
        inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(teamman)"));
        return inv;
    }
    private static ItemStack makeTeamItem(int team) {
        if(BedWars.getGameManager().getMap().getTeamManager().isInList(team)) {
            Team real_team = BedWars.getGameManager().getMap().getTeamManager().getTeamByInt(team);
            ArrayList<String> lore = new ArrayList<>();
            lore.add("&7Information on this team:");
            lore.add("");
            lore.add("&f> &7Team index: &b" + team);
            lore.add("&f> &7Team size: &b" + real_team.getTeamSize());
            if (real_team.getBed() != null) {
                SerializableLocation loc = real_team.getBed().getLoc();
                lore.add("&f> &7Team bed: &3" + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            } else {
                lore.add("&f> &7Team bed: &cNot set yet!");
            }
            if (real_team.getSpawn() != null) {
                SerializableLocation loc = real_team.getSpawn();
                lore.add("&f> &7Team spawn: &3" + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
            } else {
                lore.add("&f> &7Team spawn: &cNot set yet!");
            }
            lore.add("&a&l+ &aLeft click&a to update the bed");
            lore.add("&a&l+ &cRight click&c to update the spawn.");
            return InventoryHandeler.createStack(Utils.teamToMaterial(real_team), Utils.getTeamName(real_team), lore, "d(" + team + ")", "l(" + team + ")");
        }
        return new ItemStack(Material.AIR);
    }

}
