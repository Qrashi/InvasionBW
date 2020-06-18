package me.qrashi.plugins.bedwars.BoundingBoxes;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public final class BoundingBoxActions {
    public void replace(List<Material> toReplace, BoundingBox box) {
        long start = System.nanoTime();
        int x1 = box.getX1();
        int x2 = box.getX2();
        int y1 = box.getY1();
        int y2 = box.getY2();
        int z1 = box.getZ1();
        int z2 = box.getZ2();
        int vol = (Math.abs((x1 - x2)) + 1)  * (Math.abs(y1 - y2) + 1) * (Math.abs(z1 - z2) + 1);
        //Bukkit.broadcastMessage("x diff is " + Math.abs((x1 - x2)) + " y diff is " + Math.abs(y1 - y2) + " z diff is " + Math.abs(z1 - z2));
        Bukkit.broadcastMessage(MessageCreator.t("&7Checking " + vol + " blocks."));
        int repl = 0;
        int times = 0;
        for(int i=x1; i<=x2; i++) {
            for(int f=y1; f<=y2; f++) {
                for(int g=z1; g<=z2; g++) {
                    Block block = BedWars.getWorld().getBlockAt(i, f, g);
                    //Bukkit.broadcastMessage("x: " + i + " y: " + f + " z: " + g);
                    times++;
                    if (toReplace.contains(block.getType())) {
                        block.setType(Material.AIR);
                        repl++;
                    }
                }

            }
        }
        long finish = System.nanoTime();
        long duration = (finish - start) / 1000000;
        Bukkit.broadcastMessage(MessageCreator.t("&7Checked " + times + " blocks; Replaced " + repl + " blocks; Time: " + duration + "ms."));
    }
}
