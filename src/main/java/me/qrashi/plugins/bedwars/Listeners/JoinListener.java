package me.qrashi.plugins.bedwars.Listeners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Game.PlayType;
import me.qrashi.plugins.bedwars.Game.WorldBorderManager;
import me.qrashi.plugins.bedwars.Inventories.ItemStacks;
import me.qrashi.plugins.bedwars.Inventories.MainShop;
import me.qrashi.plugins.bedwars.Inventories.InvOpener;
import me.qrashi.plugins.bedwars.Inventories.Setup.SetupManager;
import me.qrashi.plugins.bedwars.Locations.Locations;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinListener implements Listener {

    private boolean firstJoin = true;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(MessageCreator.t("&7[&2+&7] " + player.getName()));
        TextComponent clickme = new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7[&cBedWars&7] &7Please report any issues on GitHub &a[Click me]"));
        clickme.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Fritz-CO-KG/InvasionBW/issues/new"));
        clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Click to open the a new issue on GitHub").color(net.md_5.bungee.api.ChatColor.GREEN).create()));
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().sendMessage(clickme);
            }
        }.runTaskLater(BedWars.getInstance(), 1);
        if (firstJoin) {
            BedWars.setWorld(player.getLocation().getWorld());
            firstJoin = false;
        }

        //not real
        //BedWars.getMapManager().getMap(0).clear();
        InvOpener.openDelay(event.getPlayer(), MainShop.shop());

        //real listener
        Inventory playerInv = player.getInventory();
        if(!SetupManager.getModeLocked()) {
            playerInv.clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(Locations.spawn().getLocationYP());
            player.setHealth(20);
            player.setFoodLevel(20);
            if (player.isOp()) {
                playerInv.setItem(4, ItemStacks.getJoinItem());
            }
        }
        WorldBorderManager.forceUpdate();
        //"rejoin" listeners
        if(SetupManager.getModeLocked()) {
            switch (BedWars.getGameManager().getPlayType()) {
                case LOBBY:
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendTitle(MessageCreator.t("&6Editing the lobby"), MessageCreator.t("&aHave fun!"), 10, 150, 20);
                    playerInv.clear();
                    break;
                case BUILDING:
                    player.setGameMode(GameMode.CREATIVE);
                    SerializableLocation loc = BedWars.getGameManager().getMap().getLocations().getLobbyspawn().getCopy();
                    player.teleport(loc.setY(loc.getY() + 1).getTpLocation());
                    player.getInventory().clear();
                    MessageCreator.sendTitle(player, "&aBuildMode", "&7Building " + BedWars.getGameManager().getMap().getName(), 100);
                    player.setFlying(true);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 0.1F);
                    break;
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(MessageCreator.t("&7[&c-&7] " + event.getPlayer().getName()));
    }
}
