package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        PlayType playType = BedWars.getGameManager().getPlayType();
        if (!BedWars.getGameManager().isSetUp()) {
            event.setCancelled(true);
        } else {
            switch (playType) {
                case SPECTATING_MAP:
                    event.setCancelled(true);
                case BUILDING:
                    if (!BedWars.getGameManager().getMap().getBox().isInside(new SerializableLocation(event.getBlock().getLocation()))) {
                        event.getPlayer().sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cYou can't break blocks outside of the build area!"));
                        event.setCancelled(true);
                    }
                case PLAYING:
                    if (!BedWars.getGameManager().getMap().getBox().isInside(new SerializableLocation(event.getBlock().getLocation()))) {
                        event.getPlayer().sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cYou can't break blocks outside of the build area!"));
                        event.setCancelled(true);
                    }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        PlayType playType = BedWars.getGameManager().getPlayType();
        if (!BedWars.getGameManager().isSetUp()) {
            event.setCancelled(true);
        } else {
            switch (playType) {
                case SPECTATING_MAP:
                    event.setCancelled(true);
                case BUILDING:
                    if (!BedWars.getGameManager().getMap().getBox().isInside(new SerializableLocation(event.getBlock().getLocation()))) {
                        event.getPlayer().sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cYou can't break blocks outside of the build area!"));
                        event.setCancelled(true);
                    }
                case PLAYING:
                    if (!BedWars.getGameManager().getMap().getBox().isInside(new SerializableLocation(event.getBlock().getLocation()))) {
                        event.getPlayer().sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cYou can't break blocks outside of the build area!"));
                        event.setCancelled(true);
                    }
            }
        }
    }
}
