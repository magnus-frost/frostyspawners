package me.frostdev.frostyspawners.util.config;

import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigBoolean implements ConfigSetting {
    private String path;
    private Boolean def;

    public ConfigBoolean(String p, boolean d) {
        this.path = p;
        this.def = d;
    }

    public Boolean get() {
        return !this.validValue(Frostyspawners.PLUGIN.getConfig().get(this.path)) ? this.def : (Boolean)Frostyspawners.PLUGIN.getConfig().get(this.path);
    }

    public Boolean getDefault() {
        return this.def;
    }

    public boolean validValue(Object v) {
        return v != null && v.getClass().equals(Boolean.class);
    }

    public boolean isSet(YamlConfiguration conf) {
        if (!conf.isSet(this.path)) {
            return false;
        } else {
            return this.validValue(conf.get(this.path));
        }
    }

    public String getPath() {
        return this.path;
    }
}
