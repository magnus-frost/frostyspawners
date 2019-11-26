package me.frostdev.frostyspawners.runnable;

import java.util.Iterator;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;

public class SpawnerShowDelayScheduler implements Runnable {
    private Frostyspawners main;

    public SpawnerShowDelayScheduler(Frostyspawners as) {
        this.main = as;
    }

    public void run() {
        Iterator var2 = this.main.getData().getSpawners().values().iterator();

        while(true) {
            while(var2.hasNext()) {
                Spawner spawner = (Spawner)var2.next();
                if (spawner.isEnabled() && spawner.getShowDelay()) {
                    spawner.update();

                    try {
                        if (spawner.getHologram().size() > 0) {
                            spawner.getHologram().clearLines();
                            spawner.getHologram().appendTextLine(String.valueOf(spawner.getDelayInSeconds()));
                        } else {
                            spawner.getHologram().appendTextLine(String.valueOf(spawner.getDelayInSeconds()));
                        }
                    } catch (IllegalArgumentException var4) {
                        if (var4.getMessage().equals("hologram already deleted")) {
                        }
                    }
                } else if (spawner.getHologram().size() > 0) {
                    spawner.getHologram().clearLines();
                }
            }

            return;
        }
    }
}
