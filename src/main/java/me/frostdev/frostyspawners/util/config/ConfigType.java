package me.frostdev.frostyspawners.util.config;


import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

public class ConfigType {
    private String type;
    public Double getTypeCost(EntityType t) {
        type = t.toString().toLowerCase();
            return !Frostyspawners.PLUGIN.getConfig().isSet("types." + type + ".cost") ? 0.0D : Frostyspawners.PLUGIN.getConfig().getDouble("types." + type + ".cost");
        }
    public int getLevelReq(EntityType t) {
        type = t.toString().toLowerCase();
        return !Frostyspawners.PLUGIN.getConfig().isSet("types." + type + ".levelreq") ? 0 : Frostyspawners.PLUGIN.getConfig().getInt("types." + type + ".levelreq");
    }
    public boolean getenabled(EntityType t) {
        type = t.toString().toLowerCase();
        return Frostyspawners.PLUGIN.getConfig().isSet("types." + type + ".enabled") || Frostyspawners.PLUGIN.getConfig().getBoolean("types." + type + ".enabled");
    }
    public String gettype(EntityType t) {
        type = t.toString().toLowerCase();
        return type;
    }
}
