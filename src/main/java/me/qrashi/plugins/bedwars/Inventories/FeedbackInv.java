package me.qrashi.plugins.bedwars.Inventories;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class FeedbackInv {
    public static Inventory getFeedbackInv() {
        Inventory inv = InventoryHandeler.createInventory("How did you enjoy &cBed&fWars&r?");
        inv.setItem(19, InventoryHandeler.createStack(Material.GREEN_WOOL, "&2It was very good", Arrays.asList("&a&l+ &aLeft click to choose", "&7Thanks for you feedback & time"), "f(1)"));
        inv.setItem(21, InventoryHandeler.createStack(Material.LIME_WOOL, "&aIt was good, it could be better", Arrays.asList("&a&l+ &aLeft click to choose", "&7Thanks for you feedback & time"), "f(2)"));
        inv.setItem(23, InventoryHandeler.createStack(Material.YELLOW_WOOL, "&eIt was ok", Arrays.asList("&a&l+ &aLeft click to choose", "&7Thanks for you feedback & time"), "f(3)"));
        inv.setItem(25, InventoryHandeler.createStack(Material.RED_WOOL, "&cIt was horrible", Arrays.asList("&a&l+ &aLeft click to choose", "&7Thanks for you feedback & time"), "f(4)"));
        return inv;
    }
}
