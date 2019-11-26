package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.api.event.SpawnerChangeTypeEvent.Cause;
import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class SpawnerEggInteractEvent extends SpawnerChangeTypeEvent {
    private Player player;
    private ItemStack hand;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerEggInteractEvent(Spawner s, EntityType p, EntityType n, Player pl, ItemStack h) {
        super(s, p, n, Cause.EGG);
        this.player = pl;
        this.hand = h;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getHand() {
        return this.hand;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
