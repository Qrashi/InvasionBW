package me.qrashi.plugins.bedwars.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MainShop {
    public static Inventory shop() {
        Inventory inv = InventoryHandeler.createInventory("§Shop", 45);
        for(int m = 28; m < 35;m++ ) {
            inv.setItem(m ,new ItemStack(Material.AIR));
        }
        for(int m = 37; m < 44;m++ ) {
            inv.setItem(m ,new ItemStack(Material.AIR));
        }

        //items for the QuickShop
        inv.setItem(0, InventoryHandeler.createStack(Material.SANDSTONE, "&7Blocks", Arrays.asList("&7Diese Blöcke sind Billig", "&7und eignen sich perfekt", "&7zum Bauen von Wegen"), "b(blocks)" ,2));
        inv.setItem(9, InventoryHandeler.createStack(Material.BRICK, "&7Bronze", Arrays.asList("&7Du benötigst", "&7ein Bronze um" ,"&7dieses Item zu kaufen"), "b(blocks)", 1));
        inv.setItem(1, InventoryHandeler.createStack(Material.END_STONE, "&7Endstone", Arrays.asList("&7Diese Blöcke sind Teuer", "&7und eignen sich perfekt", "&7zum einabuen des Betts"), "b(endstone)" ,1));
        inv.setItem(10, InventoryHandeler.createStack(Material.BRICK, "&7Bronze", Arrays.asList("&7Du benötigst", "&77 Bronze um" ,"&7dieses Item zu kaufen"), "b(endstone)", 7));
        //switch different shops
        inv.setItem(28, InventoryHandeler.createStack(Material.SANDSTONE, "&7Building blocks", Arrays.asList("&7Wechsle zu den", "&7Baumaterialen"), "s(building)", true));
        inv.setItem(29, InventoryHandeler.createStack(Material.CHAINMAIL_CHESTPLATE, "&7Armor", Arrays.asList("&7Wechsle zu den", "&7Rüstungsteilen"), "s(armor)", false));
        inv.setItem(30, InventoryHandeler.createStack(Material.STONE_PICKAXE, "&7Tools", Arrays.asList("&7Wechsle zu den", "&7Werkzeuge"), "s(tools)", false));
        inv.setItem(31, InventoryHandeler.createStack(Material.IRON_SWORD, "&7Weapons", Arrays.asList("&7Wechsle zu den", "&7Waffen"), "s(weapons)", false));
        inv.setItem(32, InventoryHandeler.createStack(Material.BOW, "&7Bows", Arrays.asList("&7Wechsle zu den", "&7Bögen", "&7Du scheiß Kind", "&7Spammen is für Noobs"), "s(bows)", false));
        inv.setItem(33, InventoryHandeler.createStack(Material.APPLE, "&7Food", Arrays.asList("&7Wechsle zum", "&7Essen", "&7Bist du Hungrig hmm"), "s(food)", false));
        inv.setItem(34, InventoryHandeler.createStack(Material.POTION, "&7Potions", Arrays.asList("&7Wechsle zu den", "&7Tränken"), "s(potions)"));
        inv.setItem(37, InventoryHandeler.createStack(Material.CHEST, "&7Chests", Arrays.asList("&7Wechsle zu den", "&7Kisten") ,"s(chests)"));
        return inv;
    }
}
