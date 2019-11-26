package me.frostdev.frostyspawners.runnable;

import java.util.Iterator;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.config.Config;
import me.frostdev.frostyspawners.util.config.ConfigLevel;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnerPlayEffect implements Runnable {
    private Frostyspawners main;

    public SpawnerPlayEffect(Frostyspawners as) {
        this.main = as;
    }

    public void run() {
        Iterator var2 = this.main.getData().getSpawners().values().iterator();

        while(true) {
            Spawner spawner;
            do {
                if (!var2.hasNext()) {
                    return;
                }

                spawner = (Spawner)var2.next();
            } while(!spawner.isValid());

            boolean playerNear = false;
            Iterator var5 = spawner.getWorld().getNearbyEntities(spawner.getLocation(), 50.0D, 50.0D, 50.0D).iterator();

            while(var5.hasNext()) {
                Entity near = (Entity)var5.next();
                if (near.getType() == EntityType.PLAYER) {
                    playerNear = true;
                    break;
                }
            }

            if (playerNear) {
                ConfigLevel level = Config.getLevel(spawner.getLevel());
                if (level.isValid() == 5 && !level.getEffectType().equals("none")) {
                    if (level.getEffectType().equals("particle")) {
                        Particle particle = (Particle)level.getEffect();
                        spawner.playEffect(particle);
                    } else if (level.getEffectType().equals("effect")) {
                        Effect effect = (Effect)level.getEffect();
                        spawner.playEffect(effect);
                    }
                }
            }
        }
    }
}
