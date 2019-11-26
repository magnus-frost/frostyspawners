package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SpawnerChangeTypeEvent extends SpawnerEvent implements Cancellable {
    private EntityType previousType;
    private EntityType newType;
    private SpawnerChangeTypeEvent.Cause cause;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerChangeTypeEvent(Spawner s, EntityType p, EntityType n, SpawnerChangeTypeEvent.Cause c) {
        super(s);
        this.previousType = p;
        this.newType = n;
        this.cause = c;
    }

    public EntityType getPreviousType() {
        return this.previousType;
    }

    public EntityType getNewType() {
        return this.newType;
    }

    public SpawnerChangeTypeEvent.Cause getCause() {
        return this.cause;
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

    public static enum Cause {
        PLACE,
        MENU,
        EGG,
        COMMAND,
        PLUGIN,
        UNKNOWN;

        private Cause() {
        }
    }
}
