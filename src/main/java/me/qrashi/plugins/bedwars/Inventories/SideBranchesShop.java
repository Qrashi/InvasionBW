package me.qrashi.plugins.bedwars.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.Collections;

public class SideBranchesShop {

    public static Inventory build() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Baumaterialien");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Collections.singletonList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory armor() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Rüstungsteile");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Collections.singletonList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory tools() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Werkzeuge");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Collections.singletonList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory weapons() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Waffen");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Collections.singletonList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory bows() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Bögen");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Collections.singletonList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }

}
