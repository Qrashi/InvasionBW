package me.qrashi.plugins.bedwars.Maps;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Maps.Shops.Shop;
import me.qrashi.plugins.bedwars.Maps.Spawners.Spawner;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class LocationSaver {

    private List<Spawner> spawners = Collections.emptyList();
    private List<Shop> shops = Collections.emptyList();
    private Location specspawn = new Location(BedWars.getWorld(), 0, 0, 0);
    private Location lobbyspawn = new Location(BedWars.getWorld(), 0, 0, 0);


    public boolean hasSpawners()  {
        return !spawners.isEmpty();
    }
    public void addSpawner(SpawnerType type, int x, int y, int z) {
        spawners.add(new Spawner(type, x, y, z));

    }

    public List<Shop> getShops() {
        return shops;
    }

    public Shop getShop(int i) {
        return shops.get(i);
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }
    public void spawnAll() {
        for(Spawner spawner : spawners) {
            spawner.spawn();
        }
    }
    public Spawner getSpawner(int i) {
        return spawners.get(i);
    }

    public Location getLobbyspawn() {
        return lobbyspawn;
    }

    public void setLobbyspawn(Location lobbyspawn) {
        this.lobbyspawn = lobbyspawn;
    }

    public Spawner getSpawnerAt(int x, int y, int z) {
        for(Spawner spawner : spawners) {
            if(spawner.getX() == x) {
                if(spawner.getY() == y) {
                    if(spawner.getZ() == z) {
                        return spawner;
                    }
                }
            }
        }
    return null;
    }

    public Location getSpecspawn() {
        return specspawn;
    }

    public void setSpecspawn(Location specspawn) {
        this.specspawn = specspawn;
    }
}
