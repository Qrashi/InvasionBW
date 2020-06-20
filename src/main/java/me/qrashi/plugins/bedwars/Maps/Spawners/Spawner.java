package me.qrashi.plugins.bedwars.Maps.Spawners;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;
import me.qrashi.plugins.bedwars.Utils.StackCreator;
import me.qrashi.plugins.bedwars.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Spawner implements Serializable {
    private SpawnerType type;
    private SerializableLocation loc;
    public Spawner(SpawnerType type, SerializableLocation loc) {
        this.type = type;
        this.loc = loc;
    }

    public Spawner(SpawnerType type, int x, int y, int z) {
        this.type = type;
        loc = new SerializableLocation(x, y, z);
    }

    public Spawner() {}

    public void spawn() {
        BedWars.getWorld().dropItem(loc.getLocation(), Objects.requireNonNull(Utils.getItemFromType(type)));
    }

    public SerializableLocation getLoc() {return loc;}
    public SpawnerType getType() {
        return type;
    }

    public void setType(SpawnerType type) {
        this.type = type;
    }

    public void setLoc(SerializableLocation loc) {this.loc = loc;}
}
