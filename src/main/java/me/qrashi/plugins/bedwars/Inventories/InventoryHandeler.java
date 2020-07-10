package me.qrashi.plugins.bedwars.Inventories;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Commands.EndCommand;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Inventories.Setup.MapChooser;
import me.qrashi.plugins.bedwars.Inventories.Setup.SearchManager;
import me.qrashi.plugins.bedwars.Inventories.Setup.SetupManager;
import me.qrashi.plugins.bedwars.Players.PlayerData;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

import java.util.ArrayList;
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
    */

    private void handleLeftClick(char command, String arguments, Player player, boolean isRightClick) {
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
                                EndInventory.endGame();
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
    }

    /*
    Information:
    Please enter your command definitions here:

    z: Special operations
    s: Spectate map

    */

    private void handleRightClick(char command, String arguments, Player player) {
        switch (command) {
            case 'z':
                switch (arguments) {
                    case "clearsearch":
                        InvOpener.openDelay(player, MapChooser.smartInv(player));
                        break;
                    case "cancelend":
                        Bukkit.broadcastMessage(MessageCreator.t("&cThe game end token was forced invalid."));
                        EndInventory.setConfirmed(false);
                        InvOpener.closeDelay(player);
                        break;
                }
                break;
            case 's':
                //Spectate map arguments
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
                                                handleLeftClick(command, args, playerP, false);
                                            } else {
                                                if(lore.size() > 2) {
                                                    String commandrawRight = ChatColor.stripColor(lore.get(lore.size() - 3));
                                                    char commandRight = commandrawRight.charAt(0);
                                                    StringBuilder argRight = new StringBuilder();
                                                    IntStream.range(2, commandrawRight.length() - 1).forEachOrdered(n -> argRight.append(commandrawRight.charAt(n)));
                                                    //Bukkit.broadcastMessage("Command: " + command + " Action: " + arg);
                                                    String argsRight = argRight.toString();
                                                    //Bukkit.broadcastMessage("Command: " + commandRight + " Args: " + argsRight);
                                                    handleLeftClick(commandRight, argsRight, playerP, true);
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
                                            handleLeftClick(command, args, playerP, false);
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
                                                String args = arg.toString();
                                                handleLeftClick(command, args, playerP, false);
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
                                        handleLeftClick(command, args, playerP, false);
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
