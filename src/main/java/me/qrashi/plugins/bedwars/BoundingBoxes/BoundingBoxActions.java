package me.qrashi.plugins.bedwars.BoundingBoxes;

import me.qrashi.plugins.bedwars.BedWars;
import me.qrashi.plugins.bedwars.Utils.MessageCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class BoundingBoxActions {
    public static void replace(List<Material> toReplace, BoundingBox box) {
        long start = System.nanoTime();
        double x1 = box.getX1();
        double x2 = box.getX2();
        double y1 = box.getY1();
        double y2 = box.getY2();
        double z1 = box.getZ1();
        double z2 = box.getZ2();
        double vol = (Math.abs((x1 - x2)) + 1)  * (Math.abs(y1 - y2) + 1) * (Math.abs(z1 - z2) + 1);
        //Bukkit.broadcastMessage("x diff is " + Math.abs((x1 - x2)) + " y diff is " + Math.abs(y1 - y2) + " z diff is " + Math.abs(z1 - z2));
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] Executing task &aCLEANUP&7, checking " + vol + " blocks."));
        int repl = 0;
        double times = 0;
        for(double i=x1; i<=x2; i++) {
            for(double f=y1; f<=y2; f++) {
                for(double g=z1; g<=z2; g++) {
                    Location blockLoc = new Location(BedWars.getWorld(), i, f, g);
                    Block block = BedWars.getWorld().getBlockAt(blockLoc);
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
        if(times != vol) {
            Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cERROR: &7Checked blocks &7(" + times + ") &cdo not align with calculated &7(" + vol + ") &cblocks!"));
        }
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &7Checked " + times + " blocks; Replaced " + repl + " blocks; Time: " + duration + "ms."));
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &aFinished task CLEANUP."));
    }
    public static int checkEmpty(BoundingBox box) {
        long start = System.nanoTime();
        double x1 = box.getX1();
        double x2 = box.getX2();
        double y1 = box.getY1();
        double y2 = box.getY2();
        double z1 = box.getZ1();
        double z2 = box.getZ2();
        double vol = (Math.abs((x1 - x2)) + 1)  * (Math.abs(y1 - y2) + 1) * (Math.abs(z1 - z2) + 1);
        //Bukkit.broadcastMessage("x diff is " + Math.abs((x1 - x2)) + " y diff is " + Math.abs(y1 - y2) + " z diff is " + Math.abs(z1 - z2));
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] Executing task &aCHECK_EMPTY&7, checking " + vol + " blocks."));
        double times = 0;
        int notAir = 0;
        for(double i=x1; i<=x2; i++) {
            for(double f=y1; f<=y2; f++) {
                for(double g=z1; g<=z2; g++) {
                    Location blockLoc = new Location(BedWars.getWorld(), i, f, g);
                    Block block = BedWars.getWorld().getBlockAt(blockLoc);
                    //Bukkit.broadcastMessage("x: " + i + " y: " + f + " z: " + g);
                    times++;
                    if (block.getType() != Material.AIR) {
                        notAir++;
                    }
                }

            }
        }
        long finish = System.nanoTime();
        long duration = (finish - start) / 1000000;
        if(times != vol) {
            Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &cERROR: &7Checked blocks &7(" + times + ") &cdo not align with calculated &7(" + vol + ") &cblocks!"));
        }
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &7Checked " + times + " blocks; Found " + notAir + " not air blocks; Time: " + duration + "ms."));
        Bukkit.broadcastMessage(MessageCreator.t("&7[&cBedWars&7] &aFinished task CHECK_EMPTY."));
        return notAir;
    }
}
