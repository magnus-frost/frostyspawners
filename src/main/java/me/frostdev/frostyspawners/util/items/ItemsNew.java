package me.frostdev.frostyspawners.util.items;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsNew implements Items {
    public ItemsNew() {
    }

    public ItemStack spawner(int i) {
        return new ItemStack(Material.matchMaterial("SPAWNER"), i);
    }

    public ItemStack exp_bottle(int i) {
        return new ItemStack(Material.matchMaterial("EXPERIENCE_BOTTLE"), i);
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

    public ItemStack wood_pickaxe(int i) {
        return new ItemStack(Material.matchMaterial("WOODEN_PICKAXE"), i);
    }

    public ItemStack gold_pickaxe(int i) {
        return new ItemStack(Material.matchMaterial("GOLDEN_PICKAXE"), i);
    }

    public ItemStack stained_glass_pane(int i, DyeColor c) {
        return new ItemStack(Material.matchMaterial(c.name() + "_STAINED_GLASS_PANE"), i);
    }
    public ItemStack bedrock(int i) {
        return new ItemStack(Material.matchMaterial("BEDROCK"), i);
    }
    public ItemStack helmet(int i) {
        ItemStack heml = new ItemStack(Material.IRON_HELMET);
        ItemMeta helmeta = heml.getItemMeta();
        helmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        heml.setItemMeta(helmeta);
        return heml;
    }

    public ItemStack wool(int i, DyeColor c) {
        return new ItemStack(Material.matchMaterial(c.name() + "_WOOL"), i);
    }

    public SpawnEggNew spawn_egg(EntityType t) {
        return new SpawnEggNew(t);
    }
}
