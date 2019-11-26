package me.frostdev.frostyspawners.util.config;

import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigInteger implements ConfigSetting {
    private String path;
    private int def;

    public ConfigInteger(String p, int d) {
        this.path = p;
        this.def = d;
    }

    public Integer get() {
        return !this.validValue(Frostyspawners.PLUGIN.getConfig().get(this.path)) ? this.def : (Integer)Frostyspawners.PLUGIN.getConfig().get(this.path);
    }

    public Integer getDefault() {
        return this.def;
    }

    public boolean isSet(YamlConfiguration conf) {
        if (!conf.isSet(this.path)) {
            return false;
        } else {
            return this.validValue(conf.get(this.path));
        }
    }

    public boolean validValue(Object v) {
        return v != null && v.getClass().equals(String.class);
    }

    public String getPath() {
        return this.path;
    }
}
