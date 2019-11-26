package me.frostdev.frostyspawners.runnable;

import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.Logger;
import org.bukkit.Bukkit;

public class SpawnerShowDelay implements Runnable {
    private Spawner spawner;
    private int id;

    public SpawnerShowDelay(Spawner s) {
        this.spawner = s;
    }

    public void setTaskId(int i) {
        this.id = i;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

    public void run() {
        Logger.info(String.valueOf(this.spawner.getDelay()));
        if (this.spawner.getShowDelay() && this.spawner.isValid() && this.spawner.isEnabled()) {
            if (this.spawner.getHologram().size() > 0) {
                this.spawner.getHologram().clearLines();
                this.spawner.getHologram().appendTextLine(String.valueOf(this.spawner.getDelayInSeconds()));
            } else {
                this.spawner.getHologram().appendTextLine(String.valueOf(this.spawner.getDelayInSeconds()));
            }

        } else {
            if (this.spawner.getHologram().size() > 0) {
                this.spawner.getHologram().clearLines();
            }

            this.cancel();
        }
    }
}
