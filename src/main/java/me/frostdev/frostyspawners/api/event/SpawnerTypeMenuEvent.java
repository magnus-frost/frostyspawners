package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.api.event.SpawnerChangeTypeEvent.Cause;
import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class SpawnerTypeMenuEvent extends SpawnerChangeTypeEvent {
    private Player player;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerTypeMenuEvent(Spawner s, EntityType p, EntityType n, Player pl) {
        super(s, p, n, Cause.MENU);
        this.player = pl;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
