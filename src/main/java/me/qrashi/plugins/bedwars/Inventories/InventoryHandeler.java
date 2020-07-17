package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.BuildMode.BuildInv;
import me.qrashi.plugins.bedwars.BuildMode.BuildModeManager;
import me.qrashi.plugins.bedwars.Game.GameState;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapChooser;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapSpectateManager;
import me.qrashi.plugins.bedwars.Inventories.Setup.SearchManager;
import me.qrashi.plugins.bedwars.Inventories.Setup.SetupManager;
import me.qrashi.plugins.bedwars.Maps.Spawners.Spawner;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import me.qrashi.plugins.bedwars.Maps.Teams.Bed;
import me.qrashi.plugins.bedwars.Maps.Teams.Team;
import me.qrashi.plugins.bedwars.Maps.Teams.TeamManager;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Players.PlayerData;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import me.qrashi.plugins.bedwars.Utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class InventoryHandeler implements Listener {

    //main Inventory handeler

    /*
    Information:
    Please enter your command definitions here:

    s: Shop
    o: Open
    m: Choose a map
    p: Setup
    z: Special operations
    +: Page+ in the mapChooser (for the search)
    -: Page- in the mapChooser (for the search)
    r: reserved for RightClick, don't use!
    b: BuildMode
    d: BuildMode (set beD)
    l: BuildMode (set spawnLocation)
    i: BuildMode (set beD)
    f: FeedBack inventory
    */

    private boolean handleLeftClick(char command, String arguments, Player player, boolean isRightClick, Block clicked) {
        if (isRightClick) {
            if (command == 'r') {
                char commandRightClick = arguments.charAt(0);
                StringBuilder argRight = new StringBuilder();
                IntStream.range(2, arguments.length() - 1).forEachOrdered(n -> argRight.append(arguments.charAt(n)));
                String argsRight = argRight.toString();
                //Bukkit.broadcastMessage("Total: " + arguments + " Command:" + commandRightClick + " args: " + argsRight);
                handleRightClick(commandRightClick, argsRight, player);
            }
        } else {
            switch (command) {
                case 'f':
                    if(BedWars.getGameManager().getGameState() == GameState.END) {
                        player.sendMessage(MessageCreator.t("&7[&aFeedback&7] Thank you for your feedback!"));
                        InvOpener.closeDelay(player);
                    }
                case 's':
                    switch (arguments) {
                        case "main":
                            InvOpener.openDelay(player, MainShop.shop());
                            break;
                        case "building":
                            InvOpener.openDelay(player, SideBranchesShop.build());
                            break;
                        case "armor":
                            InvOpener.openDelay(player, SideBranchesShop.armor());
                            break;
                        case "tools":
                            InvOpener.openDelay(player, SideBranchesShop.tools());
                            break;
                        case "weapons":
                            InvOpener.openDelay(player, SideBranchesShop.weapons());
                            break;
                        case "bows":
                            InvOpener.openDelay(player, SideBranchesShop.bows());
                            break;
                    }
                    break;
                case 'o':
                    switch (arguments) {
                        case "setup":
                            InvOpener.openDelay(player, SetupManager.mainInv(player));
                            break;
                        case "shop":
                            InvOpener.openDelay(player, MainShop.shop());
                            break;
                        case "close":
                            InvOpener.closeDelay(player);
                            break;
                        case "teamman":
                            if(BuildModeManager.isInBuild()) {
                                InvOpener.openDelay(player, BuildInv.getTeamManager());
                            }
                            break;
                        case "spawnman":
                            if(BuildModeManager.isInBuild()) {
                                InvOpener.openDelay(player, BuildInv.getSpawnermanager());
                            }
                            break;
                        case "buildinv":
                            if(BuildModeManager.isInBuild()) {
                                InvOpener.openDelay(player, BuildInv.getBuildInv(player));
                            }
                            break;
                        case "spawnpman":
                            if(BuildModeManager.isInBuild()) {
                                InvOpener.openDelay(player, BuildInv.getSpawnPointInv());
                            }
                            break;
                    }
                    break;
                case 'm':
                    if (BedWars.getMapManager().exists(arguments)) {
                        BedWars.getGameManager().setGameMap(BedWars.getMapManager().getMapByName(arguments));
                        if (BedWars.getGameManager().getPlayType() == PlayType.BUILDING) {
                            InvOpener.openDelay(player, MapChooser.getMapChooseInv(false, true, player));
                        } else if (BedWars.getGameManager().getPlayType() == PlayType.PLAYING) {
                            InvOpener.openDelay(player, MapChooser.getMapChooseInv(true, false, player));
                        }
                    }
                    break;
                case 'i':
                    if(BuildModeManager.isInBuild()) {
                        TeamManager teamManager = BedWars.getGameManager().getMap().getTeamManager();
                        int team = Integer.parseInt(arguments);
                        if(teamManager.isInList(team)) {
                            if(clicked != null) {
                                Bed oldBed = BedWars.getGameManager().getMap().getTeamManager().getTeamByInt(team).getBed();
                                if(oldBed != null) {
                                    SerializableLocation oldBedLoc = oldBed.getLoc();
                                    player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &cDestroying old bed..."));
                                    Block oldBedBlock = oldBedLoc.getBlock();
                                    oldBedBlock.setType(Material.AIR);
                                    oldBedBlock.getState().update();
                                    for(BlockFace face : BlockFace.values()) {
                                        oldBedBlock.getRelative(face, 1).getState().update();
                                    }
                                }
                                BedWars.getGameManager().getMap().getTeamManager().getTeamByInt(team).updateBed(new Bed(new SerializableLocation(clicked)));
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Registered bed for team " + Utils.getTeamName(teamManager.getTeamByInt(team))));
                                MessageCreator.sendTitle(player, "&aSuccess!", "&7Set the bed of [" + Utils.getTeamName(teamManager.getTeamByInt(team)) + "&7]", 75);
                            }
                        }
                    }
                    return false;
                case 'd':
                    if(BuildModeManager.isInBuild()) {
                        TeamManager teamManager = BedWars.getGameManager().getMap().getTeamManager();
                        int team = Integer.parseInt(arguments);
                        if(teamManager.isInList(team)) {
                            player.getInventory().setItem(0, createStack(Material.RED_BED, "&cBed&aRegisterer &7[" + Utils.getTeamName(teamManager.getTeamByInt(team)) + "&7]" , Arrays.asList("&a&l+ &aPlace a bed to register it!"), "i(" + team + ")"));
                            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Place the bed to register it! &7[" + Utils.getTeamName(teamManager.getTeamByInt(team)) + "&7]"));
                            InvOpener.closeDelay(player);
                        }
                    }
                    break;
                case 'b':
                    if(BuildModeManager.isInBuild()) {
                        switch (arguments) {
                            case "rename":
                                BuildModeManager.rename(player);
                                break;
                            case "team+":
                                if(BedWars.getGameManager().getMap().getTeamManager().getTeams() != 4) {
                                    BedWars.getGameManager().getMap().getTeamManager().setTeams(BedWars.getGameManager().getMap().getTeamManager().getTeams() + 1);
                                    InvOpener.openDelay(player, BuildInv.getTeamManager());
                                }
                                break;
                            case "size+":
                                BedWars.getGameManager().getMap().getTeamManager().setTeamSize(BedWars.getGameManager().getMap().getTeamManager().getTeamSize() + 1);
                                InvOpener.openDelay(player, BuildInv.getTeamManager());
                                break;
                            case "specloc":
                                SerializableLocation toSetS = new SerializableLocation(player);
                                BedWars.getGameManager().getMap().getLocations().setSpecspawn(toSetS);
                                InvOpener.closeDelay(player);
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &aSuccessfully &7set the spectator spawn to your location."));
                                break;
                            case "lobbyloc":
                                SerializableLocation toSetL = new SerializableLocation(player);
                                BedWars.getGameManager().getMap().getLocations().setLobbyspawn(toSetL);
                                InvOpener.closeDelay(player);
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] &aSuccessfully &7set the lobbyspawn to your location."));
                                break;
                            case "settime":
                                BuildModeManager.setTime(player);
                                break;
                            case "create_gold":
                                player.getInventory().setItem(0, createStack(Material.GOLD_BLOCK, "&6Create a gold spawner", "b(gold)"));
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Place this block &babove/next to/under &7your spawner and &cnot as a replacement!"));
                                InvOpener.closeDelay(player);
                                break;
                            case "create_iron":
                                player.getInventory().setItem(0, createStack(Material.IRON_BLOCK, "&6Create a &firon&6 spawner", "b(iron)"));
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Place this block &babove/next to/under &7your spawner and &cnot as a replacement!"));
                                InvOpener.closeDelay(player);
                                break;
                            case "create_bronze":
                                player.getInventory().setItem(0, createStack(Material.BROWN_TERRACOTTA, "&6Create a &cbronze&6 spawner", "b(bronze)"));
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Place this block &babove/next to/under &7your spawner and &cnot as a replacement!"));
                                InvOpener.closeDelay(player);
                                break;
                            case "gold":
                                if(clicked != null) {
                                    //Bukkit.broadcastMessage("k reg");
                                    BuildModeManager.createSpawner(SpawnerType.GOLD, clicked, player);
                                    InvOpener.closeDelay(player);
                                    return false;
                                }
                                break;
                            case "bronze":
                                if(clicked != null) {
                                    //Bukkit.broadcastMessage("k reg");
                                    BuildModeManager.createSpawner(SpawnerType.BRONZE, clicked, player);
                                    InvOpener.closeDelay(player);
                                    return false;
                                }
                                break;
                            case "iron":
                                if(clicked != null) {
                                    //Bukkit.broadcastMessage("k reg");
                                    BuildModeManager.createSpawner(SpawnerType.IRON, clicked, player);
                                    InvOpener.closeDelay(player);
                                    return false;
                                }
                                break;
                            case "delspawner":
                                if(clicked != null) {
                                    //Bukkit.broadcastMessage("k reg");
                                    BuildModeManager.deleteSpawner(clicked, player);
                                    InvOpener.closeDelay(player);
                                    return false;
                                }
                                break;
                            case "1clear":
                                player.getInventory().setItem(0, new ItemStack(Material.AIR));
                                InvOpener.closeDelay(player);
                                break;
                            case "despawner":
                                player.getInventory().setItem(0, createStack(Material.RED_WOOL, "&cSpawner despawner", Arrays.asList("&7Left click on a block to", "delete potential spawners."), "b(delspawner)"));
                                player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Place this block &babove/next to/under &7the potential spawner and &cnot as a replacement!"));
                                InvOpener.closeDelay(player);
                                break;
                            case "teamsett":
                                InvOpener.openDelay(player, BuildInv.getTeamManagerTeamsInv());
                                break;
                            case "finish":
                                if(BuildModeManager.checkReady()) {
                                    InvOpener.closeDelay(player);
                                    BedWars.getGameManager().getMap().setAvailable(true);
                                    BedWars.getGameManager().setGameState(GameState.END);
                                    Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cGame has ended! You will be kicked soon!"));
                                    for(Player online : Bukkit.getOnlinePlayers()) {
                                        MessageCreator.sendTitle(online, "&aMap finished!", "&7Please wait...", 75);
                                        online.setGameMode(GameMode.SPECTATOR);
                                        InvOpener.closeDelay(online);
                                    }
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            for (Player online : Bukkit.getOnlinePlayers()) {
                                                InvOpener.openDelay(online, FeedbackInv.getFeedbackInv());
                                            }
                                        }
                                    }.runTaskLater(BedWars.getInstance(), 40);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            for(Player online : Bukkit.getOnlinePlayers()) {
                                                MessageCreator.sendTitle(online, "&aBuildMode", "&7Leaving BuildMode", 75);
                                                Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cServer will reset in 10 seconds!"));
                                            }
                                        }

                                    }.runTaskLater(BedWars.getInstance(), 100);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cBuilding has ended!"));
                                            EndInventory.endGame(false, "");
                                        }
                                    }.runTaskLater(BedWars.getInstance(), 200);
                                }
                        }
                    }
                    break;
                case 'p':
                    switch (arguments) {
                        case "play":
                            BedWars.getGameManager().setPlayType(PlayType.PLAYING);
                            InvOpener.openDelay(player, SetupManager.mainInv(player));
                            break;
                        case "build":
                            BedWars.getGameManager().setPlayType(PlayType.BUILDING);
                            InvOpener.openDelay(player, SetupManager.mainInv(player));
                            break;
                        case "lobby":
                            BedWars.getGameManager().setPlayType(PlayType.LOBBY);
                            InvOpener.openDelay(player, SetupManager.mainInv(player));
                            break;
                        case "lockptype":
                            SetupManager.setModeLocked(true);
                            if (BedWars.getGameManager().getPlayType() != PlayType.LOBBY) {
                                InvOpener.openDelay(player, SetupManager.mainInv(player));
                            } else {
                                InvOpener.closeDelay(player);
                            }
                            BedWars.getGameManager().finalizePlayType();
                            //BedWars.getGameManager().setSetUp(true);
                            break;
                        case "editmapb":
                            InvOpener.openDelay(player, MapChooser.getMapChooseInv(false, true, player));
                            break;
                        case "newmapb":
                            SetupManager.createMapStart(player);
                            break;
                        case "contmap":
                            SetupManager.mapInvFinished();
                            break;


                    }
                    break;
                case '+':
                    PlayerData playerDataP = BedWars.getPlayerDataManager().getData(player);
                    playerDataP.setPage(playerDataP.getPage() + 1);
                    if (arguments.equalsIgnoreCase("")) {
                        InvOpener.openDelay(player, MapChooser.smartInv(player));
                    } else {
                        SearchManager.search(player, arguments);
                    }
                    break;
                case '-':
                    PlayerData playerDataN = BedWars.getPlayerDataManager().getData(player);
                    if (playerDataN.getPage() != 0) {
                        playerDataN.setPage(playerDataN.getPage() - 1);
                        if (arguments.equalsIgnoreCase("")) {
                            InvOpener.openDelay(player, MapChooser.smartInv(player));
                        } else {
                            SearchManager.search(player, arguments);
                        }
                    }
                    break;
                case 'z':
                    switch (arguments) {
                        case "searchmap":
                            SearchManager.startSearch(player, "ESC> cancel search");
                            break;
                        case "searchmap_as":
                            SearchManager.startSearch(player, "ESC> reset search");
                            break;
                        case "econf":
                            EndInventory.confirm(player);
                            InvOpener.closeDelay(player);
                            break;
                        case "eg":
                            if (EndInventory.isConfirmed()) {
                                EndInventory.endGame(false, "");
                            }
                            break;
                        case "dc":
                            TextComponent clickme = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7[&3Network&7] &9Link to our discord server &a[Click me]"));
                            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/t7sT9Ka"));
                            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder("Click to open the discord invitation").color(net.md_5.bungee.api.ChatColor.BLUE).create()));
                            player.spigot().sendMessage(clickme);
                            InvOpener.closeDelay(player);
                            break;
                    }
                    break;
            }
        }
        return true;
    }

    /*
    Information:
    Please enter your command definitions here:

    z: Special operations
    s: Spectate map
    b: BuildMode

    */

    private void handleRightClick(char command, String arguments, Player player) {
        switch (command) {
            case 'z':
                switch (arguments) {
                    case "clearsearch":
                        InvOpener.openDelay(player, MapChooser.smartInv(player));
                        break;
                    case "cancelend":
                        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cThe game end token was forced invalid."));
                        EndInventory.setConfirmed(false);
                        InvOpener.closeDelay(player);
                        break;
                }
                break;
            case 's':
                if(BedWars.getMapManager().exists(arguments) && BedWars.getGameManager().getPlayType() != PlayType.LOBBY && BedWars.getGameManager().getPlayType() != PlayType.SPECTATING_MAP) {
                    MapSpectateManager.spectate(BedWars.getMapManager().getMapByName(arguments), false);
                }
                break;
            case 'b':
                if(BuildModeManager.isInBuild()) {
                    switch (arguments) {
                        case "team-":
                            if (BedWars.getGameManager().getMap().getTeamManager().getTeams() != 2) {
                                BedWars.getGameManager().getMap().getTeamManager().setTeams(BedWars.getGameManager().getMap().getTeamManager().getTeams() - 1);
                                InvOpener.openDelay(player, BuildInv.getTeamManager());
                            }
                            break;
                        case "size-":
                            if (BedWars.getGameManager().getMap().getTeamManager().getTeamSize() != 1) {
                                BedWars.getGameManager().getMap().getTeamManager().setTeamSize(BedWars.getGameManager().getMap().getTeamManager().getTeamSize() - 1);
                                InvOpener.openDelay(player, BuildInv.getTeamManager());
                            }
                            break;
                        case "specloc":
                            player.teleport(BedWars.getGameManager().getMap().getLocations().getSpecspawn().getLocationYP());
                            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Warped you to the spectator spawnpoint"));
                            InvOpener.closeDelay(player);
                            break;
                        case "lobbyloc":
                            player.teleport(BedWars.getGameManager().getMap().getLocations().getLobbyspawn().getLocationYP());
                            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Warped you to the lobby spawnpoint"));
                            InvOpener.closeDelay(player);
                            break;
                        case "delete_gold":
                            List<Spawner> roRemoveG = new ArrayList<>(Collections.emptyList());
                            for(Spawner spawner : BedWars.getGameManager().getMap().getLocations().getSpawners()) {
                                if(spawner.getType() == SpawnerType.GOLD) {
                                    roRemoveG.add(spawner);
                                }
                            }
                            BedWars.getGameManager().getMap().getLocations().removeSpawner(roRemoveG);
                            InvOpener.openDelay(player, BuildInv.getSpawnermanager());
                            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7]&c Deleted ALL &6gold &cspawners!"));
                            break;
                        case "delete_iron":
                            List<Spawner> roRemoveI = new ArrayList<>(Collections.emptyList());
                            for(Spawner spawner : BedWars.getGameManager().getMap().getLocations().getSpawners()) {
                                if(spawner.getType() == SpawnerType.IRON) {
                                    roRemoveI.add(spawner);
                                }
                            }
                            BedWars.getGameManager().getMap().getLocations().removeSpawner(roRemoveI);
                            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7]&c Deleted ALL &firon &cspawners!"));
                            InvOpener.openDelay(player, BuildInv.getSpawnermanager());
                            break;
                        case "delete_bronze":
                            List<Spawner> roRemoveB = new ArrayList<>(Collections.emptyList());
                            for(Spawner spawner : BedWars.getGameManager().getMap().getLocations().getSpawners()) {
                                if(spawner.getType() == SpawnerType.BRONZE) {
                                    roRemoveB.add(spawner);
                                }
                            }
                            BedWars.getGameManager().getMap().getLocations().removeSpawner(roRemoveB);
                            InvOpener.openDelay(player, BuildInv.getSpawnermanager());
                            player.sendMessage(MessageCreator.t("&7[&aBuildMode&7]&c Deleted ALL bronze &cspawners!"));
                            break;
                    }
                }
                break;
            case 'l':
                if(BuildModeManager.isInBuild()) {
                    TeamManager teamManager = BedWars.getGameManager().getMap().getTeamManager();
                    int team = Integer.parseInt(arguments);
                    if(teamManager.isInList(team)) {
                        Team toEdit = teamManager.getTeamByInt(team);
                        toEdit.setSpawn(new SerializableLocation(player.getLocation()));
                        MessageCreator.sendTitle(player, "&aSuccess!", "&7Set the respawn location", 60);
                        player.sendMessage(MessageCreator.t("&7[&aBuildMode&7] Set the team respawn location to your location!"));
                        InvOpener.closeDelay(player);
                    }
                }
        }
    }

    private final List<Action> okAct = new ArrayList<>();
    public InventoryHandeler() {
        okAct.add(Action.RIGHT_CLICK_AIR);
        okAct.add(Action.RIGHT_CLICK_BLOCK);
    }

    //static util methods

    public static Inventory createInventory(String name, int size) {
        Inventory inv = Bukkit.createInventory(null, size, MessageCreator.t(name));
        IntStream.range(0, size).forEachOrdered(n -> inv.setItem(n, getNothing()));
        return inv;
    }
    public static Inventory createInventory(String name) {
        return createInventory(name, 45);
    }

    //Main StackCreator method
    public static ItemStack createStack(Material material, String name, List<String> lore, String command, String commandRightClick ,boolean enchanted, int amount) {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta stack_meta = stack.getItemMeta();
        assert stack_meta != null;
        stack_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> newlore = new java.util.ArrayList<>(Collections.emptyList());
        if(!lore.isEmpty()) {
            for (String i : lore) {
                newlore.add(ChatColor.translateAlternateColorCodes('&', i));
            }
        }
        if(!commandRightClick.equals("")) { newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&or(" + commandRightClick + ")")); }
        if(!command.equals("")) { newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&o" + command)); }
        newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&oInvasionBW"));
        stack_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
        stack_meta.setLore(newlore);
        stack.setItemMeta(stack_meta);
        if(enchanted){
            stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        return stack;
    }

    //Other StackCreators which access the method above

    public static ItemStack createStack(Material material, String name, List<String> lore, String command, String rightCommand) {
        return createStack(material, name, lore, command, rightCommand, false, 1);
    }

    public static ItemStack createStack(Material material, String name, List<String> lore, String command, String rightCommand, boolean enchanted) {
        return createStack(material, name, lore, command, rightCommand, enchanted, 1);
    }

    public static ItemStack createStack(Material material, String name, List<String> lore, String command, boolean enchanted) {
        return createStack(material, name, lore, command, "", enchanted, 1);
    }

    public static ItemStack createStack(Material material, String name, String command) {
        return createStack(material, name, Collections.emptyList(), command, "", false, 1);
    }

    public static ItemStack createStack(Material material, String name, List<String> lore, boolean enchanted) {
        return createStack(material, name, lore, "", "", enchanted, 1);
    }

    public static ItemStack createStack(Material material, String name, List<String> lore, String command) {
        return createStack(material, name, lore, command, "", false, 1);
    }
    public static ItemStack createStack(Material material, String name, List<String> lore, String command, int amount) {
        return createStack(material, name, lore, command, "", false, amount);
    }
    public static ItemStack createStack(Material material, String name, List<String> lore) {
        return createStack(material, name, lore, "", "", false, 1);
    }

    public static ItemStack getNothing() {
        ItemStack nothing = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta nothing_meta =  nothing.getItemMeta();
        assert nothing_meta != null;
        nothing_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', " "));
        nothing_meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&0&oInvasionBW")));
        nothing.setItemMeta(nothing_meta);
        return nothing;
    }

    //actual handlers

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (clicked != null) {
            if (clicked.getType() != Material.AIR) {
                if (clicked.hasItemMeta()) {
                    ItemMeta meta = clicked.getItemMeta();
                    if (meta != null) {
                        if (meta.hasLore()) {
                            List<String> lore = meta.getLore();
                            if (lore != null) {
                                if (lore.size() > 0) {

                                    if (ChatColor.stripColor(lore.get(lore.size() - 1)).equals("InvasionBW")) {
                                        event.setCancelled(true);
                                    }
                                    if (lore.size() > 1) {
                                        String commandraw = ChatColor.stripColor(lore.get(lore.size() - 2));
                                        HumanEntity player = event.getWhoClicked();
                                        Player playerP = Bukkit.getPlayer(player.getName());
                                        if (playerP != null) {
                                            playerP.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                                        }
                                        if (commandraw.length() > 0) {
                                            char command = commandraw.charAt(0);
                                            StringBuilder arg = new StringBuilder();
                                            IntStream.range(2, commandraw.length() - 1).forEachOrdered(n -> arg.append(commandraw.charAt(n)));
                                            //Bukkit.broadcastMessage("Command: " + command + " Action: " + arg);
                                            String args = arg.toString();
                                            if (event.getAction() != InventoryAction.PICKUP_HALF) {
                                                handleLeftClick(command, args, playerP, false, null);
                                            } else {
                                                if(lore.size() > 2) {
                                                    String commandrawRight = ChatColor.stripColor(lore.get(lore.size() - 3));
                                                    char commandRight = commandrawRight.charAt(0);
                                                    StringBuilder argRight = new StringBuilder();
                                                    IntStream.range(2, commandrawRight.length() - 1).forEachOrdered(n -> argRight.append(commandrawRight.charAt(n)));
                                                    //Bukkit.broadcastMessage("Command: " + command + " Action: " + arg);
                                                    String argsRight = argRight.toString();
                                                    //Bukkit.broadcastMessage("Command: " + commandRight + " Args: " + argsRight);
                                                    event.setCancelled(handleLeftClick(commandRight, argsRight, playerP, true, null));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        ItemStack clicked = event.getCursor();
        if (clicked != null) {
            if (clicked.getType() != Material.AIR) {
                if (clicked.hasItemMeta()) {
                    ItemMeta meta = clicked.getItemMeta();
                    if (meta != null) {
                        if (meta.hasLore()) {
                            List<String> lore = meta.getLore();
                            if (lore != null) {
                                if (lore.size() > 0) {

                                    if (ChatColor.stripColor(lore.get(lore.size() - 1)).equals("InvasionBW")) {
                                        event.setCancelled(true);
                                    }
                                    if (lore.size() > 1) {
                                        String commandraw = ChatColor.stripColor(lore.get(lore.size() - 2));
                                        HumanEntity player = event.getWhoClicked();
                                        Player playerP = Bukkit.getPlayer(player.getName());
                                        if (playerP != null) {
                                            playerP.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                                        }
                                        if (commandraw.length() > 0) {
                                            char command = commandraw.charAt(0);
                                            StringBuilder arg = new StringBuilder();
                                            IntStream.range(2, commandraw.length() - 1).forEachOrdered(n -> arg.append(commandraw.charAt(n)));
                                            //Bukkit.broadcastMessage("Command: " + command + " Action: " + arg);
                                            String args = arg.toString();
                                            event.setCancelled(handleLeftClick(command, args, playerP, false, null));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack clicked = event.getItem();
        Action action = event.getAction();
        if(okAct.contains(action)) {
            if (clicked != null) {
                if (clicked.getType() != Material.AIR) {
                    if (clicked.hasItemMeta()) {
                        ItemMeta meta = clicked.getItemMeta();
                        if (meta != null) {
                            if (meta.hasLore()) {
                                List<String> lore = meta.getLore();
                                if (lore != null) {
                                    if (lore.size() > 0) {

                                        if (ChatColor.stripColor(lore.get(lore.size() - 1)).equals("InvasionBW")) {
                                            event.setCancelled(true);
                                        }
                                        if (lore.size() > 1) {
                                            String commandraw = ChatColor.stripColor(lore.get(lore.size() - 2));
                                            HumanEntity player = event.getPlayer();
                                            Player playerP = Bukkit.getPlayer(player.getName());
                                            if (playerP != null) {
                                                playerP.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                                            }
                                            if (commandraw.length() > 0) {
                                                char command = commandraw.charAt(0);
                                                StringBuilder arg = new StringBuilder();
                                                IntStream.range(2, commandraw.length() - 1).forEachOrdered(n -> arg.append(commandraw.charAt(n)));
                                                //Bukkit.broadcastMessage("Command: " + command + " Action: " + arg);
                                                Block relative_block = null;
                                                String args = arg.toString();
                                                Block clicked_block = event.getClickedBlock();
                                                if(clicked_block != null && event.getBlockFace() != null) {
                                                    relative_block = clicked_block.getRelative(event.getBlockFace(), 1);
                                                } else {
                                                    relative_block = event.getClickedBlock();
                                                }
                                                event.setCancelled(handleLeftClick(command, args, playerP, false, relative_block));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack clicked = event.getItemDrop().getItemStack();
        if (clicked.getType() != Material.AIR) {
            if (clicked.hasItemMeta()) {
                ItemMeta meta = clicked.getItemMeta();
                if (meta != null) {
                    if (meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        if (lore != null) {
                            if (lore.size() > 0) {

                                if (ChatColor.stripColor(lore.get(lore.size() - 1)).equals("InvasionBW")) {
                                    event.setCancelled(true);
                                }
                                if (lore.size() > 1) {
                                    String commandraw = ChatColor.stripColor(lore.get(lore.size() - 2));
                                    HumanEntity player = event.getPlayer();
                                    Player playerP = Bukkit.getPlayer(player.getName());
                                    if (playerP != null) {
                                        playerP.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                                    }
                                    if (commandraw.length() > 0) {
                                        char command = commandraw.charAt(0);
                                        StringBuilder arg = new StringBuilder();
                                        IntStream.range(2, commandraw.length() - 1).forEachOrdered(n -> arg.append(commandraw.charAt(n)));
                                        //Bukkit.broadcastMessage("Command: " + command + " Action: " + arg);
                                        String args = arg.toString();
                                        event.setCancelled(handleLeftClick(command, args, playerP, false, null));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
