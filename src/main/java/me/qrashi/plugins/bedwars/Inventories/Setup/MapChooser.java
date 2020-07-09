package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.InventoryHandeler;
import me.qrashi.plugins.bedwars.Maps.GameMap;
import me.qrashi.plugins.bedwars.Maps.MapManager;
import me.qrashi.plugins.bedwars.Utils.MapStatistics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MapChooser {

    public static Inventory smartInv(Player player) {
        if(BedWars.getGameManager().getPlayType() == PlayType.BUILDING) {
            return getMapChooseInv(false, true, player);
        } else {
            return getMapChooseInv(true, false, player);
        }
    }

    public static Inventory smartInvSearch(Player player, ArrayList<GameMap> matches, String text) {
        if(BedWars.getGameManager().getPlayType() == PlayType.BUILDING) {
            return getMapChooseInvSearch(false, true, player, matches, text);
        } else {
            return getMapChooseInvSearch(true, false, player, matches, text);
        }
    }

    public static Inventory getMapChooseInv(boolean excludeUnfinished, boolean goBack, Player player) {
        Inventory inv = InventoryHandeler.createInventory("&aChoose a map");
        GameMap chosen = BedWars.getGameManager().getMap();
        inv.setItem(0, makeMapItem(chosen, excludeUnfinished, true, false));
        inv.setItem(8, makeContinue(excludeUnfinished));
        inv.setItem(4, InventoryHandeler.createStack(Material.GLASS_PANE, "&7Search maps", Arrays.asList("&7Left click to search for specific maps", "", "&cThis is in BETA!"), "z(searchmap)", true));
        MapManager man = BedWars.getMapManager();
        int page = BedWars.getPlayerDataManager().getData(player).getPage();
        int team = 0;
        for(int slot=9; slot<36; slot++) {
            team = team + (page * 27);
            inv.setItem(slot, new ItemStack(Material.AIR));
            if(man.isIntInList(team)) {
                inv.setItem(slot, makeMapItem(man.getMapByInt(team), excludeUnfinished, false, true));
            }
            team++;
        }
        inv.setItem(44, InventoryHandeler.createStack(Material.BOOK, "&5&lInformation", Arrays.asList("", "&f> &bChoose a map by &aleft clicking &bon it.", "", "&7General information:", "&fQ: &cWhy are there so many barriers that i cant click?", "&fA: &aThis is because you cant play a game on an &c&lUNFINISHED &amap.", "&fA: &aThe autor(s) of this map have marked it as &c\"incomplete\"&a.", "", "&fQ: &cWhat does the gunpowder mean?", "&fA: &aThis map is under construction. You can therefore edit it", "&fA: &c&lbut please ask the author for permission&a to do so.", "", "&7> More information on the authors can be found on &9Discord&7", "&7> &aClick me to get invited."), "z(dc)"));
        //Pages
        inv.setItem(38, InventoryHandeler.createStack(Material.REPEATER, "&7<< &a&lPrevious page &7<<", Collections.singletonList("&7Current page: " + page),"z(p-)"));
        inv.setItem(40, InventoryHandeler.createStack(Material.BREWING_STAND, "&5&lStatistics", Arrays.asList("&7Statistics for BW maps", "", "&f> &7Total Maps: &5" + MapStatistics.totalMaps(), "&f> &aFinished &7maps: " + MapStatistics.totalFinishedMaps(), "&f> &7Pages: " + MapStatistics.getTotalPages(), "&f> &7Total teams: " + MapStatistics.totalTeams(), "&f> &7Total player spaces: " + MapStatistics.totalPlayers(), "", "&bPlugin by InvasionDevs")));
        inv.setItem(42, InventoryHandeler.createStack(Material.COMPARATOR, "&7>> &a&lNext page &7>>", Collections.singletonList("&7Current page: " + page),"z(p+)"));
        if(goBack) {
            inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(setup)"));
        } else {
            inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cClose", "o(close)"));
        }
        return inv;
    }

    public static Inventory getMapChooseInvSearch(boolean excludeUnfinished, boolean goBack, Player player, ArrayList<GameMap> matches, String term) {
        String name;
        if(matches.isEmpty()) {
            name = "&cNo results found for \"&7" + term + "&c\"!";
        } else {
            if(matches.size() == 1) {
                name = "&a" + matches.size() + " &7map found for \"" + term + "&7\"";
            } else {
                name = "&a" + matches.size() + " &7maps found for \"" + term + "&7\"";
            }
        }
        Inventory inv = InventoryHandeler.createInventory(name);
        GameMap chosen = BedWars.getGameManager().getMap();
        inv.setItem(0, makeMapItem(chosen, excludeUnfinished, true, false));
        inv.setItem(8, makeContinue(excludeUnfinished));
        inv.setItem(4, InventoryHandeler.createStack(Material.GLASS_PANE, "&7Search maps", Arrays.asList("&7Left click to search for specific maps", "", "&cThis is in BETA!"), "z(searchmap)", true));
        MapManager man = BedWars.getMapManager();
        int page = BedWars.getPlayerDataManager().getData(player).getPage();
        int team = 0;
        for(int slot=9; slot<36; slot++) {
            team = team + (page * 27);
            inv.setItem(slot, new ItemStack(Material.AIR));
            if(isInList(matches, team)) {
                inv.setItem(slot, makeMapItem(matches.get(team), excludeUnfinished, false, true));
            }
            team++;
        }
        inv.setItem(44, InventoryHandeler.createStack(Material.BOOK, "&5&lInformation", Arrays.asList("", "&f> &bChoose a map by &aleft clicking &bon it.", "", "&7General information:", "&fQ: &cWhy are there so many barriers that i cant click?", "&fA: &aThis is because you cant play a game on an &c&lUNFINISHED &amap.", "&fA: &aThe autor(s) of this map have marked it as &c\"incomplete\"&a.", "", "&fQ: &cWhat does the gunpowder mean?", "&fA: &aThis map is under construction. You can therefore edit it", "&fA: &c&lbut please ask the author for permission&a to do so.", "", "&7> More information on the authors can be found on &9Discord&7", "&7> &aClick me to get invited."), "z(dc)"));
        //Pages
        inv.setItem(38, InventoryHandeler.createStack(Material.REPEATER, "&7<< &a&lPrevious page &7<<", Collections.singletonList("&7Current page: " + page),"z(p-)"));
        inv.setItem(40, InventoryHandeler.createStack(Material.BREWING_STAND, "&5&lStatistics", Arrays.asList("&7Statistics for BW maps", "", "&f> &7Total Maps: &5" + MapStatistics.totalMaps(), "&f> &aFinished &7maps: " + MapStatistics.totalFinishedMaps(), "&f> &7Pages: " + MapStatistics.getTotalPages(), "&f> &7Total teams: " + MapStatistics.totalTeams(), "&f> &7Total player spaces: " + MapStatistics.totalPlayers(), "", "&bPlugin by InvasionDevs")));
        inv.setItem(42, InventoryHandeler.createStack(Material.COMPARATOR, "&7>> &a&lNext page &7>>", Collections.singletonList("&7Current page: " + page),"z(p+)"));
        if(goBack) {
            inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cGo back", "o(setup)"));
        } else {
            inv.setItem(36, InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cClose", "o(close)"));
        }
        return inv;
    }

    private static ItemStack makeContinue(boolean excludeUnfinished) {
        if(BedWars.getGameManager().getMap() != null) {
            if(excludeUnfinished && !BedWars.getGameManager().getMap().getAvailable()) {
                return InventoryHandeler.createStack(Material.BARRIER, "&4&lInvalid map, choose another one", Arrays.asList("&7You have choosen an invalid map.", "&7Please &aselect another one&7 to continue."));
            } else {
                return InventoryHandeler.createStack(Material.LIME_STAINED_GLASS_PANE, "&aEverything set", Arrays.asList("&aMap ready!", "&7You may continue now or choose another one."), true);
            }
        } else {
            return InventoryHandeler.createStack(Material.RED_STAINED_GLASS_PANE, "&cNo map chosen", Arrays.asList("&7Please select a map by", "&aLeft clicking &7on it", "&7You will be able to continue afterwards."));
        }
    }

    private static ItemStack makeMapItem(GameMap map, boolean excludeUnfinished, boolean isChoosen, boolean checkEnch) {
        String name = "&c&lNo map choosen";
        String nameraw = "error";
        ArrayList<String> lore = new ArrayList<>();
        lore.add("&7Click on a map to choose another map");
        lore.add("");
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
            lore.add("&f> &7Name: \"&b" + nameraw + "&7\"");
            lore.add("&f> &7Teams: &a" + map.getTeamManager().getTeams());
            lore.add("&f> &7Team size: &a" + map.getTeamManager().getTeamSize());
            lore.add("&f> &7Max players: &6" + ((map.getTeamManager().getTeams()) * (map.getTeamManager().getTeamSize())));
            lore.add("");
            lore.add("&aLeft click to choose");
        }
        boolean avialible = false;
        if(map != null) {
            avialible = map.getAvailable();
        }
        if(isChoosen && BedWars.getGameManager().getMap() == null) {
            return InventoryHandeler.createStack(Material.BARRIER, name, lore);
        }
        boolean ench = false;
        if(checkEnch) {
            ench = (BedWars.getGameManager().getMap() == map);
        }
        if(excludeUnfinished && !avialible) {
            return InventoryHandeler.createStack(Material.BARRIER, "&cUnavailable: " + name, lore, ench);
        }
        else if (!excludeUnfinished && !avialible) {
            if(name.equalsIgnoreCase("&cNo map choosen")) {
                return InventoryHandeler.createStack(Material.GUNPOWDER, name, lore, ench);
            }
            if(isChoosen) {
                return InventoryHandeler.createStack(Material.END_CRYSTAL, name, lore, ench);
            }
            return InventoryHandeler.createStack(Material.GUNPOWDER, "&5Experimental: " + name, lore, "m(" + nameraw + ")", ench);
        } else { //map is avialible and unfinished are off or on
            if(name.equalsIgnoreCase("&cNo map choosen")) {
                return InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, name, lore, ench);
            }
            if(isChoosen) {
                return InventoryHandeler.createStack(Material.END_CRYSTAL, name, lore, ench);
            }
            return InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, "&aAvailable: " + name, lore, "m(" + nameraw + ")", ench);
            }
        }

        private static boolean isInList(ArrayList<GameMap> list, int isIn) {
            return isIn < list.size();
        }

    }
