package me.qrashi.plugins.bedwars.Maps;

import me.qrashi.plugins.bedwars.Maps.Shops.Shop;
import me.qrashi.plugins.bedwars.Maps.Spawners.Spawner;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import me.qrashi.plugins.bedwars.Objects.SerializableLocation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class LocationSaver implements Serializable {

    private List<Spawner> spawners = Collections.emptyList();
    private List<Shop> shops = Collections.emptyList();
    private SerializableLocation specspawn;
    private SerializableLocation lobbyspawn;

    public LocationSaver(SerializableLocation startloc) {
        specspawn = startloc;
        lobbyspawn = startloc;
    }


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

    public SerializableLocation getLobbyspawn() {
        return lobbyspawn;
    }

    public void setLobbyspawn(SerializableLocation lobbyspawn) {
        this.lobbyspawn = lobbyspawn;
    }

    public Spawner getSpawnerAt(SerializableLocation toSearch) {
        for(Spawner spawner : spawners) {
            if(spawner.getLoc().equals(toSearch)) {
                return spawner;
            }
        }
    return null;
    }

    public SerializableLocation getSpecspawn() {
        return specspawn;
    }

    public void setSpecspawn(SerializableLocation specspawn) {
        this.specspawn = specspawn;
    }
}
