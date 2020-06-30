package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MapChooser {

    public static Inventory getMapChooseInv(boolean excludeUnfinished) {
        Inventory inv = InventoryHandeler.createInventory("&aChoose a map");
        GameMap chosen = BedWars.getGameManager().getMap();
        inv.setItem(0, makeMapItem(chosen, excludeUnfinished));
        inv.setItem(8, makeContinue(excludeUnfinished));
        MapManager man = BedWars.getMapManager();
        for(int i=0; i<36; i++) {
            int slot = i + 9;
            inv.setItem(slot, new ItemStack(Material.AIR));
            if(man.isIntInList(i)) {
                inv.setItem(slot, makeMapItem(man.getMapByInt(i), excludeUnfinished));
            }
        }
        inv.setItem(44, InventoryHandeler.createStack(Material.BOOK, "&7Information", Arrays.asList("", "&f> &bChoose a map by &aleft clicking &bon it.", "", "&7General information:", "&fQ: &cWhy are there so many barriers that i cant click?", "&fA: &aThis is because you cant play a game on an &c&lUNFINISHED &amap.", "&fA: &aThe autor(s) of this map have marked it as &c\"incomplete\"&a.", "", "&fQ: &cWhat does the gunpowder mean?", "&fA: &aThis map is under construction. You can therefore edit it, &c&lbut please ask the author for permission&a to do so.", "", "&7> More information on the authors can be found on &9Discord&7", "&7> &aclick me to get invited."), "z(dc)"));
        return inv;
    }

    private static ItemStack makeContinue(boolean excludeUnfinished) {
        if(BedWars.getGameManager().getMap() != null) {
            if(excludeUnfinished && !BedWars.getGameManager().getMap().getAvailable()) {
                return InventoryHandeler.createStack(Material.BARRIER, "&4&lInvalid map, choose another one", Arrays.asList("", "&cYou have choosen an invalid map.", "&aSelect another one to continue."));
            } else {
                return InventoryHandeler.createStack(Material.LIME_STAINED_GLASS_PANE, "&aEverything set", Arrays.asList("", "&aSuccessfully chosen a map.", "&aYou may continue now or choose another one."), true);
            }
        } else {
            return InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cNo map chosen", Arrays.asList("", "&cPlease select a map by &aLeft clicking &aon it", "&aYou will be able to continue afterwards."));
        }
    }

    private static ItemStack makeMapItem(GameMap map, boolean excludeUnfinished) {
        String name = "none";
        List<String> lore = Arrays.asList("&7Click on a map to choose another map", "&7Map details:");
        if(BedWars.getGameManager().getMap() == null) {
            lore.add("&cNo information found for " + name);
        } else {
            name = map.getName();
            if(!map.getAvailable()) {
                lore.add("");
                lore.add("&cThis map has been marked as &lincomplete&r&c!");
                lore.add("");
            }
            lore.add("&f> &7Name: " + map.getName());
            lore.add("&f> &7Teams: " + map.getTeamManager().getTeams());
            lore.add("&f> &7Team size: " + map.getTeamManager().getTeamSize());
            lore.add("");
            lore.add("&aLeft click to choose");
        }
        if(excludeUnfinished && !map.getAvailable()) {
            return InventoryHandeler.createStack(Material.BARRIER, name, lore);
        }
        else if (!excludeUnfinished && !map.getAvailable()) {
            return InventoryHandeler.createStack(Material.GUNPOWDER, name, lore, "m(" + map.getName() + ")");
        } else { //map is avialible and unfinished are off or on
            return InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, name, lore, "m(" + map.getName() + ")");
            }
        }
    }
