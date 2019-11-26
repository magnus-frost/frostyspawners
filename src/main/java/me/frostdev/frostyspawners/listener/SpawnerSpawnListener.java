package me.frostdev.frostyspawners.listener;

import java.util.Random;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class SpawnerSpawnListener implements Listener {
    private Frostyspawners main;

    public SpawnerSpawnListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onSpawnerSpawner(SpawnerSpawnEvent e) {
        Block b = e.getSpawner().getBlock();
        Spawner spawner = this.main.getData().getSpawner(b);
        if (!spawner.isEnabled()) {
            spawner.setDelay(spawner.getDefaultDelay());
            e.getEntity().remove();
        }

        me.frostdev.frostyspawners.api.event.SpawnerSpawnEvent event = new me.frostdev.frostyspawners.api.event.SpawnerSpawnEvent(spawner, e.getEntity(), e.getLocation());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            this.main.getServer().getScheduler().scheduleSyncDelayedTask(this.main, new SpawnerSpawnListener.schedule(spawner), 0L);
        }
    }

    class schedule implements Runnable {
        private Spawner spawner;

        public schedule(Spawner s) {
            this.spawner = s;
        }

        public void run() {
            Random r1 = new Random();
            int defSecDel = this.spawner.getDefaultDelayInSeconds();
            if (defSecDel == 0) {
                defSecDel = 20;
            }

            int a = r1.nextInt(200) / defSecDel;
            boolean b = r1.nextBoolean();
            int defaultDelay = this.spawner.getDefaultDelay();
            if (b) {
                defaultDelay -= a;
            } else {
                defaultDelay += a;
            }

            this.spawner.setDelay(defaultDelay);
            this.spawner.update();
        }
    }
}
