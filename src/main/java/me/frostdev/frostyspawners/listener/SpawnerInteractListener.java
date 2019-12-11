package me.frostdev.frostyspawners.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.Lang;
import me.frostdev.frostyspawners.Permissions;
import me.frostdev.frostyspawners.api.event.SpawnerEggInteractEvent;
import me.frostdev.frostyspawners.api.event.SpawnerInspectEvent;
import me.frostdev.frostyspawners.api.event.SpawnerOpenMenuEvent;
import me.frostdev.frostyspawners.exception.SetTypeFailException;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.SoundHandler;
import me.frostdev.frostyspawners.util.Util;
import me.frostdev.frostyspawners.util.config.Config;
import me.frostdev.frostyspawners.util.items.SpawnEggLegacy;
import me.frostdev.frostyspawners.util.items.SpawnEggNew;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerInteractListener implements Listener {
    private Frostyspawners main;
    public static final Map<UUID, MenuHandler> players = new HashMap();

    public SpawnerInteractListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onSpawnerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK && this.main.hasHolographicDisplays() && e.getClickedBlock().getType() == this.main.items.spawner(1).getType() && e.getPlayer().hasPermission((new Permissions()).spawner_inspect) && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR && !e.getPlayer().isOp()) {
            Spawner spawner = this.main.getData().getSpawner(e.getClickedBlock());
            SpawnerInspectEvent inspectEvent = new SpawnerInspectEvent(spawner, e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(inspectEvent);
            if (!inspectEvent.isCancelled()) {
                spawner.toggleInspect(e.getPlayer());
            }

        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == this.main.items.spawner(1).getType()) {
                Player p = e.getPlayer();
                Block b = e.getClickedBlock();
                Spawner spawner;
                SpawnerOpenMenuEvent event;
                MenuHandler handler;
                if (Util.isSpawnEgg(p.getInventory().getItemInMainHand())) {
                    if (Config.eggsEnable.get()) {
                        spawner = this.main.getData().getSpawner(b);
                        if (spawner.isLocked() && !spawner.getOwner().getName().equals(p.getName())) {
                            return;
                        }

                        event = null;
                        handler = null;
                        ItemStack eggItem;
                        EntityType eggType;
                        if (Util.isLegacyVersion()) {
                            SpawnEggLegacy egg = new SpawnEggLegacy(p.getInventory().getItemInMainHand());
                            eggItem = egg.toItemStack();
                            eggType = egg.getSpawnedType();
                        } else {
                            SpawnEggNew egg = new SpawnEggNew(p.getInventory().getItemInMainHand());
                            eggItem = egg.toItemStack();
                            eggType = egg.getSpawnedType();
                        }

                        if ((!p.hasPermission((new Permissions()).spawner_egg_changetype) || !p.hasPermission("frostyspawners.spawner.egg." + Util.toString(eggType).toLowerCase().replace(" ", ""))) && !p.hasPermission((new Permissions()).spawner_egg_changetype_all)) {
                            p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_CHANGE_EGG_NO_PERMISSION.toString().replace("%type%", Util.toString(eggType)));
                            return;
                        }

                        if (!p.getInventory().containsAtLeast(eggItem, Config.eggsAmount.get())) {
                            p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_CHANGE_EGG_NOT_ENOUGH_EGGS.toString().replace("%amount%", String.valueOf(Config.eggsAmount.get())));
                            return;
                        }

                        SpawnerEggInteractEvent event1 = new SpawnerEggInteractEvent(spawner, spawner.getSpawnedType(), eggType, p, eggItem);
                        Bukkit.getServer().getPluginManager().callEvent(event1);
                        if (event1.isCancelled()) {
                            return;
                        }

                        for(int i = 1; i < Config.eggsAmount.get(); ++i) {
                            p.getInventory().remove(eggItem);
                        }

                        try {
                            spawner.setSpawnedType(eggType);
                        } catch (SetTypeFailException var9) {
                            p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_CHANGE_TYPE_FAIL.toString());
                            Logger.debug("Failed to change entity type of spawner '" + spawner.getID() + "' with spawn egg. Player: " + p.getName() + ". Reason: unknown.", var9);
                            return;
                        }

                        p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_CHANGE_TYPE.toString().replace("%type%", Util.toString(eggType)));
                        SoundHandler.OPTION_CHANGETYPE.playSound(spawner.getLocation(), 1.0F, 2.0F);
                        return;
                    }
                } else {
                    if (!p.hasPermission((new Permissions()).menu) || p.getInventory().getItemInMainHand().getType() != Material.AIR || !Config.upgradeSpawners.get()) {
                        return;
                    }

                    spawner = this.main.getData().getSpawner(b);
                    if (spawner.hasOwner() && spawner.isLocked() && spawner.getOwner().getUniqueId() != p.getUniqueId()) {
                        p.sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_LOCKED.toString().replace("%owner%", spawner.getOwner().getName()));
                        return;
                    }

                    if (players.containsKey(p.getUniqueId())) {
                        return;
                    }

                    event = new SpawnerOpenMenuEvent(spawner, p);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }

                    handler = new MenuHandler(p, this.main, spawner);
                    this.main.getServer().getPluginManager().registerEvents(handler, this.main);
                    players.put(p.getUniqueId(), handler);
                }

            }
        }
    }
}
