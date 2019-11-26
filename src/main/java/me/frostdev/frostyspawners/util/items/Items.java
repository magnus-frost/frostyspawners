package me.frostdev.frostyspawners.util.items;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public interface Items {
    ItemStack spawner(int var1);

    ItemStack exp_bottle(int var1);

    ItemStack barrier(int var1);

    ItemStack blaze_rod(int var1);

    ItemStack stick(int var1);

    ItemStack wood_pickaxe(int var1);

    ItemStack gold_pickaxe(int var1);

    ItemStack stained_glass_pane(int var1, DyeColor var2);

    ItemStack wool(int var1, DyeColor var2);

    SpawnEgg spawn_egg(EntityType var1);

    ItemStack bedrock(int var1);
}
