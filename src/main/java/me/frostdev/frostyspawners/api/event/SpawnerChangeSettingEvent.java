package me.frostdev.frostyspawners.api.event;

import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SpawnerChangeSettingEvent extends SpawnerEvent implements Cancellable {
    private Object previousValue;
    private Object newValue;
    private SpawnerChangeSettingEvent.Setting setting;
    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    public SpawnerChangeSettingEvent(Spawner s, Object p, Object n, SpawnerChangeSettingEvent.Setting se) {
        super(s);
        this.previousValue = p;
        this.newValue = n;
        this.setting = se;
    }

    public Object getPreviousValue() {
        return this.previousValue;
    }

    public Object getNewValue() {
        return this.newValue;
    }

    public SpawnerChangeSettingEvent.Setting getSetting() {
        return this.setting;
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

    public static enum Setting {
        ENABLED,
        LOCKED,
        SHOWDELAY;

        private Setting() {
        }
    }
}