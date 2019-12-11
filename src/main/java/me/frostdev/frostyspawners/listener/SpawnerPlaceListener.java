package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.api.event.SpawnerChangeLevelEvent;
import me.frostdev.frostyspawners.api.event.SpawnerChangeLevelEvent.Cause;
import me.frostdev.frostyspawners.api.event.SpawnerPlaceEvent;
import me.frostdev.frostyspawners.exception.InvalidLevelException;
import me.frostdev.frostyspawners.exception.SetTypeFailException;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.config.Config;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerPlaceListener implements Listener {
    private Frostyspawners main;

    public SpawnerPlaceListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onSpawnerPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == this.main.items.spawner(1).getType()) {

            Player p = e.getPlayer();
            Block b = e.getBlock();
            Spawner spawner = this.main.getData().getSpawner(b);
            ItemStack hand = e.getItemInHand();
            String typeName = "";

            if (hand.hasItemMeta() && hand.getItemMeta().hasDisplayName()) {
                typeName = ChatColor.stripColor(hand.getItemMeta().getDisplayName().replace(" Spawner", "").toUpperCase().replace(" ", "_"));
            } else {
                typeName = hand.getItemMeta().getLocalizedName().replace(" Spawner", "").replace(" ", "_");
            }
            Block block = e.getBlockPlaced();
            Chunk c = block.getChunk();
            c.load();

            int check = SpawnerCount(c, Material.SPAWNER);
            if (check >= Config.limit.get()) {
                e.setCancelled(true);
                p.sendMessage("Chunk Spawner Limit Reached!");
            }
            spawner.setOwner(p.getUniqueId());
            SpawnerPlaceEvent placeEvent = new SpawnerPlaceEvent(spawner, p);
            Bukkit.getServer().getPluginManager().callEvent(placeEvent);
            if (!placeEvent.isCancelled()) {
                try {
                 //   p.sendMessage(typeName);
                    spawner.setSpawnedType(typeName);
                    spawner.getCreatureSpawner().setMaxNearbyEntities(Config.maxnearbyentities.get());
                    spawner.getCreatureSpawner().setSpawnRange(Config.maxradius.get());
                } catch (IllegalArgumentException var17) {
                    Logger.debug("Failed to set entity type for spawner '" + spawner.getID() + "'. Reason: '" + typeName + "' is not a valid entity type. Defaulting entity type to 'PIG'.", var17);

                    try {
                        p.sendMessage("Failed to set spawner type, reason unknown. Talk to magnus and enjoy pigs in the mean-time");
                        spawner.setSpawnedType(EntityType.PIG);
                    } catch (Exception var11) {
                        Logger.error("Failed to set entity type for spawner '" + spawner.getID() + "'. Reason: unknown.", var11);
                    }

                    return;
                } catch (SetTypeFailException var18) {
                    Logger.error("Failed to set entity type for spawner '" + spawner.getID() + "'. Reason: syntax error/unknown. Attempting to default spawner type to 'PIG'.", var18);

                    try {
                        spawner.setSpawnedType(EntityType.PIG);
                    } catch (Exception var16) {
                        Logger.error("Failed to set entity type for spawner '" + spawner.getID() + "'. Reason: unknown.", var16);
                    }
                }

                int level = 0;
                if (hand.hasItemMeta() && hand.getItemMeta().hasLore()) {
                    try {
                        level = Integer.valueOf(ChatColor.stripColor(((String)hand.getItemMeta().getLore().get(0)).replaceAll("Level: ", "")));
                    } catch (NullPointerException var14) {
                        Logger.debug("Failed to retrieve level for placed spawner with ID '" + spawner.getID() + "'. Reason: level is null.", var14);
                    } catch (Exception var15) {
                        Logger.debug("Failed to retrieve level for placed spawner with ID '" + spawner.getID() + "'. Reason: " + var15.getClass().getSimpleName() + ".", var15);
                    }
                }

                SpawnerChangeLevelEvent levelEvent = new SpawnerChangeLevelEvent(spawner, 0, level, Cause.PLACE);
                Bukkit.getServer().getPluginManager().callEvent(levelEvent);
                if (!levelEvent.isCancelled()) {
                    try {
                        spawner.setLevel(level);
                    } catch (InvalidLevelException var12) {
                        Logger.error("Failed to set level for spawner '" + spawner.getID() + "'. Reason: '" + level + "' is not a valid level (level can not be lower than 0 or higher than set max level).");
                    } catch (Exception var13) {
                        Logger.error("Failed to set level for spawner '" + spawner.getID() + "'. Reason: unknown.");
                    }

                    spawner.update();
                }
            }
        }
    }
    private int SpawnerCount(Chunk chunk, Material m) {
        int count = 0;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (chunk.getBlock(x, y, z).getType() == m) count++;
                }
            }
        }
        return count;
    }

}
