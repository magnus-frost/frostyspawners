package me.frostdev.frostyspawners.listener;

import java.util.ArrayList;
import java.util.List;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.Lang;
import me.frostdev.frostyspawners.api.event.SpawnerBreakEvent;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.config.Config;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerBreakListener implements Listener {
    private Frostyspawners main;
    private List<Player> players;

    public SpawnerBreakListener(Frostyspawners as) {
        this.main = as;
        this.players = new ArrayList();
    }

    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void onSpawnerBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Spawner spawner = this.main.getData().getSpawner(b);

        if (Config.silkTouchMine.get()) {

            if (b.getType() == this.main.items.spawner(1).getType()) {
                ItemStack hand = p.getInventory().getItemInMainHand();
                if (!this.isPickaxe(hand)) {
                    return;
                }
                if(!spawner.getOwner().getUniqueId().equals(p.getUniqueId()) && !p.isOp()){
                    p.sendMessage("You do not have permission to access this spawner.");
                    e.setCancelled(true);
                    return;
                }
                if (!hand.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                    if (p.isSneaking()) {
                        p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_BREAK_DESTROY.toString().replace("%type%", spawner.getSpawnedEntity()).replace("%level%", String.valueOf(spawner.getLevel())));
                        return;
                    }

                    if (Config.silkTouchProtect.get()) {
                        if (this.players.contains(p)) {
                            p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_BREAK_DESTROY.toString().replace("%type%", spawner.getSpawnedEntity()).replace("%level%", String.valueOf(spawner.getLevel())));
                            this.players.remove(p);
                            return;
                        }

                        e.setCancelled(true);
                        p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_BREAK_NOSILKTOUCH.toString());
                        this.players.add(p);
                        return;
                    }
                }

                SpawnerBreakEvent event = new SpawnerBreakEvent(spawner, p);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

                spawner.getHologram().delete();
                this.main.getData().removeSpawner(spawner);
                e.setExpToDrop(0);
                b.getDrops().clear();
                ItemStack sitem = this.main.items.spawner(1);
                ItemMeta meta = sitem.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + spawner.getSpawnedEntity() + ChatColor.RESET + " Spawner");
                List<String> lore = new ArrayList();
                lore.add(ChatColor.GOLD + "Level: " + ChatColor.RESET + spawner.getLevel());
                meta.setLore(lore);
                sitem.setItemMeta(meta);
                spawner.getWorld().dropItem(spawner.getLocation(), sitem);
                p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_BREAK_MINE.toString().replace("%type%", spawner.getSpawnedEntity()).replace("%level%", String.valueOf(spawner.getLevel())));
                if (this.players.contains(p)) {
                    this.players.remove(p);
                }
            }

        }
    }

    private boolean isPickaxe(ItemStack item) {
        return item.getType() == this.main.items.wood_pickaxe(1).getType() || item.getType() == Material.STONE_PICKAXE || item.getType() == Material.IRON_PICKAXE || item.getType() == this.main.items.gold_pickaxe(1).getType() || item.getType() == Material.DIAMOND_PICKAXE;
    }
}
