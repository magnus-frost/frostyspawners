package me.frostdev.frostyspawners.spawners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.exception.InvalidLevelException;
import me.frostdev.frostyspawners.exception.SetTypeFailException;
import me.frostdev.frostyspawners.runnable.SpawnerInspect;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.Util;
import me.frostdev.frostyspawners.util.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Spawner implements ConfigurationSerializable {
    private static Frostyspawners main;
    private String id;
    private UUID owner;
    private int level;
    private Block block;
    private CreatureSpawner spawner;
    private boolean enabled;
    private boolean locked;
    private boolean showDelay;
    private Hologram holo;
    private Map<UUID, SpawnerInspect> inspect;

    static {
        main = Frostyspawners.INSTANCE;
    }

    public Spawner(@Nonnull Block b) {
        if (b == null) {
            throw new NullPointerException("Block can not be null");
        } else if (b.getType() != main.items.spawner(1).getType()) {
            throw new IllegalArgumentException("Block has to be a spawner");
        } else {
            this.id = Util.serializeLocationToId(b.getLocation());
            this.block = b;
            this.spawner = (CreatureSpawner)b.getState();
            this.level = 0;
            this.enabled = true;
            this.locked = false;
            this.showDelay = false;
            Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)this.spawner.getBlock().getY() + 1.5D, (double)this.spawner.getBlock().getZ() + 0.5D);

            try {
                this.holo = HologramsAPI.createHologram(main, loc);
            } catch (NoClassDefFoundError var5) {
                return;
            }

            try {
                this.holo = HologramsAPI.createHologram(main, loc);
            } catch (NoClassDefFoundError var4) {
                return;
            }

            this.inspect = new HashMap();
        }
    }

    private Spawner(@Nonnull String id, UUID owner, @Nonnull int level, boolean enabled, boolean locked, boolean showDelay) {
        this.id = id;
        this.owner = owner;
        this.level = level;
        this.enabled = enabled;
        this.locked = locked;
        this.showDelay = showDelay;
        this.block = Util.deserializeId(id).getBlock();
        if (this.block.getType() != main.items.spawner(1).getType()) {
            throw new IllegalArgumentException("Spawner with '" + id + "' is no spawner anymore.");
        } else {
            this.spawner = (CreatureSpawner)this.block.getState();
            Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)this.spawner.getBlock().getY() + 1.5D, (double)this.spawner.getBlock().getZ() + 0.5D);

            try {
                this.holo = HologramsAPI.createHologram(main, loc);
            } catch (NoClassDefFoundError var9) {
                return;
            }

            this.inspect = new HashMap();
        }
    }

    public Spawner(@Nonnull Map<String, Object> data) {
        this.id = (String)data.get("id");
        this.owner = UUID.fromString((String)data.get("owner"));
        this.level = (Integer)data.get("level");
        this.enabled = (Boolean)data.get("enabled");
        this.locked = (Boolean)data.get("locked");
        this.showDelay = (Boolean)data.get("showDelay");
        this.block = Util.deserializeId(this.id).getBlock();
        this.spawner = (CreatureSpawner)this.block.getState();
        Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)this.spawner.getBlock().getY() + 1.5D, (double)this.spawner.getBlock().getZ() + 0.5D);

        try {
            this.holo = HologramsAPI.createHologram(main, loc);
        } catch (NoClassDefFoundError var4) {
            return;
        }

        this.inspect = new HashMap();
    }

    public String getID() {
        return this.id;
    }

    public Block getBlock() {
        return this.block;
    }

    public Location getLocation() {
        return this.spawner.getLocation();
    }

    public World getWorld() {
        return this.spawner.getWorld();
    }

    public void setLevel(int level) throws InvalidLevelException {
        if (level > Config.maxLevel.get()) {
            throw new InvalidLevelException("Level (" + level + ") can not be higher than maximum level (" + Config.maxLevel.get() + ").");
        } else if (level < 0) {
            throw new InvalidLevelException("Level (" + level + ") can not be lower than 0.");
        } else if (level != this.getLevel()) {
            this.level = level;
            this.spawner.setDelay(this.getDefaultDelay());
            this.spawner.setSpawnCount(Config.getLevel(level).getSpawnCount());
        }
    }

    public int levelUp() {
        if (this.getLevel() == Config.maxLevel.get()) {
            return 0;
        } else {
            try {
                this.setLevel(this.level + 1);
            } catch (InvalidLevelException var2) {
                Logger.error("Failed to level up spawner '" + this.id + "'. Reason: " + var2.getMessage(), var2);
                return 3;
            }

            return this.getLevel() < Config.maxLevel.get() ? 1 : 2;
        }
    }

    public int getLevel() {
        return this.level;
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

    public void setOwner(Player player) {
        this.owner = player.getUniqueId();
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }

    public OfflinePlayer getOwner() {
        return Bukkit.getServer().getOfflinePlayer(this.owner);
    }

    public ItemStack toItemStack() {
        return this.toItemStack(1);
    }

    public ItemStack toItemStack(int i) {
        ItemStack spawner = main.items.spawner(i);
        ItemMeta meta = spawner.getItemMeta();
        BlockStateMeta bmeta = (BlockStateMeta)meta;
        ((CreatureSpawner)bmeta.getBlockState()).setSpawnedType(this.getSpawnedType());
        bmeta.setDisplayName(ChatColor.GOLD + this.getSpawnedEntity() + ChatColor.WHITE + " Spawner");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.GRAY + "Level: " + ChatColor.GOLD + this.getLevel());
        bmeta.setLore(lore);
        spawner.setItemMeta(bmeta);
        return spawner;
    }

    public CreatureSpawner getCreatureSpawner() {
        return this.spawner;
    }

    public boolean getShowDelay() {
        return this.showDelay;
    }

    public void setShowDelay(boolean value) {
        this.showDelay = value;
    }

    public void setDelay(int delay) {
        this.spawner.setDelay(delay);
    }

    public void setDelayInSeconds(int delay) {
        this.spawner.setDelay(delay * 20);
    }

    public int getDelay() {
        return this.spawner.getDelay();
    }

    public int getDelayInSeconds() {
        return this.spawner.getDelay() / 20;
    }

    public int getDefaultDelay() {
        return Config.getLevel(this.level).getDelay();
    }

    public int getDefaultDelayInSeconds() {
        return Config.getLevel(this.level).getDelay() / 20;
    }

    public int getSpawnCount() {
        return Config.getLevel(this.level).getSpawnCount();
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setLocked(boolean value) {
        this.locked = value;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public EntityType getSpawnedType() {
        return this.spawner.getSpawnedType();
    }

    public void update() {
        this.spawner.update();
    }

    public boolean isValid() {
        return this.block.getType() == main.items.spawner(1).getType();
    }

    public String getSpawnedEntity() {
        return Util.toString(this.spawner.getSpawnedType());
    }

    public void setSpawnedType(EntityType type) throws SetTypeFailException {
        try {
            this.spawner.setSpawnedType(type);
        } catch (Exception var3) {
            throw new SetTypeFailException("Failed to set type for spawner '" + this.getID() + "'.");
        }

        this.update();
    }

    public void setSpawnedType(String type) throws IllegalArgumentException, SetTypeFailException {
        EntityType t = null;

        try {
            t = EntityType.valueOf(type);
        } catch (IllegalArgumentException var5) {
            throw new IllegalArgumentException("Invalid entity type '" + type + "'.");
        }

        try {
            this.spawner.setSpawnedType(t);
        } catch (Exception var4) {
            throw new SetTypeFailException("Failed to set type for spawner '" + this.getID() + "'.");
        }

        this.update();
    }

    public Hologram getHologram() {
        return this.holo;
    }

    public void toggleInspect(Player player) {
        SpawnerInspect inspectRunnable;
        if (!this.inspect.containsKey(player.getUniqueId())) {
            inspectRunnable = new SpawnerInspect(this, player);
            inspectRunnable.setTaskId(Bukkit.getScheduler().scheduleSyncDelayedTask(main, inspectRunnable, 100L));
            this.inspect.put(player.getUniqueId(), inspectRunnable);
        } else {
            inspectRunnable = (SpawnerInspect)this.inspect.get(player.getUniqueId());
            inspectRunnable.run();
            this.inspect.remove(player.getUniqueId());
        }

    }

    public boolean isInspecting(Player player) {
        return this.inspect.containsKey(player.getUniqueId());
    }

    public void removeInspect(Player player) {
        if (this.inspect.containsKey(player.getUniqueId())) {
            this.inspect.remove(player.getUniqueId());
        }

    }

    public void playEffect(Effect effect) {
        ArrayList<Location> circle = this.getCircle(this.spawner.getLocation(), 1.0D, 100);

        for(int i = 0; i < circle.size(); ++i) {
            Location loc = (Location)circle.get(i);
            loc.getWorld().playEffect(loc, effect, 1);
        }

    }

    public void playEffect(Particle particle) {
        ArrayList<Location> circle = this.getCircle(this.spawner.getLocation(), 1.0D, 100);

        for(int i = 0; i < circle.size(); ++i) {
            Location loc = (Location)circle.get(i);
            loc.getWorld().spawnParticle(particle, loc, 1);
        }

    }

    private ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = 6.283185307179586D / (double)amount;
        ArrayList<Location> locations = new ArrayList();

        for(int i = 0; i < amount; ++i) {
            double angle = (double)i * increment;
            double x = center.getX() + 0.5D + radius * Math.cos(angle);
            double z = center.getZ() + 0.5D + radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY() + 0.5D, z));
        }

        return locations;
    }

    public static ItemStack getAsItemStack(EntityType type) {
        return getAsItemStack(type, 0, 1);
    }

    public static ItemStack getAsItemStack(EntityType type, int level) {
        return getAsItemStack(type, level, 1);
    }

    public static ItemStack getAsItemStack(EntityType type, int level, int amount) {
        ItemStack spawner = main.items.spawner(amount);
        ItemMeta meta = spawner.getItemMeta();
        String entityType = Util.toString(type);
        List<String> lore = new ArrayList();
        lore.add(ChatColor.GOLD + "Level: " + ChatColor.WHITE + level);
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + entityType + ChatColor.WHITE + " Spawner");
        spawner.setItemMeta(meta);
        return spawner;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap();
        result.put("id", this.id);
        if (this.hasOwner()) {
            result.put("owner", this.owner.toString());
        } else {
            result.put("owner", "none");
        }

        result.put("level", this.level);
        result.put("enabled", this.enabled);
        result.put("locked", this.locked);
        result.put("showDelay", this.showDelay);
        return result;
    }

    public static Spawner deserialize(Map<String, Object> data) {
        String id = (String)data.get("id");
        String s = (String)data.get("owner");
        UUID owner = null;
        if (!s.equals("none")) {
            owner = UUID.fromString((String)data.get("owner"));
        }

        int level = (Integer)data.get("level");
        boolean enabled = (Boolean)data.get("enabled");
        boolean locked = (Boolean)data.get("locked");
        boolean showDelay = (Boolean)data.get("showDelay");
        return new Spawner(id, owner, level, enabled, locked, showDelay);
    }

    public static Spawner valueOf(Map<String, Object> data) {
        String id = (String)data.get("id");
        String s = (String)data.get("owner");
        UUID owner = null;
        if (!s.equals("none")) {
            owner = UUID.fromString((String)data.get("owner"));
        }

        int level = (Integer)data.get("level");
        boolean enabled = (Boolean)data.get("enabled");
        boolean locked = (Boolean)data.get("locked");
        boolean showDelay = (Boolean)data.get("showDelay");
        return new Spawner(id, owner, level, enabled, locked, showDelay);
    }
}
