package me.frostdev.frostyspawners.util.items;


import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpawnEggLegacy implements SpawnEgg {
    private EntityType type;
    private String name;

    public SpawnEggLegacy(EntityType type) {
        this.type = type;
    }

    public SpawnEggLegacy(ItemStack item) {
        String typename = item.getItemMeta().getDisplayName().replaceAll("Spawn", "").replaceAll(" ", "_").toUpperCase();
        this.name = typename;
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
        return this.name;
    }

    public String toString() {
        return "SPAWN EGG{" + this.getSpawnedType() + "}";
    }

    public SpawnEggLegacy clone() {
        return this.clone();
    }

    public ItemStack toItemStack() {
        return this.toItemStack(1);
    }

    public ItemStack toItemStack(int amount) {
        ItemStack item = new ItemStack(Material.valueOf("MONSTER_EGG"), amount);
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = stack.getTag();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }

        NBTTagCompound id = new NBTTagCompound();
        id.setString("id", "minecraft:" + this.type.toString().toLowerCase());
        tagCompound.set("EntityTag", id);
        stack.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(stack);
    }

    public static SpawnEggLegacy fromItemStack(ItemStack item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        } else if (item.getType() != Material.valueOf("MONSTER_EGG")) {
            throw new IllegalArgumentException("item is not a monster egg");
        } else {
            net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagCompound = stack.getTag();
            if (tagCompound != null) {
                EntityType type = EntityType.fromName(tagCompound.getCompound("EntityTag").getString("id"));
                return type != null ? new SpawnEggLegacy(type) : null;
            } else {
                return null;
            }
        }
    }
}
