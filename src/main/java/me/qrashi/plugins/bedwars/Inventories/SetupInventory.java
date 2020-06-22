package me.qrashi.plugins.bedwars.Inventories;

import org.bukkit.inventory.Inventory;

public class SetupInventory {

    public static Inventory mainInv() {
        Inventory inv = InventoryHandeler.createInventory("&c&lBed&7&lWars setup");
        return inv;
    }

}
