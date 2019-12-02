package me.frostdev.frostyspawners.util.items;


import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.Permission;


public class TypeMenuEggItem {
    private Frostyspawners main;
    private SpawnEgg egg;
    private EntityType type;
    private Material material;
    private String name;
    private Permission permission;
    private double cost;
    private Double lvlreq;
    private Boolean isenabled;

    public TypeMenuEggItem(EntityType t, Material m, String n) {
        this.main = Frostyspawners.INSTANCE;
        this.type = t;
        this.material = m;
        this.name = n;
        this.permission = Util.toPermission(t);
        this.egg = this.main.items.spawn_egg(this.type);
        this.cost = !Frostyspawners.PLUGIN.getConfig().isSet("types." + type + ".cost") ? 0.0D : Frostyspawners.PLUGIN.getConfig().getDouble("types." + type + ".cost");
        this.lvlreq = !Frostyspawners.PLUGIN.getConfig().isSet("types." + type + ".levelreq") ? 0.0D : Frostyspawners.PLUGIN.getConfig().getInt("types." + type + ".levelreq");
        this.isenabled = !Frostyspawners.PLUGIN.getConfig().isBoolean("types." + type + ".enabled") ? true : Frostyspawners.PLUGIN.getConfig().getBoolean("types." + type + ".enabled");
    }



    public EntityType getEntityType() {
        return this.type;
    }

    public String getType() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getDisplayName() {
        return (String)Util.getUserFriendlyNames().get(this.egg.getSpawnedType());
    }

    public Permission getPermission() {
        return this.permission;
    }
    public Double getCost() {
        return this.cost;
    }
    public Boolean getIsenabled() {return this.isenabled;}
    public Integer getlvlreq () {
        return this.lvlreq.intValue();
    }
}
