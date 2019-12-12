package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class SpawnerDeathListener implements Listener {
    private Frostyspawners main;

    public SpawnerDeathListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onSpawnerDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        String typename = Util.toUserFriendlyString(e.getEntityType());
        typename = ChatColor.GOLD + typename;
        Location location = entity.getLocation();


        if (entity.isCustomNameVisible() && entity.fromMobSpawner() && entity.getMetadata("frosty_ident").get(0).asBoolean()) {
            if(entity.getCustomName() != null && entity.getCustomName().contains(typename)){

                int temp = entity.getMetadata("frosty_count").get(0).asInt();
                if(temp > 1) {
                    e.setShouldPlayDeathSound(false);
                    List<ItemStack> drops = e.getDrops();
                    temp--;
                    for(int i = 0; i< drops.size(); i++) {
                        entity.getLocation().getWorld().dropItem(location, drops.get(i));
                    }
                    entity.setMetadata("frosty_count", new FixedMetadataValue(this.main, temp));
                    ConsoleCommandSender console = getServer().getConsoleSender();
                    entity.setCustomName(typename + " x"+ temp);
                    console.sendMessage(temp + " Lives left on Entity: " + entity.getType().toString() + ":" + entity.getUniqueId().toString());
                    console.sendMessage("MetaData: "+entity.getMetadata("frosty_count").get(0).asString());
                    e.setCancelled(true);
                }
            }


        }
    }
}
