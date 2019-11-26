package me.frostdev.frostyspawners.runnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnerInspect implements Runnable {
    private int id;
    private Player player;
    private Spawner spawner;
    private Hologram holo;

    public SpawnerInspect(Spawner s, Player p) {
        this.spawner = s;
        this.player = p;
        Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)(this.spawner.getBlock().getY() + 2), (double)this.spawner.getBlock().getZ() + 0.5D);
        this.holo = HologramsAPI.createHologram(Frostyspawners.INSTANCE, loc);
        VisibilityManager man = this.holo.getVisibilityManager();
        man.showTo(this.player);
        man.setVisibleByDefault(false);
        this.holo.appendTextLine(ChatColor.GOLD + this.spawner.getSpawnedEntity() + ChatColor.WHITE + " Spawner");
        if (this.spawner.hasOwner()) {
            this.holo.appendTextLine(ChatColor.GOLD + "Owner: " + ChatColor.WHITE + this.spawner.getOwner().getName());
        } else {
            this.holo.appendTextLine(ChatColor.GOLD + "Owner: " + ChatColor.WHITE + "None");
        }

        this.holo.appendTextLine(ChatColor.GOLD + "Level: " + ChatColor.WHITE + this.spawner.getLevel());
    }

    public int getId() {
        return this.id;
    }

    public void setTaskId(int id) {
        this.id = id;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

    public void run() {
        this.holo.delete();
        this.spawner.removeInspect(this.player);
    }
}
