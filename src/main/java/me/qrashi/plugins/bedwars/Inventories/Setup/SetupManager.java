package me.qrashi.plugins.bedwars.Inventories.Setup;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.BuildMode.BuildModeManager;
import me.qrashi.plugins.bedwars.Game.GameState;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Inventories.InventoryHandeler;
import me.qrashi.plugins.bedwars.Utils.AnvilGUI.AnvilGUI;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Collections;

public final class SetupManager {

    private static boolean modeLocked = false;

    public static Inventory mainInv(Player player) {
        Inventory inv = InventoryHandeler.createInventory("&c&lBed&7&lWars setup");
        PlayType state = BedWars.getGameManager().getPlayType();
        if (modeLocked) {
            if (state == PlayType.BUILDING) {
                inv.setItem(21, InventoryHandeler.createStack(Material.NETHER_STAR, "&a&lCreate a new map", Arrays.asList("&7Left click to &acreate a new", "&aspace &7for your map", "", "&cBe aware of griefs, there is &4no backup&c!"), "p(newmapb)"));
                inv.setItem(23, InventoryHandeler.createStack(Material.IRON_AXE, "&a&lEdit an existing map", Arrays.asList("&7Left click to edit an existing map", "", "&cBe aware of griefs, there is &4no backup&c!"), "p(editmapb)"));
            }
            else if (state == PlayType.PLAYING) {
                return MapChooser.getMapChooseInv(true, false, player);
            }
        } else {
            inv.setItem(19, InventoryHandeler.createStack(Material.REDSTONE_TORCH, "&a&lPlay a game", Collections.singletonList("&7Left click to &astart&7 a game"), "p(play)", (state == PlayType.PLAYING)));
            inv.setItem(22, InventoryHandeler.createStack(Material.SMOOTH_SANDSTONE, "&b&lBuild mode", Arrays.asList("&7Left click to &abuild&7 a map", "&7Click to see the \"build the lobby\" item"), "p(build)", (state == PlayType.BUILDING)));
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

    public static void reset() {
        modeLocked = false;
    }

    public static void mapInvFinished() {
        if(BedWars.getGameManager().getPlayType() == PlayType.BUILDING) {
            BedWars.getGameManager().setGameState(GameState.INGAME);
            BedWars.getGameManager().setSetUp(true);
            BuildModeManager.startBuild(BedWars.getGameManager().getMap());
        }
    }

    private static void createMap(String name, Player player) {
        if(BedWars.getMapManager().exists(name)) {
            player.sendMessage(MessageCreator.t("&7[&cBedWars&7] That map already exists! Try another one"));
            new BukkitRunnable() {
                @Override
                public void run() {
                    new AnvilGUI.Builder()
                            .onComplete((player, text) -> {
                                if (player.getUniqueId().equals(player.getUniqueId())) {
                                    createMap(text, player);
                                    InvOpener.closeDelay(player);
                                }
                                return AnvilGUI.Response.text("Checking map...");
                            })
                            .title("Enter your maps name")
                            .text(MessageCreator.t("&cName already in use"))
                            .plugin(BedWars.getInstance())
                            .open(player);
                }
            }.runTaskLater(BedWars.getInstance(), 2);
        } else {
            player.sendMessage(MessageCreator.t("&7[&cBedWars&7] Map name is valid. Please wait..."));
            for(Player players : Bukkit.getOnlinePlayers()) {
                InvOpener.closeLater(players);
                MessageCreator.sendTitle(players, "&7Please wait!", "&cThis may lag & crash!", 2000000);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(!BedWars.getMapManager().makeNewMap(name)) {
                        player.sendMessage(MessageCreator.t("&7[&cBedWars&7] &cMap invalid. CODE: ERR_MAP_NOT_EMPTY"));
                        return;
                    } else {
                        player.sendMessage(MessageCreator.t("&7[&cBedWars&7] &aYour map was created! You will now be teleported."));
                    }
                    BuildModeManager.startBuild(BedWars.getGameManager().getMap());
                }
            }.runTaskLater(BedWars.getInstance(), 20);
        }

    }

    public static void createMapStart(Player clickedPlayer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                new AnvilGUI.Builder()
                        .onComplete((player, text) -> {
                            if (player.getUniqueId().equals(clickedPlayer.getUniqueId())) {
                                createMap(text, player);
                                InvOpener.closeLater(player);
                            }
                            return AnvilGUI.Response.text("Checking map...");
                        })
                        .title("Enter your maps name")
                        .text("Name your map")
                        .plugin(BedWars.getInstance())
                        .open(clickedPlayer);
            }
        }.runTaskLater(BedWars.getInstance(), 1);

    }
}
