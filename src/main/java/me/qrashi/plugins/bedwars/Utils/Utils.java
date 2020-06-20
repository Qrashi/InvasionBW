package me.qrashi.plugins.bedwars.Utils;

import me.qrashi.plugins.bedwars.Maps.Spawners.Spawner;
import me.qrashi.plugins.bedwars.Maps.Spawners.SpawnerType;
import me.qrashi.plugins.bedwars.Maps.Teams.Team;
import me.qrashi.plugins.bedwars.Maps.Teams.TeamColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public final class Utils {
    private final List<Material> materialList;
    public Utils() {
        materialList = Arrays.asList(Material.SMOOTH_SANDSTONE, Material.COBWEB, Material.CHEST, Material.ENDER_CHEST, Material.TNT, Material.END_STONE, Material.IRON_BLOCK, Material.STONE);
    }
    public List<Material> getMaterialList() {
        return materialList;
    }

    public String getTeamName(Team team) {
        switch (team.getCol()) {
            case RED:
                return MessageCreator.t("&cTeam Red");
            case BLUE:
                return MessageCreator.t("&9Team Blue");
            case GREEN:
                return MessageCreator.t("&aTeam Green");
            case YELLOW:
                return MessageCreator.t("&eTeam Yellow");
        }
    return "";
    }

    public static TeamColor numToCol(int num) {
        switch (num) {
            case 0:
                return TeamColor.BLUE;
            case 1:
                return TeamColor.RED;
            case 2:
                return TeamColor.GREEN;
            case 3:
                return TeamColor.YELLOW;
        }
    return TeamColor.BLUE;
    }
    public static ItemStack getItemFromType(SpawnerType type) {
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
}
