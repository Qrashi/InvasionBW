package me.qrashi.plugins.bedwars.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class SideBranchesShop {

    public static Inventory Baumaterialien() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Baumaterialien");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Arrays.asList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory Rüstung() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Rüstungsteile");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Arrays.asList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory Werkzeug() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Werkzeuge");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Arrays.asList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory Waffen() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Waffen");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Arrays.asList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }
    public static Inventory Boegen() {
        Inventory inv = Bukkit.createInventory(null, 45, "§7Bögen");

        for(int m = 0; m<45;m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        inv.setItem(0, InventoryHandeler.createStack(Material.BARRIER, "&eZurück", Arrays.asList("&7Zurück zum Hauptmenu"), "s(main)", false));
        return inv;
    }

}
