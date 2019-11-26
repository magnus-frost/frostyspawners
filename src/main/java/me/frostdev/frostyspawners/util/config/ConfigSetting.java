package me.frostdev.frostyspawners.util.config;


import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigSetting {
    Object get();

    Object getDefault();

    boolean isSet(YamlConfiguration var1);

    boolean validValue(Object var1);

    String getPath();
}
