package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(!BedWars.getGameManager().isSetUp()) {
            event.setCancelled(true);
        } else if(BedWars.getGameManager().getPlayType() != PlayType.LOBBY) {
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!BedWars.getGameManager().isSetUp()) {
            event.setCancelled(true);
        } else if(BedWars.getGameManager().getPlayType() != PlayType.LOBBY) {
            event.setCancelled(true);
        }
    }
}
