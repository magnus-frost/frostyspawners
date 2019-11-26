package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.api.event.SpawnerChangeLevelEvent.Cause;
import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.event.HandlerList;

public class SpawnerLevelupEvent extends SpawnerChangeLevelEvent {
    private static final HandlerList handlers = new HandlerList();

    public SpawnerLevelupEvent(Spawner s, int l, Cause c) {
        super(s, l - 1, l, c);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
