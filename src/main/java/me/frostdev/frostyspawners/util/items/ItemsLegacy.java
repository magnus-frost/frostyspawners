package me.frostdev.frostyspawners.util.items;


import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemsLegacy implements Items {
    public ItemsLegacy() {
    }

    public ItemStack spawner(int i) {
        return new ItemStack(Material.matchMaterial("MOB_SPAWNER"), i);
    }

    public ItemStack exp_bottle(int i) {
        return new ItemStack(Material.matchMaterial("EXP_BOTTLE"), i);
    }

    public ItemStack barrier(int i) {
        return new ItemStack(Material.matchMaterial("BARRIER"), i);
    }

    public ItemStack blaze_rod(int i) {
        return new ItemStack(Material.matchMaterial("BLAZE_ROD"), i);
    }

    public ItemStack stick(int i) {
        return new ItemStack(Material.matchMaterial("STICK"), i);
    }
    public ItemStack bedrock(int i) {
        return new ItemStack(Material.matchMaterial("BEDROCK"), i);
    }
    public ItemStack helmet(int i) {return new ItemStack(Material.IRON_HELMET);}

    public ItemStack wood_pickaxe(int i) {
        return new ItemStack(Material.matchMaterial("WOOD_PICKAXE"), i);
    }

    public ItemStack gold_pickaxe(int i) {
        return new ItemStack(Material.matchMaterial("GOLD_PICKAXE"), i);
    }

    public ItemStack stained_glass_pane(int i, DyeColor c) {
        ItemStack item = new ItemStack(Material.matchMaterial("STAINED_GLASS_PANE"), i);
        item.setDurability(c.getWoolData());
        return item;
    }

    public ItemStack wool(int i, DyeColor c) {
        ItemStack item = new ItemStack(Material.matchMaterial("WOOL"), i);
        item.setDurability(c.getWoolData());
        return item;
    }

    public SpawnEggLegacy spawn_egg(EntityType t) {
        return new SpawnEggLegacy(t);
    }
}
