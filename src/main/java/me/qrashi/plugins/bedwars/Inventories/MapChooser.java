package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class MapChooser {

    public static Inventory getMapChooseInv(boolean excludeUnfinished, boolean goBack) {
        Inventory inv = InventoryHandeler.createInventory("&aChoose a map");
        GameMap chosen = BedWars.getGameManager().getMap();
        inv.setItem(0, makeMapItem(chosen, excludeUnfinished, true, false));
        inv.setItem(8, makeContinue(excludeUnfinished));
        MapManager man = BedWars.getMapManager();
        for(int i=0; i<27; i++) {
            int slot = i + 9;
            inv.setItem(slot, new ItemStack(Material.AIR));
            if(man.isIntInList(i)) {
                inv.setItem(slot, makeMapItem(man.getMapByInt(i), excludeUnfinished, false, true));
            }
        }
        inv.setItem(44, InventoryHandeler.createStack(Material.BOOK, "&5&lInformation", Arrays.asList("", "&f> &bChoose a map by &aleft clicking &bon it.", "", "&7General information:", "&fQ: &cWhy are there so many barriers that i cant click?", "&fA: &aThis is because you cant play a game on an &c&lUNFINISHED &amap.", "&fA: &aThe autor(s) of this map have marked it as &c\"incomplete\"&a.", "", "&fQ: &cWhat does the gunpowder mean?", "&fA: &aThis map is under construction. You can therefore edit it", "&fA: &c&lbut please ask the author for permission&a to do so.", "", "&7> More information on the authors can be found on &9Discord&7", "&7> &aClick me to get invited."), "z(dc)"));
        if(goBack) {
            inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(setup)"));
        }
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
            return InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cNo map chosen", Arrays.asList("", "&cPlease select a map by", "&aLeft clicking &aon it", "&aYou will be able to continue afterwards."));
        }
    }

    private static ItemStack makeMapItem(GameMap map, boolean excludeUnfinished, boolean isChoosen, boolean checkEnch) {
        String name = "&c&lNo map choosen";
        String nameraw = "error";
        ArrayList<String> lore = new ArrayList<>();
        lore.add("&7Click on a map to choose another map");
        lore.add("&dMap details:");
        if(map == null) {
            lore.add("&bNo information found for &7\"&c" + name + "&7\"&c");
        } else {
            nameraw = map.getName();
            if(isChoosen) {
                name = "&a&lSelected: " + nameraw;
            } else {
                name = "&e&l" + nameraw;
            }
            if(!map.getAvailable()) {
                lore.add("");
                lore.add("&cThis map has been marked as \"&lINCOMPLETE&r&c\"!");
                lore.add("");
            }
            lore.add("&f> &7Name: &b" + nameraw);
            lore.add("&f> &7Teams: &a" + map.getTeamManager().getTeams());
            lore.add("&f> &7Team size: &a" + map.getTeamManager().getTeamSize());
            lore.add("&f> &7Max players: &6" + ((map.getTeamManager().getTeams()) * (map.getTeamManager().getTeamSize())));
            lore.add("");
            lore.add("&aLeft click to choose");
        }
        boolean avialible;
        if(map != null) {
            avialible = map.getAvailable();
        } else {
            avialible = false;
        }
        boolean ench = false;
        if(checkEnch) {
            ench = (BedWars.getGameManager().getMap() == map);
        }
        if(excludeUnfinished && !avialible) {
            return InventoryHandeler.createStack(Material.BARRIER, name, lore, ench);
        }
        else if (!excludeUnfinished && !avialible) {
            if(name.equalsIgnoreCase("&cNo map choosen")) {
                return InventoryHandeler.createStack(Material.GUNPOWDER, name, lore, ench);
            }
            if(isChoosen) {
                return InventoryHandeler.createStack(Material.REDSTONE_TORCH, name, lore, ench);
            }
            return InventoryHandeler.createStack(Material.GUNPOWDER, name, lore, "m(" + nameraw + ")", ench);
        } else { //map is avialible and unfinished are off or on
            if(name.equalsIgnoreCase("&cNo map choosen")) {
                return InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, name, lore, ench);
            }
            if(isChoosen) {
                return InventoryHandeler.createStack(Material.REDSTONE_TORCH, name, lore, ench);
            }
            return InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, name, lore, "m(" + nameraw + ")", ench);
            }
        }

    }
