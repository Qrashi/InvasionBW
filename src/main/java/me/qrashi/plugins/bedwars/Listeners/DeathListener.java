package me.qrashi.plugins.bedwars.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity player = event.getEntity();
        if(player instanceof Player) {
            if(event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
                double hp = ((Player) player).getHealth();
                double damage = event.getDamage();
                if (damage >= hp) {
                    event.setDamage(0);
                    if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                        EntityDamageEvent.DamageCause cause = event.getCause();
                    }
                    Bukkit.broadcastMessage(((Player) player).getDisplayName() + " died");
                    ((Player) player).setHealth(20);
                    ((Player) player).setFoodLevel(20);
                }
            }
        }
    }
}
