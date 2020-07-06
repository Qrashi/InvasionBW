package me.qrashi.plugins.bedwars.Game;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Inventories.EndInventory;
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
    private boolean locked = false;
    private boolean setUp = false;

    public Manager() {
        BarSender sender = BedWars.getBarSender();
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!locked) {
                    sender.sendPerms("&cBedWars&7 is waiting for the completion of the game setup.", "&7Please &astart&7 the game using the &cbed &7item in your inventory");
                    gameState = GameState.SETUP;
                } else {
                    if(playType == PlayType.BUILDING) {
                        sender.sendToAll("&7The &cadmins&7 are &achoosing a map&7 to &abuild&7...");
                    }
                    else if (playType == PlayType.PLAYING) {
                        sender.sendToAll("&7The &cadmins&7 are &achoosing a map&7 to &aplay&7...");
                    }
                    if(setUp) {
                        if(playType == PlayType.LOBBY) {
                            sender.sendPerms("&7Lobby editing mode is &a&lenabled&7. &aHave fun editing the lobby!", "&7Lobby editing mode is &a&lenabled&7. &cEnd the mode using &4/endgame");
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

    public PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(PlayType playType) {
        if(!locked) {
            this.playType = playType;
        }
    }
    public void finalizePlayType() {
        if(playType == PlayType.LOBBY) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendTitle(MessageCreator.t("&6Editing the lobby"), MessageCreator.t("&aHave fun!"), 10, 150, 20);
                player.getInventory().clear();
            }
        }
        locked = true;
    }

    public boolean isSetUp() {
        return setUp;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setSetUp(boolean setUp) {
        this.setUp = setUp;
    }
    public void reset() {
        map = null;
        gameTime = 0;
        gameState = GameState.SETUP;
        playType = PlayType.NONE;
        locked = false;
        setUp = false;
        EndInventory.setConfirmed(false);
    }
}
