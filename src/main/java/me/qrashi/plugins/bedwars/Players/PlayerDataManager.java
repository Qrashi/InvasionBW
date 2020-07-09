package me.qrashi.plugins.bedwars.Players;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public final class PlayerDataManager implements Listener {

    HashMap<Player, PlayerData> playerData = new HashMap<>();

    public PlayerDataManager() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            playerData.put(player, new PlayerData());
        }
    }

    public PlayerData getData(Player player) {
        return playerData.get(player);
    }

    public void reset() {
        playerData.clear();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerData.put(event.getPlayer(), new PlayerData());
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerData.remove(event.getPlayer());
    }

}
