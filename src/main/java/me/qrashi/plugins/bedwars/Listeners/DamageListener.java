package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity damaged = event.getEntity();
        if(damaged instanceof Player) {
            if (!BedWars.getGameManager().isSetUp()) {
                event.setCancelled(true);
            }
        }
    }
}
