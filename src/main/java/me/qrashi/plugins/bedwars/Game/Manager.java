package me.qrashi.plugins.bedwars.Game;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.EndInventory;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Utils.BarSender;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class Manager {

    GameMap map = null;
    int gameTime = 0;
    GameState gameState = GameState.SETUP;
    PlayType playType = PlayType.NONE;
    private boolean PlayTypeLocked = false;
    private boolean setUp = false;

    public Manager() {
        BarSender sender = BedWars.getBarSender();
        new BukkitRunnable() {
            @Override
            public void run() {
                if(gameState == GameState.END) {
                    sender.sendToAll("&cBedWars will shutdown soon, please wait!");
                } else {
                    if (!PlayTypeLocked) {
                        sender.sendPerms("&cBedWars&7 is waiting for the completion of the game setup.", "&7Please &astart&7 the game using the &cbed &7item in your inventory");
                        gameState = GameState.SETUP;
                    } else {
                        if (playType == PlayType.BUILDING) {
                            sender.sendToAll("&7The &cadmins&7 are &achoosing a map&7 to &a&lbuild&7...");
                        } else if (playType == PlayType.PLAYING) {
                            sender.sendToAll("&7The &cadmins&7 are &achoosing a map&7 to &a&lplay&7...");
                        } else if (playType == PlayType.SPECTATING_MAP) {
                            sender.sendPerms("&7You are spectating map \"" + MapSpectateManager.getCurrentSpectate().getSpectatingMap().getName() + "\"", "&7Stop spectating this map using &c/leave");
                        }
                        if (setUp) {
                            if (playType == PlayType.LOBBY) {
                                sender.sendPerms("&7Lobby editing mode is &a&lenabled&7. &aHave fun editing the lobby!", "&7Lobby editing mode is &a&lenabled&7. &cEnd the mode using &4/endgame");
                            }
                            if (playType == PlayType.BUILDING) {
                                String mapName = BedWars.getGameManager().getMap().getName();
                                sender.sendPerms("&7You are editing \"" + mapName + "\".", "&aEditing \"" + mapName + "\". &cEnd the game using &4/endgame&c. &7Use /build for more customisation!");
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(BedWars.getPlugin(BedWars.class), 0, 20);
    }

    public GameMap getMap() {
        return map;
    }

    public void setGameMap(GameMap toSet) {
        if(toSet != null) {
            map = toSet;
        }
    }

    public int getGameTime() {
        return gameTime;
    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState state) {
        gameState = state;
    }

    public PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(PlayType playType) {
        this.playType = playType;
    }
    public void finalizePlayType() {
        if(playType == PlayType.LOBBY) {
            setUp = true;
            gameState = GameState.INGAME;
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendTitle(MessageCreator.t("&6Editing the lobby"), MessageCreator.t("&aHave fun!"), 10, 150, 20);
                player.getInventory().clear();
            }
        }
        PlayTypeLocked = true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.closeInventory();
        }
    }

    public boolean isSetUp() {
        return setUp;
    }

    public boolean isPlayTypeLocked() {
        return PlayTypeLocked;
    }

    public void setSetUp(boolean setUp) {
        this.setUp = setUp;
    }
    public void reset() {
        map = null;
        gameTime = 0;
        gameState = GameState.SETUP;
        playType = PlayType.NONE;
        PlayTypeLocked = false;
        setUp = false;
        EndInventory.setConfirmed(false);
    }
}
