package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SpawnerSpawnEvent extends SpawnerEvent implements Cancellable {
    private Entity entity;
    private Location location;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerSpawnEvent(Spawner s, Entity e, Location l) {
        super(s);
        this.entity = e;
        this.location = l;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public EntityType getEntityType() {
        return this.entity.getType();
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean value) {
        this.cancelled = value;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
