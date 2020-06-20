package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.Utils.InventoryHandeler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MainShop {
    public static Inventory shop() {
        Inventory inv = Bukkit.createInventory(null, 45, "§6Shop");
        for(int m = 18; m <45; m++) {
            inv.setItem(m, InventoryHandeler.getNothing());
        }
        for(int m = 28; m < 35;m++ ) {
            inv.setItem(m ,new ItemStack(Material.AIR));
        }
        for(int m = 37; m < 44;m++ ) {
            inv.setItem(m ,new ItemStack(Material.AIR));
        }

        //items for the QuickShop
        inv.setItem(0, InventoryHandeler.createStack(Material.SANDSTONE, "&7Blöcke", Arrays.asList("&7Diese Blöcke sind Billig", "&7und eignen sich perfekt", "&7zum Bauen von Wegen"), "s(blöcke)" ,2));
        inv.setItem(9, InventoryHandeler.createStack(Material.BRICK, "&7Bronze", Arrays.asList("&7Du benötigst", "&7ein Bronze um" ,"&7dieses Item zu kaufen"), "s(blöcke)", 1));
        inv.setItem(1, InventoryHandeler.createStack(Material.END_STONE, "&7Endstein Blöcke", Arrays.asList("&7Diese Blöcke sind Teuer", "&7und eignen sich perfekt", "&7zum einabuen des Betts"), "s(endstone)" ,1));
        inv.setItem(10, InventoryHandeler.createStack(Material.BRICK, "&7Bronze", Arrays.asList("&7Du benötigst", "&77 Bronze um" ,"&7dieses Item zu kaufen"), "s(endstone)", 7));
        //switch different shops
        inv.setItem(28, InventoryHandeler.createStack(Material.SANDSTONE, "&7Bau Materialien", Arrays.asList("&7Wechsle zu den", "&7Baumaterialen"), "s(bau)", true));
        inv.setItem(29, InventoryHandeler.createStack(Material.CHAINMAIL_CHESTPLATE, "&7Rüstungsteile", Arrays.asList("&7Wechsle zu den", "&7Rüstungsteilen"), "s(rüstung)", false));
        inv.setItem(30, InventoryHandeler.createStack(Material.STONE_PICKAXE, "&7Werkzeuge", Arrays.asList("&7Wechsle zu den", "&7Werkzeuge"), "s(werkzeuge)", false));
        inv.setItem(31, InventoryHandeler.createStack(Material.IRON_SWORD, "&7Waffen", Arrays.asList("&7Wechsle zu den", "&7Waffen"), "s(waffen)", false));
        inv.setItem(32, InventoryHandeler.createStack(Material.BOW, "&7Bögen", Arrays.asList("&7Wechsle zu den", "&7Bögen", "&7Du scheiß Kind", "&7Spammen is für Noobs"), "s(bögen)", false));
        inv.setItem(33, InventoryHandeler.createStack(Material.APPLE, "&7Essen", Arrays.asList("&7Wechsle zum", "&7Essen", "&7Bist du Hungrig hmm"), "s(essen)", false));
        inv.setItem(34, InventoryHandeler.createStack(Material.POTION, "&7Tränke", Arrays.asList("&7Wechsle zu den", "&7Tränken"), "s(tränke)"));
        inv.setItem(36, InventoryHandeler.createStack(Material.CHEST, "&7Kisten", Arrays.asList("&7Wechsle zu den", "&7Kisten") ,"s(kisten)"));
        return inv;
    }
}
