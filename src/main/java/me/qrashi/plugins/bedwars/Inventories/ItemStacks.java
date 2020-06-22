package me.qrashi.plugins.bedwars.Inventories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ItemStacks {
    public static ItemStack getJoinItem() {
        return InventoryHandeler.createStack(Material.RED_BED, "&a&lGame setup", Arrays.asList("&7Click to open the setup GUI", "&7This can only be done by operators."), "o(setup)", true);
    }
}
