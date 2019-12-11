package me.frostdev.frostyspawners.util;

import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.config.ConfigType;
import me.frostdev.frostyspawners.util.items.TypeMenuEggItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import java.text.Collator;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class Util {
    private static Map<String, EntityType> mobTypes;
    private static Map<String, EntityType> simpleMobTypes;
    private static Map<EntityType, String> userFriendlyMobTypes;
    private static Map<EntityType, TypeMenuEggItem> eggs;
    private static final String SERVER_VERSION;

    static {
        String name = Bukkit.getServer().getClass().getName();
        name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
        name = name.substring(0, name.indexOf("."));
        SERVER_VERSION = name;
    }

    private boolean isenabled(EntityType type){
        ConfigType yn = new ConfigType();
        return yn.getenabled(type);
    }

    public Util() {
        mobTypes = new LinkedHashMap();
        simpleMobTypes = new LinkedHashMap();
        userFriendlyMobTypes = new LinkedHashMap();
        Collection<String> types = new TreeSet(Collator.getInstance());
        EntityType[] var5;
        int var4 = (var5 = EntityType.values()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            EntityType type = var5[var3];
            if (isenabled(type) && type.isAlive() && type != EntityType.WITHER && type != EntityType.ENDER_DRAGON && type != EntityType.ARMOR_STAND) {
                types.add(type.name());
            }
        }
        types.add("IRON_GOLEM");

        for (String name : types) {
            EntityType type = EntityType.valueOf(name);
            mobTypes.put(name, type);
            simpleMobTypes.put(name.replace("_", ""), type);
            Logger.info("Loaded " + type.name() + " : " + name.replace("_", ""));
            String[] s1 = type.name().split("_");
            String uname = "";

            for (int i = 0; i < s1.length; ++i) {
                String s = s1[i];
                String word = s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1);
                if (i == s1.length - 1) {
                    uname = uname + word;
                } else {
                    uname = uname + word + " ";
                }
            }

            userFriendlyMobTypes.put(type, uname);
        }

        eggs = loadEggs();
    }

    public static Map<EntityType, TypeMenuEggItem> getEggs() {
        return eggs;
    }

    public static TypeMenuEggItem getEgg(EntityType type) {
        return (TypeMenuEggItem)eggs.get(type);
    }

    public static Material getEggMaterial(EntityType type) {
        if(type.equals(EntityType.IRON_GOLEM)){
            return Material.IRON_HELMET;
        }else
        return ((TypeMenuEggItem)eggs.get(type)).getMaterial();
    }

    public static String getEggName(EntityType type) {
        if(type.equals(EntityType.IRON_GOLEM)){
            return (String) "Iron Golem";
        }
        return ((TypeMenuEggItem)eggs.get(type)).getDisplayName();
    }

    public static Map<String, EntityType> getEntityTypes() {
        return mobTypes;
    }

    public static Map<EntityType, String> getUserFriendlyNames() {
        return userFriendlyMobTypes;
    }

    public static String toUserFriendlyString(EntityType type) {
        if(type.equals(EntityType.IRON_GOLEM)){
            return (String)"IRON_GOLEM";
        }
        return (String)userFriendlyMobTypes.get(type);
    }

    public static String toString(EntityType type) {
        if(type.equals(EntityType.IRON_GOLEM)){
            return (String)"IRON_GOLEM";
        }
        return (String)userFriendlyMobTypes.get(type);
    }

    public static Permission toPermission(EntityType type) {
        return new Permission("frostyspawners.menu.type." + type.name().toLowerCase());
    }

    public static EntityType fromString(String type) {
        if(type.equals("IRON_GOLEM")){
            return EntityType.IRON_GOLEM;
        }
        return (EntityType)simpleMobTypes.get(type.toUpperCase());
    }

    public static boolean isSpawnEgg(ItemStack item) {
        if(item.getType().equals(Material.IRON_HELMET)){
            return true;
        }
        return item.getType().name().contains("SPAWN_EGG");
    }

    public static String serializeLocationToId(Location location) {
        return location.getWorld().getName() + "@" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }

    public static Location deserializeId(String id) {
        String[] s1 = id.split("@");
        String world = s1[0];
        String[] coords = s1[1].split(";");
        int x = Integer.valueOf(coords[0]);
        int y = Integer.valueOf(coords[1]);
        int z = Integer.valueOf(coords[2]);
        if (Bukkit.getServer().getWorld(world) == null) {
            throw new NullPointerException("World '" + world + "' does not exist (anymore).");
        } else {
            return new Location(Bukkit.getServer().getWorld(world), (double)x, (double)y, (double)z);
        }
    }

    public static boolean isLegacyVersion() {
        return !isVersionHigherThan(1, 12);
    }

    public static boolean isVersionHigherThan(int mainVersion, int secondVersion) {
        String firstChar = SERVER_VERSION.substring(1, 2);
        int fInt = Integer.parseInt(firstChar);
        if (fInt < mainVersion) {
            return false;
        } else {
            StringBuilder secondChar = new StringBuilder();

            int sInt;
            for(sInt = 3; sInt < 10 && SERVER_VERSION.charAt(sInt) != '_' && SERVER_VERSION.charAt(sInt) != '.'; ++sInt) {
                secondChar.append(SERVER_VERSION.charAt(sInt));
            }

            sInt = Integer.parseInt(secondChar.toString());
            return sInt > secondVersion;
        }
    }

    private static Map<EntityType, TypeMenuEggItem> loadEggs() {
        Map<EntityType, TypeMenuEggItem> eggs = new LinkedHashMap();
        Material[] var4;
        int var3 = (var4 = Material.values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            Material mat = var4[var2];
            if(mat.name().contains("IRON")){
                Material igolem = Material.IRON_HELMET;
                eggs.put(EntityType.IRON_GOLEM, new TypeMenuEggItem(EntityType.IRON_GOLEM, igolem, "Iron Golem"));
            }
            if (mat.name().contains("SPAWN_EGG")) {
                String typeName = mat.name().replace("_SPAWN_EGG", "");
                if (typeName.equals("MOOSHROOM")) {
                    typeName = "MUSHROOM_COW";
                } else if (typeName.equals("ZOMBIE_PIGMAN")) {
                    typeName = "PIG_ZOMBIE";
                } else if(typeName.equals("IRON_GOLEM")) {
                    typeName = "IRON_GOLEM";
                    mat = Material.IRON_HELMET;
                }

                EntityType type = EntityType.valueOf(typeName);
                eggs.put(type, new TypeMenuEggItem(type, mat, typeName));
            }
        }

        return eggs;
    }

    public static void inspectSpawner(Player player, Spawner spawner) {
    }
}
