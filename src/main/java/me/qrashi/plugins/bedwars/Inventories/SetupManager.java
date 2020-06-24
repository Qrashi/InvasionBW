package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.GameState;
import me.qrashi.plugins.bedwars.Game.PlayType;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public final class SetupManager {

    private static boolean modeLocked = false;

    public static Inventory mainInv() {
        Inventory inv = InventoryHandeler.createInventory("&c&lBed&7&lWars setup");
        PlayType state = BedWars.getGameManager().getPlayType();
        if(modeLocked) {
            if(state == PlayType.BUILDING) {
                inv.setItem(21, InventoryHandeler.createStack(Material.NETHER_STAR, "&a&lCreate a new map", Arrays.asList("&7Left click to &acreate a new", "&aspace &7for your map", "", "&cBe aware of griefs, there is &4no backup&c!"), "p(newmap)"));
                inv.setItem(23, InventoryHandeler.createStack(Material.IRON_AXE, "&a&lEdit an existing map", Arrays.asList("&7Left click to edit an existing map", "", "&cBe aware of griefs, there is &4no backup&c!"), "p(editmap)"));
            }
        } else {
            inv.setItem(19, InventoryHandeler.createStack(Material.REDSTONE_TORCH, "&a&lPlay a game", Collections.singletonList("&7Left click to &astart&7 a game"), "p(play)", (state == PlayType.PLAYING)));
            inv.setItem(22, InventoryHandeler.createStack(Material.SANDSTONE, "&b&lBuild mode", Arrays.asList("&7Left click to &abuild&7 a map", "&7Click to see the \"build the lobby\" item"), "p(build)", (state == PlayType.BUILDING)));
            if (state == PlayType.BUILDING || state == PlayType.LOBBY) {
                inv.setItem(31, InventoryHandeler.createStack(Material.GRASS_BLOCK, "&c&lEdit the lobby", Arrays.asList("&7Please be &ccareful&7 when using this", "&7mode and be aware of potential", "&cgriefs &7that could occure.", "&c&lThere is no backup!"), "p(lobby)", (state == PlayType.LOBBY)));
            }
            if (state == PlayType.NONE) {
                inv.setItem(25, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&c&lCan't continue", Arrays.asList("&aSelect something &7to be", "&7able to continue")));
            } else {
                inv.setItem(25, InventoryHandeler.createStack(Material.LIME_STAINED_GLASS_PANE, "&a&lClick to continue", Arrays.asList("&7Selected: &a" + state.toString(), "", "&aClick to continue!"), "p(lockptype)"));
            }
        }


        return inv;
    }

    public static boolean getModeLocked() {
        return modeLocked;
    }

    public static void setModeLocked(boolean modeLocked) {
        SetupManager.modeLocked = modeLocked;
    }
}
