package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ItemSpawnListener implements Listener {
    private Frostyspawners main;

    public ItemSpawnListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onItemSpawn(ItemSpawnEvent e) {
        if(e.getEntity().getItemStack().getType().equals(Material.EGG) && e.getEntity().getNearbyEntities(1,1,1).contains(EntityType.CHICKEN)){
            List<Entity> chickencheck = e.getEntity().getNearbyEntities(1, 1, 1);
            Entity chicken = chickencheck.get(0);
            for(int i = 0; i<chickencheck.size(); i++){
               if(chickencheck.get(i).hasMetadata("frosty_count")){
                   chicken = chickencheck.get(i);
               }
            }
            if(chicken.hasMetadata("frosty_count")){
                int count = chicken.getMetadata("frosty_count").get(0).asInt();
                Random rand = new Random();
                count = count - rand.ints().limit(5).sum();
                ItemStack egg = e.getEntity().getItemStack();
                count = count - rand.nextInt();
                egg.add(count);
            }

        }else{
            return;
        }
    }
}
