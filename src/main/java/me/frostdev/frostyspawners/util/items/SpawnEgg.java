package me.frostdev.frostyspawners.util.items;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public interface SpawnEgg {
    EntityType getSpawnedType();

    void setSpawnedType(EntityType var1);

    String getEntityName();

    String toString();

    SpawnEgg clone();

    ItemStack toItemStack();

    ItemStack toItemStack(int var1);

}
