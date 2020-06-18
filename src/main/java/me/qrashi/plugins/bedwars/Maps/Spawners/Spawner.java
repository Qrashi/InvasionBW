package me.qrashi.plugins.bedwars.Maps.Spawners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Utils.StackCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Spawner {
    private SpawnerType type;
    private int x;
    private int y;
    private int z;
    public Spawner(SpawnerType type, int x, int y, int z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void spawn() {
        Location spawnloc = new Location(BedWars.getWorld(), x, y, z);
        BedWars.getWorld().dropItem(spawnloc, getItemFromType());
    }
    private ItemStack getItemFromType() {
        switch (type) {
            case GOLD:
                return StackCreator.createStack(Material.GOLD_INGOT, "&6Gold", Arrays.asList("&7The most &6valuable &7item", "&7in the game.", "&7Can be found in the middle of", "&7the map"));
            case IRON:
                return StackCreator.createStack(Material.IRON_INGOT, "&7Iron", Arrays.asList("&7Used to buy a lot of armor", "&7in the game."));
            case BRONZE:
                return StackCreator.createStack(Material.BRICK, "&cBronze", Arrays.asList("&7Used for almost everything", "&7in the game."));
        }
    return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public SpawnerType getType() {
        return type;
    }

    public void setType(SpawnerType type) {
        this.type = type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
