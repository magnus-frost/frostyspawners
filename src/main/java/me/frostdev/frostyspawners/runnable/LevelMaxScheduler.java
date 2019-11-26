package me.frostdev.frostyspawners.runnable;

import java.util.Random;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.Lang;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.SoundHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

public class LevelMaxScheduler implements Runnable {
    private Frostyspawners main;
    private Spawner spawner;
    private boolean showDelay = false;
    int i;

    public LevelMaxScheduler(Frostyspawners as, Spawner s) {
        this.main = as;
        this.spawner = s;
        this.i = 0;
    }

    public void run() {
        if (this.i < 4) {
            ++this.i;
            this.randomEffect();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, this, 12L);
        } else {
            this.showDelay = this.spawner.getShowDelay();
            this.spawner.setShowDelay(false);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, new LevelMaxScheduler.effect(), 20L);
        }

    }

    private void randomEffect() {
        Random r = new Random();
        int i = r.nextInt(4);
        double x = r.nextDouble() - r.nextDouble();
        double y = r.nextDouble() - r.nextDouble();
        double z = r.nextDouble() - r.nextDouble();
        Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + x, (double)(this.spawner.getBlock().getY() + 2) + y, (double)this.spawner.getBlock().getZ() + z);
        switch(i) {
            case 0:
                SoundHandler.UPGRADE_MAX_RANDOM_1.playSound(loc, 1.0F, 1.0F);
            case 1:
                SoundHandler.UPGRADE_MAX_RANDOM_2.playSound(loc, 1.0F, 1.0F);
            case 2:
                SoundHandler.UPGRADE_MAX_RANDOM_3.playSound(loc, 1.0F, 1.0F);
            case 3:
                SoundHandler.UPGRADE_MAX_RANDOM_4.playSound(loc, 1.0F, 1.0F);
            default:
                this.spawner.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 25);
        }
    }

    class effect implements Runnable {
        effect() {
        }

        public void run() {
            Location loc = new Location(LevelMaxScheduler.this.spawner.getWorld(), (double)LevelMaxScheduler.this.spawner.getBlock().getX() + 0.5D, (double)(LevelMaxScheduler.this.spawner.getBlock().getY() + 2), (double)LevelMaxScheduler.this.spawner.getBlock().getZ() + 0.5D);
            SoundHandler.UPGRADE_MAX_EXPLOSION.playSound(loc, 1.0F, 1.0F);
            LevelMaxScheduler.this.spawner.getWorld().spawnParticle(Particle.TOTEM, loc, 25);
            if (LevelMaxScheduler.this.spawner.getShowDelay()) {
                LevelMaxScheduler.this.showDelay = true;
                LevelMaxScheduler.this.spawner.getHologram().clearLines();
            }

            LevelMaxScheduler.this.spawner.getHologram().appendTextLine(Lang.GUI_UPGRADE_MAXEDOUT.toString());
            Bukkit.getScheduler().scheduleSyncDelayedTask(LevelMaxScheduler.this.main, LevelMaxScheduler.this.new resetHolo(), 100L);
        }
    }

    class resetHolo implements Runnable {
        resetHolo() {
        }

        public void run() {
            LevelMaxScheduler.this.spawner.getHologram().clearLines();
            LevelMaxScheduler.this.spawner.setShowDelay(LevelMaxScheduler.this.showDelay);
        }
    }
}
