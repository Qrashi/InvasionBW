package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MapChooser {

    public static Inventory getMapChooseInv(boolean excludeUnfinished) {
        Inventory inv = InventoryHandeler.createInventory("&aChoose a map");
        inv.setItem(0, InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, "&aChoosen map: " + name, Arrays.asList()));
        return inv;
    }

    private ItemStack makeMapItem(GameMap map, boolean excludeUnfinished) {
        String name = "none";
        List<String> lore = Arrays.asList("&7Click on a map to choose another map", "&7Map details:");
        if(BedWars.getGameManager().getMap() == null) {
            lore.add("&cNo information found for " + name);
        } else {
            name = map.getName();
            lore.add("&f> &7Name: " + map.getName());
            lore.add("&f> &7Teams: " + map.getTeamManager().getTeams());
            lore.add("&f> &7Team size: " + map.getTeamManager().getTeamSize());
            lore.add("");
            lore.add("&aRight click to choose");
        }
        if(excludeUnfinished && !map.getAvailable()) {
            return InventoryHandeler.createStack(Material.BARRIER, name, lore);
        }
        else {

        }
    }
}
