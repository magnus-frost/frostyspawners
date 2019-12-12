package me.frostdev.frostyspawners;

import me.frostdev.frostyspawners.commands.CommandAdvancedspawners;
import me.frostdev.frostyspawners.exception.InvalidLevelException;
import me.frostdev.frostyspawners.exception.SetLevelFailException;
import me.frostdev.frostyspawners.listener.*;
import me.frostdev.frostyspawners.runnable.SpawnerPlayEffect;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.spawners.menu.Menu;
import me.frostdev.frostyspawners.util.Data;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.Util;
import me.frostdev.frostyspawners.util.config.Config;
import me.frostdev.frostyspawners.util.config.ConfigLevel;
import me.frostdev.frostyspawners.util.items.Items;
import me.frostdev.frostyspawners.util.items.ItemsLegacy;
import me.frostdev.frostyspawners.util.items.ItemsNew;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import java.util.Iterator;
import java.util.Set;

public class Frostyspawners extends JavaPlugin {
    public static Plugin PLUGIN;
    public static Frostyspawners INSTANCE;
    private String name = "FrostySpawners";
    private String versionID = "0.5";
    private String spigotID = "1.14.2";
    public Menu menu;
    public Items items;
    private Data data;
    public static YamlConfiguration LANG;
    public static File LANG_FILE;
    private boolean holograms;
    private Economy ECON = null;
    private String perms = null;


    public Frostyspawners() {
    }

    public void onEnable() {
        PLUGIN = this;
        INSTANCE = this;
        this.saveDefaultConfig();
        if (Util.isLegacyVersion()) {
            this.items = new ItemsLegacy();
        } else {
            this.items = new ItemsNew();
        }

        new Config();
        new Util();
        this.menu = new Menu(this);
        Logger.info("Loaded configuration.");
        if (Config.upgradeWithMoney.get()) {
            this.loadVault();
        }

        this.data = new Data(this);

        try {
            this.loadLang();
        } catch (Exception var4) {
            Logger.error("Failed to load language file(s). This is a fatal error", var4);
            Logger.error("This is a fatal error. Now disabling");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Logger.info("Using language file '" + LANG_FILE.getName() + "'.");

        try {
            this.checkLevels();
        } catch (Exception var3) {
            Logger.error("Failed to check and correct invalid configurated levels.", var3);
            Logger.info("If this problem persists, please report to plugin developer.");
        }

        try {
            this.validateConfiguratedLevels();
        } catch (Exception var2) {
            Logger.error("Failed to validate configurated levels.", var2);
            Logger.info("If this problem persists, please report to plugin developer.");
        }

        Logger.info("Validated configurated levels.");
        if (Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
            Logger.info("HolographicDisplays found. Special features enabled.");
            this.holograms = true;
        } else {
            this.holograms = false;
        }

        this.getCommand("frostyspawners").setExecutor(new CommandAdvancedspawners(this));
        this.getServer().getPluginManager().registerEvents(new SpawnerBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new SpawnerRenameListener(this), this);
        this.getServer().getPluginManager().registerEvents(new SpawnerSpawnListener(this), this);
        this.getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new SpawnerInteractListener(this), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SpawnerPlayEffect(this), 0L, 20L);
        this.getServer().getPluginManager().registerEvents(new SpawnerDeathListener(this), this);
        ConfigurationSerialization.registerClass(Spawner.class);
        Logger.info("Setup complete. Enabled " + this.name + "version " + this.versionID + ".");
    }


    public void onDisable() {
        this.data.saveSpawners();
    }

    public Data getData() {
        return this.data;
    }

    private void validateConfiguratedLevels() {
        int level = 0;

        for(int maxLevel = Config.maxLevel.get(); level <= maxLevel; ++level) {
            ConfigLevel clevel = Config.getLevel(level);
            int isValid = clevel.isValid();
            if (isValid == 0) {
                Logger.debug("Level " + level + ": not set");
            } else if (isValid == 1) {
                Logger.debug("Level " + level + ": cost not valid");
            } else if (isValid == 2) {
                Logger.debug("Level " + level + ": delay not valid");
            } else if (isValid == 3) {
                Logger.debug("Level " + level + ": Could not identify effect/particle type");
            } else if (isValid == 4) {
                Logger.debug("Level " + level + ": Error while trying to set effect/particle.");
            } else if (isValid == 5) {
                Logger.debug("Level " + level + ": Level is valid");
            }
        }

    }

    public void checkLevels() throws SetLevelFailException {
        Set<String> slist = this.data.getSaver().getSaver().getConfigurationSection("spawners").getKeys(false);
        Iterator var3 = slist.iterator();

        while(var3.hasNext()) {
            String id = (String)var3.next();
            if (id.contains("@")) {
                String[] s1 = id.split("@");
                if (s1[1].contains(";")) {
                    String[] s2 = s1[1].split(";");
                    String world = s1[0];
                    World w = this.getServer().getWorld(world);
                    int x = Integer.valueOf(s2[0]);
                    int y = Integer.valueOf(s2[1]);
                    int z = Integer.valueOf(s2[2]);
                    Block b = w.getBlockAt(x, y, z);
                    if (b.getType() != this.items.spawner(1).getType()) {
                        this.data.getSaver().getSaver().set("spawners." + id, (Object)null);
                    } else {
                        Spawner spawner = new Spawner(b);
                        if (!spawner.isValid()) {
                            this.data.getSaver().getSaver().set("spawners." + spawner.getID(), (Object)null);
                        } else {
                            if (spawner.getLevel() > this.getConfig().getInt("settings.maxLevel")) {
                                try {
                                    spawner.setLevel(this.getConfig().getInt("settings.maxLevel"));
                                } catch (InvalidLevelException var15) {
                                    var15.printStackTrace();
                                }
                            }

                            if (spawner.getLevel() < 0) {
                                try {
                                    spawner.setLevel(0);
                                } catch (InvalidLevelException var14) {
                                    var14.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean loadVault() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                this.ECON = (Economy)rsp.getProvider();
                return this.ECON != null;
            }
        }
    }

    public Economy getEconomy() {
        return this.ECON;
    }

    public boolean hasHolographicDisplays() {
        return this.holograms;
    }

    public String getPluginName() {
        return this.name;
    }

    public String getVersionID() {
        return this.versionID;
    }

    public String getSpigotID() {
        return this.spigotID;
    }

    public File getLangFile() {
        return LANG_FILE;
    }

    public YamlConfiguration loadLang() {
        File lang = new File(this.getDataFolder(), File.separator + "lang" + File.separator + "en_US.yml");
        if (!lang.exists()) {
            try {
                this.getDataFolder().mkdir();
                lang.mkdir();
                InputStream defConfigStream = this.getResource(this.getDataFolder() + File.separator + "lang" + File.separator + "en_US.yml");
                if (defConfigStream != null) {
                    File langFile = new File(this.getDataFolder(), File.separator + "lang" + File.separator + "en_US.yml");
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(langFile);
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                    return defConfig;
                }
            } catch (IOException var8) {
                var8.printStackTrace();
                Logger.error("Couldn't create language file.");
                Logger.error("This is a fatal error. Now disabling");
                this.setEnabled(false);
            }
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        Lang[] var6;
        int var5 = (var6 = Lang.values()).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            Lang item = var6[var4];
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }

        Lang.setFile(conf);
        LANG = conf;
        LANG_FILE = lang;

        try {
            conf.save(this.getLangFile());
        } catch (IOException var7) {
            Logger.error("Failed to save en_US.yml.");
            Logger.error("Report this stack trace to plugin developer.");
            var7.printStackTrace();
        }

        return conf;
    }
}




