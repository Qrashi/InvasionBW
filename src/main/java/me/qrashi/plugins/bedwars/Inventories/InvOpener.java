package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InvOpener {
    public static void openDelay(Player player, Inventory inv) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(BedWars.getInstance(), 1L);
    }
    public static void openDelay(HumanEntity player, Inventory inv) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(BedWars.getInstance(), 1L);
    }
    public static void closeDelay(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        }.runTaskLater(BedWars.getInstance(), 1L);
    }
    public static void closeDelay(HumanEntity player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        }.runTaskLater(BedWars.getInstance(), 1L);
    }
}