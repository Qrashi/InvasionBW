package me.qrashi.plugins.bedwars.Maps.Teams;

import me.qrashi.plugins.bedwars.BedWars;
import org.bukkit.Material;

public class Bed {

    private int x;
    private int z;
    private int y;
    private Team team;
    private boolean destroyed;

    public Bed(int x, int y, int z, Team correspondingTeam) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.team = correspondingTeam;
    }

    public void destroy() {
        destroyed = true;
        BedWars.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void revive() {
        destroyed = false;
        BedWars.getWorld().getBlockAt(x, y, z).setType(Material.RED_BED);
    }

    public void reset() {
        destroyed = false;
    }

    public Team getTeam() {
        return team;
    }

}
