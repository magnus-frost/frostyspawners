package me.frostdev.frostyspawners.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.spawners.SpawnerSaver;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class Data {
    private Frostyspawners main;
    private Map<String, Spawner> spawners;
    private SpawnerSaver saver;

    public Data(Frostyspawners as) {
        this.main = as;
        this.saver = new SpawnerSaver(as);
        this.spawners = new HashMap();
        Iterator var3 = this.saver.loadSpawners().iterator();

        while(var3.hasNext()) {
            Spawner spawner = (Spawner)var3.next();
            this.spawners.put(spawner.getID(), spawner);
        }

    }

    public void save() {
        this.saver.save();
    }

    public SpawnerSaver getSaver() {
        return this.saver;
    }

    public void saveSpawners() {
        this.saver.save(this.spawners.values());
    }

    public Map<String, Spawner> getSpawners() {
        return this.spawners;
    }

    public void addSpawner(Spawner spawner) {
        this.spawners.put(spawner.getID(), spawner);
    }

    public Spawner getSpawner(Block block) {
        if (block.getType() != this.main.items.spawner(1).getType()) {
            return null;
        } else if (this.spawners.get(Util.serializeLocationToId(block.getLocation())) != null) {
            return (Spawner)this.spawners.get(Util.serializeLocationToId(block.getLocation()));
        } else {
            Spawner spawner = new Spawner(block);
            this.addSpawner(spawner);
            return spawner;
        }
    }

    public Spawner getSpawner(Location location) {
        if (location.getBlock().getType() != this.main.items.spawner(1).getType()) {
            return null;
        } else if (this.spawners.get(Util.serializeLocationToId(location)) != null) {
            return (Spawner)this.spawners.get(Util.serializeLocationToId(location));
        } else {
            Spawner spawner = new Spawner(location.getBlock());
            this.addSpawner(spawner);
            return spawner;
        }
    }

    public Spawner getSpawner(String id) {
        Location loc = Util.deserializeId(id);
        if (loc.getBlock().getType() != this.main.items.spawner(1).getType()) {
            return null;
        } else if (this.spawners.get(id) != null) {
            return (Spawner)this.spawners.get(id);
        } else {
            Spawner spawner = new Spawner(Util.deserializeId(id).getBlock());
            this.addSpawner(spawner);
            return spawner;
        }
    }

    public int removeSpawner(Spawner spawner) {
        if (!this.spawners.containsKey(spawner.getID())) {
            return 0;
        } else if (this.spawners.get(spawner.getID()) == spawner) {
            return 1;
        } else {
            spawner.getHologram().delete();
            this.spawners.remove(spawner.getID());
            this.set("spawners." + spawner.getID(), (Object)null);
            return 2;
        }
    }

    public int removeSpawner(String id) {
        if (!this.spawners.containsKey(id)) {
            return 0;
        } else {
            this.spawners.remove(id);
            this.set("spawners." + id, (Object)null);
            return 1;
        }
    }

    public void changeSpawnerLevel(Spawner spawner, int level) {
        this.saver.changeLevel(spawner, level);
    }

    public void saveSpawner(Spawner spawner) {
        this.saver.save(spawner);
    }

    public void reloadSpawners() {
        Iterator var2 = this.saver.loadSpawners().iterator();

        while(var2.hasNext()) {
            Spawner spawner = (Spawner)var2.next();
            this.spawners.put(spawner.getID(), spawner);
        }

    }

    public void cleanSpawners() {
        this.saver.cleanup();
    }

    public void clearSpawners() {
        this.saver.clear();
    }

    public void set(String path, Object value) {
        this.saver.getSaver().set(path, value);
        this.save();
    }

    public int getLevel(Spawner spawner) {
        return this.getInt("spawners." + spawner.getID() + ".level");
    }

    public boolean getEnabled(Spawner spawner) {
        return this.getBoolean("spawners." + spawner.getID() + ".enabled");
    }

    public boolean hasOwner(Spawner spawner) {
        return this.getString("spawners." + spawner.getID() + ".owner") != null;
    }

    public UUID getOwner(Spawner spawner) {
        return UUID.fromString(this.getString("spawners." + spawner.getID() + ".owner"));
    }

    public boolean getLocked(Spawner spawner) {
        return this.getBoolean("spawners." + spawner.getID() + ".locked");
    }

    public boolean getShowDelay(Spawner spawner) {
        return this.getBoolean("spawners." + spawner.getID() + ".showDelay");
    }

    public void setLevel(Spawner spawner, int level) {
        this.set("spawners." + spawner.getID() + ".level", level);
    }

    public void setEnabled(Spawner spawner, boolean value) {
        this.set("spawners." + spawner.getID() + ".enabled", value);
    }

    public void setOwner(Spawner spawner, UUID owner) {
        this.set("spawners." + spawner.getID() + ".owner", owner.toString());
    }

    public void setLocked(Spawner spawner, boolean value) {
        this.set("spawners." + spawner.getID() + ".locked", value);
    }

    public void setShowDelay(Spawner spawner, boolean value) {
        this.set("spawners." + spawner.getID() + ".showDelay", value);
    }

    public Object get(String path) {
        return this.saver.getSaver().get(path);
    }

    public String getString(String path) {
        return this.saver.getSaver().getString(path);
    }

    public boolean getBoolean(String path) {
        return this.saver.getSaver().getBoolean(path);
    }

    public int getInt(String path) {
        return this.saver.getSaver().getInt(path);
    }

    public double getDouble(String path) {
        return this.saver.getSaver().getDouble(path);
    }

    public float getFloat(String path) {
        return (float)this.saver.getSaver().getInt(path);
    }
}
