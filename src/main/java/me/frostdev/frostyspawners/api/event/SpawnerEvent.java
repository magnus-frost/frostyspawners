package me.frostdev.frostyspawners.api.event;


import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnerEvent extends Event {
    private Spawner spawner;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerEvent(Spawner s) {
        this.spawner = s;
    }

    public Spawner getSpawner() {
        return this.spawner;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
