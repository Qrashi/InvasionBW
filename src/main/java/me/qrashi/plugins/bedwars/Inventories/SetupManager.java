package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.GameState;
import me.qrashi.plugins.bedwars.Game.PlayType;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class SetupManager {

    private boolean modeLocked = false;

    public static Inventory mainInv() {
        Inventory inv = InventoryHandeler.createInventory("&c&lBed&7&lWars setup");
        PlayType state = BedWars.getGameManager().getPlayType();
        inv.setItem(19, InventoryHandeler.createStack(Material.RED_BED, "&a&lPlay a game", Collections.singletonList("&7Left click to &astart&7 a game"), "p(play)", (state == PlayType.PLAYING)));
        inv.setItem(22, InventoryHandeler.createStack(Material.GRASS_BLOCK, "&b&lBuild mode", Arrays.asList("&7Left click to &abuild&7 a map", "&7Click to see the \"build the lobby\" item"), "p(build)", (state == PlayType.BUILDING)));
        if(state == PlayType.BUILDING || state == PlayType.LOBBY) {
            inv.setItem(31, InventoryHandeler.createStack(Material.NETHER_STAR, "&c&lEdit the lobby", Arrays.asList("&7Please be &ccareful&7 when using this", "&7mode and be aware of potential", "&cgriefs &7that could occure.", "&c&lThere is no backup!"), "p(lobby)", (state == PlayType.LOBBY)));
        }
        inv.setItem(25, InventoryHandeler.createStack(Material.AIR, "&cYour selection", Arrays.asList("&7Select something to continue")));


        return inv;
    }

    public boolean getModeLocked() {
        return modeLocked;
    }

    public void setModeLocked(boolean modeLocked) {
        this.modeLocked = modeLocked;
    }
}
