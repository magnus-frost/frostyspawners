package me.frostdev.frostyspawners.spawners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpawnerSaver {
    private Frostyspawners main;
    private File file;
    private FileConfiguration filec;

    public SpawnerSaver(Frostyspawners as) {
        this.main = as;
        this.setup();
    }

    public void save(Collection<Spawner> collection) {
        Logger.debug("Saving spawners to save file...");
        Iterator var3 = collection.iterator();

        while(var3.hasNext()) {
            Spawner spawner = (Spawner)var3.next();
            this.filec.set("spawners." + spawner.getID(), spawner.serialize());
        }

        this.save();
        Logger.debug("Saving complete.");
        collection.clear();
        this.reload();
    }

    public Collection<Spawner> loadSpawners() {
        Logger.info("Loading spawners from save file...");
        List<Spawner> spawners = new ArrayList();
        ConfigurationSection section = this.filec.getConfigurationSection("spawners");
        if (section == null) {
            Logger.info("Spawner list is null.");
            this.filec.createSection("spawners");
            return spawners;
        } else {
            Iterator var4 = section.getKeys(false).iterator();

            while(var4.hasNext()) {
                String s = (String)var4.next();
                Spawner spawner = null;

                try {
                    spawner = Spawner.deserialize(this.filec.getConfigurationSection("spawners." + s).getValues(false));
                } catch (IllegalArgumentException var7) {
                    Logger.debug("Failed to load spawner from save file. Reason: " + var7.getMessage() + ".", var7);
                    continue;
                } catch (Exception var8) {
                    Logger.debug("Failed to load spawner from save file. Reason: unknown.", var8);
                    continue;
                }

                Logger.info("Loaded spawner with ID " + spawner.getID());
                spawners.add(spawner);
            }

            Logger.info("Loaded " + spawners.size() + " spawners.");
            return spawners;
        }
    }

    public Spawner getSpawner(String id) {
        String[] s1 = id.split("@");
        String[] s2 = s1[1].split(";");
        Block b = null;

        try {
            World world = Bukkit.getServer().getWorld(s1[0]);
            int x = Integer.valueOf(s2[0]);
            int y = Integer.valueOf(s2[1]);
            int z = Integer.valueOf(s2[2]);
            b = world.getBlockAt(x, y, z);
        } catch (ArrayIndexOutOfBoundsException var9) {
            Logger.debug("Spawner with ID '" + id + "' could not be loaded. Reason: invalid ID.");
            return null;
        }

        if (b == null || b.getType() != this.main.items.spawner(1).getType()) {
            this.filec.set("spawners." + id, (Object)null);
        }

        return new Spawner(b);
    }

    public void save(Spawner spawner) {
        Logger.debug("Spawning spawner '" + spawner.getID() + "' to file...");
        this.filec.set("spawners." + spawner.getID() + ".level", spawner.getLevel());
        if (spawner.hasOwner()) {
            this.filec.set("spawners." + spawner.getID() + ".owner", spawner.getOwner().getUniqueId().toString());
        }

        this.filec.set("spawners." + spawner.getID() + ".enabled", spawner.isEnabled());
        this.filec.set("spawners." + spawner.getID() + ".locked", spawner.isEnabled());

        try {
            this.filec.save(this.file);
        } catch (IOException var3) {
            Logger.error("Failed to save spawners to file.", var3);
            return;
        }

        this.reload();
    }

    public void reload() {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(this.file);
        this.filec = conf;
    }

    public void cleanup() {
        ArrayList<String> spawnerList = (ArrayList)this.filec.getStringList("spawners");
        if (spawnerList == null) {
            this.filec.createSection("spawners");
        }

        for(int i = 0; i < spawnerList.size(); ++i) {
            String s = (String)spawnerList.get(i);
            Logger.debug("Starting spawner cleanup...");
            String[] s1 = s.split("@");
            String[] s2 = s1[1].split(";");
            World world = Bukkit.getServer().getWorld(s1[0]);
            int x = Integer.valueOf(s2[0]);
            int y = Integer.valueOf(s2[1]);
            int z = Integer.valueOf(s2[2]);
            Block b = world.getBlockAt(x, y, z);
            if (b == null || b.getType() != this.main.items.spawner(1).getType()) {
                this.filec.set("spawners." + s, (Object)null);
            }

            Spawner spawner = new Spawner(b);
            if (!spawner.isValid()) {
                Logger.debug("Removed invalid spawner with ID '" + s + "'.");
                this.filec.set("spawners." + spawner.getID(), (Object)null);
                this.save();
            }

            Logger.debug("Cleanup complete.");
        }

    }

    public void clear() {
        Logger.debug("Cleared all spawners.");
        this.filec.set("spawners", (Object)null);
        this.save();
    }

    public void setup() {
        Logger.debug("Starting saver setup...");
        File file = new File(Frostyspawners.PLUGIN.getDataFolder(), File.separator + "spawners.yml");
        if (!file.exists()) {
            Logger.debug("Creating new save file...");

            try {
                Frostyspawners.PLUGIN.getDataFolder().mkdir();
                file.mkdir();
            } catch (Exception var5) {
                Logger.error("Failed to create spawners save file. Reason: " + var5.getClass().getSimpleName() + ".", var5);
                Logger.error("This is a fatal error. Now disabling");
                Frostyspawners.PLUGIN.getServer().getPluginManager().disablePlugin(Frostyspawners.PLUGIN);
                return;
            }
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        if (conf.getConfigurationSection("spawners") == null) {
            Logger.debug("Created new spawners list.");
            conf.createSection("spawners");
        }

        this.file = file;
        this.filec = conf;

        try {
            conf.save(file);
        } catch (IOException var4) {
            Logger.error("Failed to save data to spawner save file. Reason: " + var4.getClass().getSimpleName() + ".", var4);
        }

        Logger.debug("Setup complete.");
    }

    public void changeLevel(Spawner spawner, int level) {
        this.filec.set("spawners." + spawner.getID() + ".level", level);

        try {
            this.filec.save(this.file);
        } catch (IOException var4) {
            Logger.error("Failed to save spawners to file.", var4);
            return;
        }

        this.reload();
    }

    public void save() {
        try {
            this.filec.save(this.file);
        } catch (IOException var2) {
            Logger.error("Failed to save spawners save file. Reason: IOException (can't access save file).", var2);
            Logger.error("Make sure the file actually exists, and if it is not a folder named 'spawners.yml'.");
        }

    }

    public FileConfiguration getSaver() {
        return this.filec;
    }
}

