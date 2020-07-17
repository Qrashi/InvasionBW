package me.qrashi.plugins.bedwars.BuildMode;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.DayTimeManager;
import me.qrashi.plugins.bedwars.Game.GameState;
import me.qrashi.plugins.bedwars.Game.Manager;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import me.qrashi.plugins.bedwars.Maps.Teams.Bed;
import me.qrashi.plugins.bedwars.Maps.Teams.Team;
import me.qrashi.plugins.bedwars.Maps.Teams.TeamManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.AnvilGUI.AnvilGUI;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class BuildModeManager {

    private static BuildMode build;
    private static boolean isInBuild = false;

    public static void startBuild(GameMap map) {
        Manager gameManager = BedWars.getGameManager();
        gameManager.setGameMap(map);
        gameManager.setSetUp(true);
        gameManager.setGameState(GameState.INGAME);
        SerializableLocation loc = gameManager.getMap().getLocations().getLobbyspawn().getCopy();
        Location toTp = loc.setY(loc.getY() + 1).getTpLocation();
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.CREATIVE);
            MessageCreator.sendTitle(player, "&aBuildMode", "&7Building " + map.getName(), 100);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.getInventory().clear();
            player.teleport(toTp);
        }
        WorldBorderManager.forceUpdate();
        isInBuild = true;
        build = new BuildMode(map);
    }

    public static boolean isInBuild() {
        return isInBuild;
    }

    public static BuildMode getBuild() {
        return build;
    }

    public static void reset() {
        build = null;
        isInBuild = false;
    }
    public static void rename(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                        .onComplete(((player1, s) -> {
                            if(player == player1) {
                                rename_confirm(player, s);
                            }
                            return AnvilGUI.Response.text("Renaming...");
                        }))
                        .title("Rename your map")
                        .text("New name here")
                        .plugin(BedWars.getInstance())
                        .open(player);
            }
        }.runTaskLater(BedWars.getInstance(), 1);
    }

    private static void rename_confirm(Player player, String text) {
        if(!BedWars.getMapManager().exists(text)) {
            BedWars.getGameManager().getMap().setName(text);
            InvOpener.closeLater(player);
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &aSuccesfully renamed the map to \"" + text + "&a\"!"));
        } else {
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cThis name already exists"));
            new BukkitRunnable() {
                @Override
                public void run() {
                    new AnvilGUI.Builder()
                            .onComplete(((player1, s) -> {
                                if(player == player1) {
                                    rename_confirm(player, s);
                                }
                                return AnvilGUI.Response.text("Renaming...");
                            }))
                            .title("Rename your map")
                            .text("Name already in use!")
                            .plugin(BedWars.getInstance())
                            .open(player);
                }
            }.runTaskLater(BedWars.getInstance(), 1);
        }
    }
    public static void setTime(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                        .onComplete(((player1, s) -> {
                            if(player == player1) {
                                setTimeConfirm(s, player1);
                            }
                            return AnvilGUI.Response.text("Setting time...");
                        }))
                        .title("Set the time of your map")
                        .text("New time here")
                        .plugin(BedWars.getInstance())
                        .open(player);
            }
        }.runTaskLater(BedWars.getInstance(), 1);
    }
    private static void setTimeConfirm(String newTime, Player player) {
        if(isInt(newTime)) {
            int newTimeToSet = Integer.parseInt(newTime);
            BedWars.getGameManager().getMap().getTimeHolder().setTime(newTimeToSet);
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Time valid."));
            DayTimeManager.forceUpdate();
            InvOpener.closeLater(player);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new AnvilGUI.Builder()
                            .onComplete(((player1, s) -> {
                                if(player == player1) {
                                    setTimeConfirm(s, player1);
                                }
                                return AnvilGUI.Response.text("Setting time...");
                            }))
                            .title("Set the time of your map")
                            .text("Not an integer!")
                            .plugin(BedWars.getInstance())
                            .open(player);
                }
            }.runTaskLater(BedWars.getInstance(), 1);
        }
    }
    public static boolean checkReady() {
        GameMap map = BedWars.getGameManager().getMap();
        TeamManager teamManager = map.getTeamManager();
        boolean spawner_ok = map.getLocations().getSpawners().size() > 2;
        boolean teams_ok = false;
        for(Team team : teamManager.getTeamList()) {
            if (team.getBed() != null && team.getSpawn() != null) {
                teams_ok = true;
            } else {
                teams_ok = false;
            }
        }
        return (spawner_ok && teams_ok);
    }
    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException a) {
            return false;
        }
        return true;
    }
    public static void createSpawner(SpawnerType type, Block where, Player player) {
        if(where.getType() == Material.AIR) {
            where.setType(Material.GREEN_WOOL);
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Checking block..."));
            new BukkitRunnable() {
                @Override
                public void run() {
                    where.setType(Material.AIR);
                    if (BedWars.getGameManager().getMap().getLocations().getSpawnerAt(new SerializableLocation(where.getLocation())) == null) {
                        BedWars.getWorld().spawnParticle(Particle.CLOUD, where.getLocation().add(0.5, 1, 0.5), 250, 0.1, 0.1, 0.1, 0);
                        MessageCreator.sendTitle(player, "&aSuccess!", "&7Spawner created", 50);
                        BedWars.getGameManager().getMap().getLocations().addSpawner(type, new SerializableLocation(where.getLocation()));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
                        player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &aCreated spawner!"));
                    } else {
                        BedWars.getWorld().spawnParticle(Particle.BARRIER, where.getLocation().add(-0.5, 1, -0.5), 1, 0, 0, 0, 0);
                        MessageCreator.sendTitle(player, "&cFailure!", "&7There is already a spawner!", 75);
                        player.playEffect(player.getLocation(), Effect.ANVIL_BREAK, null);
                        player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cThere is already a spawner in this location!"));
                    }
                }
            }.runTaskLater(BedWars.getInstance(), 20);
        } else {
            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cCan't replace " + where.getType().toString() + "!"));
        }

    }
    public static void deleteSpawner(Block where, Player player) {
        where.setType(Material.WHITE_WOOL);
        player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Checking block..."));
        new BukkitRunnable() {
            @Override
            public void run() {
                where.setType(Material.AIR);
                if (BedWars.getGameManager().getMap().getLocations().getSpawnerAt(new SerializableLocation(where.getLocation())) != null) {
                    BedWars.getWorld().spawnParticle(Particle.CLOUD, where.getLocation().add(0.5, 1, 0.5), 250, 0.1, 0.1, 0.1, 0);
                    MessageCreator.sendTitle(player, "&aSuccess!", "&cSpawner deleted", 50);
                    BedWars.getGameManager().getMap().getLocations().removeSpawner(BedWars.getGameManager().getMap().getLocations().getSpawnerAt(new SerializableLocation(where.getLocation())));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 2);
                    player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cDeleted this spawner"));
                } else {
                    BedWars.getWorld().spawnParticle(Particle.BARRIER, where.getLocation().add(-0.5, 1, -0.5), 1, 0, 0, 0, 0);
                    MessageCreator.sendTitle(player, "&cFailure!", "&7There is no spawner!", 75);
                    player.playEffect(player.getLocation(), Effect.ANVIL_BREAK, null);
                    player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cThere is no spawner in this location!"));
                }
            }
        }.runTaskLater(BedWars.getInstance(), 20);

    }

}
