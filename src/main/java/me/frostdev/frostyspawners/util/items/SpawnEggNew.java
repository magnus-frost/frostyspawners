package me.frostdev.frostyspawners.util.items;

import me.frostdev.frostyspawners.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpawnEggNew implements SpawnEgg {
    private EntityType type;

    public SpawnEggNew(EntityType type) {
        this.type = type;
    }

    public SpawnEggNew(ItemStack item) {
        String typename = item.getItemMeta().getDisplayName().replaceAll("Spawn", "").replaceAll(" ", "_").toUpperCase();
        EntityType type = EntityType.valueOf(ChatColor.stripColor(typename));
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
        return "SPAWN EGG{" + this.getSpawnedType() + "}";
    }

    public SpawnEggNew clone() {
        return this.clone();
    }

    public ItemStack toItemStack() {
        return this.toItemStack(1);
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(Util.getEggMaterial(this.type), amount);
    }

    public static SpawnEggNew fromItemStack(ItemStack item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        } else if (item.getType() != Material.LEGACY_MONSTER_EGG) {
            throw new IllegalArgumentException("item is not a monster egg");
        } else {
            net.minecraft.server.v1_14_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagCompound = stack.getTag();
            if (tagCompound != null) {
                EntityType type = EntityType.fromName(tagCompound.getCompound("EntityTag").getString("id"));
                return type != null ? new SpawnEggNew(type) : null;
            } else {
                return null;
            }
        }
    }
}
