package me.qrashi.plugins.bedwars.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class StackCreator {
    public static ItemStack createStack(Material material, String name, List<String> lore, String command, boolean enchanted) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta stack_meta = stack.getItemMeta();
        assert stack_meta != null;
        stack_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> newlore = new java.util.ArrayList<>(Collections.emptyList());
        for(String i: lore){
            newlore.add(ChatColor.translateAlternateColorCodes('&', i));
        }
        newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&o" + command));
        newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&oInvasionBW"));
        stack_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
        stack_meta.setLore(newlore);
        stack.setItemMeta(stack_meta);
        if(enchanted){
            stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        return stack;
    }
    public static ItemStack createStack(Material material, String name, List<String> lore, String command) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta stack_meta = stack.getItemMeta();
        assert stack_meta != null;
        stack_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> newlore = new java.util.ArrayList<>(Collections.emptyList());
        for(String i: lore){
            newlore.add(ChatColor.translateAlternateColorCodes('&', i));
        }
        newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&o" + command));
        newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&oInvasionBW"));
        stack_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
        stack_meta.setLore(newlore);
        stack.setItemMeta(stack_meta);
        return stack;
    }
    public static ItemStack createStack(Material material, String name, List<String> lore) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta stack_meta = stack.getItemMeta();
        assert stack_meta != null;
        stack_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> newlore = new java.util.ArrayList<>(Collections.emptyList());
        for(String i: lore){
            newlore.add(ChatColor.translateAlternateColorCodes('&', i));
        }
        newlore.add(ChatColor.translateAlternateColorCodes('&', "&0&oInvasionBW"));
        stack_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
        stack_meta.setLore(newlore);
        stack.setItemMeta(stack_meta);
        return stack;
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
}
