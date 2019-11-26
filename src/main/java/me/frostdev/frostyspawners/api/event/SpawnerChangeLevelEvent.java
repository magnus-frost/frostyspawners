package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SpawnerChangeLevelEvent extends SpawnerEvent implements Cancellable {
    private int previousLevel;
    private int newLevel;
    public SpawnerChangeLevelEvent.Cause cause;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerChangeLevelEvent(Spawner s, int p, int n, SpawnerChangeLevelEvent.Cause c) {
        super(s);
        this.previousLevel = p;
        this.newLevel = n;
        this.cause = c;
    }

    public int getPreviousLevel() {
        return this.previousLevel;
    }

    public int getNewLevel() {
        return this.newLevel;
    }

    public boolean isLevelUp() {
        return this.newLevel > this.previousLevel;
    }

    public SpawnerChangeLevelEvent.Cause getCause() {
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
        LEVELUP,
        COMMAND,
        PLUGIN,
        UNKNOWN;

        private Cause() {
        }
    }
}
