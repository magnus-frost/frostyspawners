package me.frostdev.frostyspawners.util.items;

import me.frostdev.frostyspawners.util.Util;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpawnEggCustom implements SpawnEgg {
    private EntityType type;

    public SpawnEggCustom(EntityType type) {
        this.type = type;
    }

    public SpawnEggCustom(ItemStack item) {
        String typename = "IRON_GOLEM";
        EntityType type = EntityType.IRON_GOLEM;
        this.type = type;
    }

    public EntityType getSpawnedType() {
        return this.type;
    }

    public void setSpawnedType(EntityType type) {
        if (type.isAlive()) {
            this.type = type;
        }
    }
    public String getEntityName() {
        return Util.getEggName(this.type);
    }

    public String toString() {
        return "IRON_GOLEM";
    }

    public SpawnEggNew clone() {
        return this.clone();
    }

    public ItemStack toItemStack() {
        return this.toItemStack(1);
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(Material.IRON_HELMET);
    }
    public static SpawnEggCustom fromItemStack(ItemStack item) {
                EntityType type = EntityType.IRON_GOLEM;
                return type != null ? new SpawnEggCustom(type) : null;
        }
    }

