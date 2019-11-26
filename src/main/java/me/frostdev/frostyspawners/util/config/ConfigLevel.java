package me.frostdev.frostyspawners.util.config;


import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.Effect;
import org.bukkit.Particle;

public class ConfigLevel {
    int level;

    public ConfigLevel(int l) {
        this.level = l;
    }

    public int getLevel() {
        return this.level;
    }

    public double getCost() {
        return !Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level + ".cost") ? 0.0D : Frostyspawners.PLUGIN.getConfig().getDouble("levels." + this.level + ".cost");
    }
    public double getTypeCost() {
        return !Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level + ".cost") ? 0.0D : Frostyspawners.PLUGIN.getConfig().getDouble("levels." + this.level + ".cost");
    }


    public int getDelay() {
        return !Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level + ".delay") ? 500 : Frostyspawners.PLUGIN.getConfig().getInt("levels." + this.level + ".delay");
    }

    public int getSpawnCount() {
        return !Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level + ".spawnCount") ? 4 : Frostyspawners.PLUGIN.getConfig().getInt("levels." + this.level + ".spawnCount");
    }

    public String getEffectType() {
        return !Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level + ".effectType") ? "none" : Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effectType");
    }

    public Object getEffect() {
        if (!Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level + ".effect")) {
            return null;
        } else if (this.getEffectType().equals("effect")) {
            try {
                Effect.valueOf(Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effect"));
            } catch (IllegalArgumentException var2) {
                return null;
            }

            return Effect.valueOf(Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effect"));
        } else if (this.getEffectType().equals("particle")) {
            try {
                Particle.valueOf(Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effect"));
            } catch (IllegalArgumentException var3) {
                return null;
            }

            return Particle.valueOf(Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effect"));
        } else {
            return null;
        }
    }

    public int isValid() {
        if (Frostyspawners.PLUGIN.getConfig().get("levels." + this.level) != null && Frostyspawners.PLUGIN.getConfig().isSet("levels." + this.level)) {
            try {
                Frostyspawners.PLUGIN.getConfig().getInt("levels." + this.level + ".cost");
            } catch (Exception var4) {
                return 1;
            }

            try {
                Frostyspawners.PLUGIN.getConfig().getInt("levels." + this.level + ".delay");
            } catch (Exception var3) {
                return 2;
            }

            try {
                if (!Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effectType").equals("none")) {
                    if (Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effectType").equals("effect")) {
                        Effect.valueOf(Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effect"));
                    }

                    if (!Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effectType").equals("particle")) {
                        return 3;
                    }

                    Particle.valueOf(Frostyspawners.PLUGIN.getConfig().getString("levels." + this.level + ".effect"));
                }

                return 5;
            } catch (Exception var2) {
                return 4;
            }
        } else {
            return 0;
        }
    }
}
