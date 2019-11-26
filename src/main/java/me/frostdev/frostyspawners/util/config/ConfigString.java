package me.frostdev.frostyspawners.util.config;

import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigString implements ConfigSetting {
    private String path;
    private String def;

    public ConfigString(String p, String d) {
        this.path = p;
        this.def = d;
    }

    public String get() {
        return !this.validValue(Frostyspawners.PLUGIN.getConfig().get(this.path)) ? this.def : (String)Frostyspawners.PLUGIN.getConfig().get(this.path);
    }

    public String color() {
        return !this.validValue(Frostyspawners.PLUGIN.getConfig().get(this.path)) ? ChatColor.translateAlternateColorCodes('&', this.def) : ChatColor.translateAlternateColorCodes('&', Frostyspawners.PLUGIN.getConfig().getString(this.path));
    }

    public String getDefault() {
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
