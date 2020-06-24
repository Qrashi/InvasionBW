package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodListener implements Listener {
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        if(!BedWars.getGameManager().isSetUp()) {
            event.setCancelled(true);
        }
    }
}
