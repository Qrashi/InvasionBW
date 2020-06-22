package me.qrashi.plugins.bedwars.Game;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Utils.BarSender;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.scheduler.BukkitRunnable;

public final class Manager {

    GameMap map = null;
    int gameTime = 0;
    GameState gameState = GameState.SETUP;
    PlayType playType = PlayType.NONE;

    public Manager() {
        BarSender sender = BedWars.getBarSender();
        new BukkitRunnable() {
            @Override
            public void run() {
                if(map == null) {
                    sender.sendPerms("&cBedWars&7 is waiting for the completion of the game setup.", "&7Please &astart&7 the game using the &cbed &7item in your inventory");
                    gameState = GameState.SETUP;
                }
            }
        }.runTaskTimer(BedWars.getPlugin(BedWars.class), 0, 20);
    }

    public GameMap getMap() {
        return map;
    }
    public int getGameTime() {
        return gameTime;
    }
    public GameState getGameState() {
        return gameState;
    }
}
