package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.Lang;
import me.frostdev.frostyspawners.Permissions;
import me.frostdev.frostyspawners.api.event.SpawnerBreakEvent;
import me.frostdev.frostyspawners.api.event.SpawnerChangeLevelEvent.Cause;
import me.frostdev.frostyspawners.api.event.SpawnerLevelupEvent;
import me.frostdev.frostyspawners.api.event.SpawnerTypeMenuEvent;
import me.frostdev.frostyspawners.exception.SetTypeFailException;
import me.frostdev.frostyspawners.runnable.LevelMaxScheduler;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.spawners.menu.MainMenuHolder;
import me.frostdev.frostyspawners.spawners.menu.SettingsMenuHolder;
import me.frostdev.frostyspawners.spawners.menu.TypeMenuHolder;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.SoundHandler;
import me.frostdev.frostyspawners.util.Util;
import me.frostdev.frostyspawners.util.config.Config;
import me.frostdev.frostyspawners.util.config.ConfigLevel;
import me.frostdev.frostyspawners.util.config.ConfigType;
import me.frostdev.frostyspawners.util.items.SpawnEggLegacy;
import me.frostdev.frostyspawners.util.items.SpawnEggNew;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

public class MenuHandler implements Listener {
    private Frostyspawners main;
    private Player player;
    private Spawner spawner;
    private Inventory menuMain;
    private Inventory menuSettings;
    private Map<Integer, Inventory> menuType;
    private String openMenu;
    private int page;
    private String prefix;

    public MenuHandler(Player p, Frostyspawners as, Spawner s) {
        this.prefix = Lang.PREFIX.toString();
        this.player = p;
        this.main = as;
        this.spawner = s;
        this.menuMain = this.main.menu.createMainMenu(this.spawner);
        this.menuSettings = this.main.menu.createSettingsMenu(this.spawner);
        this.menuType = this.main.menu.createTypeMenu(this.player);
        this.page = 1;
        SoundHandler.MENU_OPEN.playSound(this.spawner.getLocation(), 0.1F, 10.0F);
        this.openMenu("MAIN");
    }

    private void updateMenu(String action) {
        if (action.equals("MAIN") || action.equals("ALL")) {
            this.menuMain = this.main.menu.createMainMenu(this.spawner);
        }

        if (action.equals("SETTINGS") || action.equals("ALL")) {
            this.menuSettings = this.main.menu.createSettingsMenu(this.spawner);
        }

        if (action.equals("TYPE") || action.equals("ALL")) {
            this.menuType = this.main.menu.createTypeMenu(this.player);
        }

    }

    private void openMenu(String menu) {
        if (menu.equals("MAIN")) {
            this.updateMenu("MAIN");
            this.player.openInventory(this.menuMain);
            this.openMenu = menu;
        } else if (menu.equals("SETTINGS")) {
            this.updateMenu("SETTINGS");
            this.player.openInventory(this.menuSettings);
            this.openMenu = menu;
        } else if (menu.equals("TYPE")) {
            if (this.menuType.get(1) == null) {
                this.player.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_TYPE_NULL.toString());
                this.exit();
                return;
            }

            this.player.openInventory((Inventory)this.menuType.get(1));
            this.openMenu = menu;
        } else {
            this.updateMenu("MAIN");
            this.player.openInventory(this.menuMain);
        }

    }

    private void typeNextPage() {
        ++this.page;
        this.player.openInventory((Inventory)this.menuType.get(this.page));
    }

    private void typePreviousPage() {
        --this.page;
        this.player.openInventory((Inventory)this.menuType.get(this.page));
    }

    public InventoryHolder openInventory() {
        if (this.openMenu.equals("MAIN")) {
            return new MainMenuHolder();
        } else if (this.openMenu.equals("SETTINGS")) {
            return new SettingsMenuHolder();
        } else {
            return !this.openMenu.equals("TYPE_1") && !this.openMenu.equals("TYPE_2") ? null : new TypeMenuHolder();
        }
    }

    public void exit() {
        this.player.closeInventory();
        HandlerList.unregisterAll(this);
        SpawnerInteractListener.players.remove(this.player.getUniqueId());
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onMenuClick(InventoryClickEvent e) {
        if (!(e.getClickedInventory() instanceof PlayerInventory) && e.getClickedInventory() != null && e.getClickedInventory().getHolder() != null && (e.getClickedInventory().getHolder() instanceof MainMenuHolder || e.getClickedInventory().getHolder() instanceof SettingsMenuHolder || e.getClickedInventory().getHolder() instanceof TypeMenuHolder) && e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getWhoClicked().getUniqueId() == this.player.getUniqueId()) {
            int debugrange = this.spawner.getCreatureSpawner().getSpawnRange();
            int debugspawncount = this.spawner.getCreatureSpawner().getSpawnCount();
            int debugdelay = this.spawner.getDelay();
            player.sendMessage(String.valueOf(debugrange));
            player.sendMessage(String.valueOf(debugspawncount));
            player.sendMessage(String.valueOf(debugdelay));

            if (e.getClick() != ClickType.LEFT && e.getClick() != ClickType.RIGHT) {
                e.setCancelled(true);
            } else {
                e.setCancelled(true);
                Player p = (Player)e.getWhoClicked();
                Inventory menu = e.getClickedInventory();
                ItemStack option = e.getCurrentItem();
                if (menu.getHolder() instanceof MainMenuHolder) {
                    if (option.getType() == this.main.items.exp_bottle(1).getType()) {
                        if (!Config.upgradeSpawners.get()) {
                            p.sendMessage(this.prefix + Lang.GUI_UPGRADE_DISABLED.toString());
                            this.exit();
                            return;
                        }

                        if (this.spawner.getLevel() == Config.maxLevel.get()) {
                            p.sendMessage(this.prefix + Lang.GUI_UPGRADE_MAXLEVEL.toString());
                            this.exit();
                            return;
                        }

                        ConfigLevel level = Config.getLevel(this.spawner.getLevel() + 1);
                        switch(level.isValid()) {
                            case 1:
                                Logger.debug("Failed to upgrade spawner '" + this.spawner.getID() + "'. Reason: Incorrectly configured level '" + this.spawner.getLevel() + 1 + "' (invalid cost).");
                                return;
                            case 2:
                                Logger.debug("Failed to upgrade spawner '" + this.spawner.getID() + "'. Reason: Incorrectly configured level '" + this.spawner.getLevel() + 1 + "' (invalid delay).");
                                return;
                            case 3:
                                Logger.debug("Failed to upgrade spawner '" + this.spawner.getID() + "'. Reason: Incorrectly configured level '" + this.spawner.getLevel() + 1 + "' (could not identify Effect/Particle type).");
                                return;
                            case 4:
                                Logger.debug("Failed to upgrade spawner '" + this.spawner.getID() + "'. Reason: error occured while setting effect/particle.");
                                return;
                            default:
                                if (Config.upgradeWithMoney.get()) {
                                    if (this.main.getEconomy() == null) {
                                        p.sendMessage(this.prefix + Lang.GUI_UPGRADE_ECON_FAIL_NULL.toString());
                                        Logger.debug("Failed to upgrade spawner '" + this.spawner.getID() + "'. Reason: no economy plugin found.");
                                        this.exit();
                                        return;
                                    }

                                    Economy econ = this.main.getEconomy();
                                    if (!econ.has(p, level.getCost())) {
                                        p.sendMessage(this.prefix + "You need at least " + econ.format(level.getCost()) + " to upgrade the spawner to level " + level.getLevel() +". Current balance: " +  econ.format(econ.getBalance(p)));
                                        this.exit();
                                        return;
                                    }

                                    EconomyResponse res = econ.withdrawPlayer(p, level.getCost());
                                    if (res.transactionSuccess()) {
                                        SpawnerLevelupEvent levelupEvent = new SpawnerLevelupEvent(this.spawner, this.spawner.getLevel() + 1, Cause.LEVELUP);
                                        Bukkit.getServer().getPluginManager().callEvent(levelupEvent);
                                        if (levelupEvent.isCancelled()) {
                                            return;
                                        }

                                        switch(this.spawner.levelUp()) {
                                            case 1:
                                                Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)this.spawner.getBlock().getY() + 1.2D, (double)this.spawner.getBlock().getZ() + 0.5D);
                                                this.spawner.getWorld().spawnParticle(Particle.LAVA, loc.getX(), loc.getY(), loc.getZ(), 25);
                                                SoundHandler.OPTION_UPGRADE.playSound(loc, 1.0F, 2.0F);
                                            case 2:
                                                if (Config.maxLevelEffect.get()) {
                                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Frostyspawners.PLUGIN, new LevelMaxScheduler(this.main, this.spawner));
                                                }
                                            default:
                                                p.sendMessage(this.prefix + Lang.GUI_UPGRADE_SUCCESS.toString().replace("%level%", String.valueOf(level.getLevel())));
                                        }
                                    } else {
                                        p.sendMessage(this.prefix + Lang.GUI_UPGRADE_FAIL.toString());
                                    }

                                    return;
                                }

                                if ((double)p.getLevel() < level.getCost()) {
                                    p.sendMessage(this.prefix + Lang.GUI_UPGRADE_EXP_NOT_ENOUGH.toString().replace("%explevel%", String.valueOf((int)level.getCost())).replace("%level%", String.valueOf(level.getLevel())));
                                    this.exit();
                                    return;
                                }

                                SpawnerLevelupEvent levelupEvent = new SpawnerLevelupEvent(this.spawner, this.spawner.getLevel() + 1, Cause.LEVELUP);
                                Bukkit.getServer().getPluginManager().callEvent(levelupEvent);
                                if (levelupEvent.isCancelled()) {
                                    return;
                                }

                                if (this.spawner.levelUp() == 2) {
                                    if (Config.maxLevelEffect.get()) {
                                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Frostyspawners.PLUGIN, new LevelMaxScheduler(this.main, this.spawner));
                                    }
                                } else {
                                    Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)this.spawner.getBlock().getY() + 1.2D, (double)this.spawner.getBlock().getZ() + 0.5D);
                                    this.spawner.getWorld().spawnParticle(Particle.LAVA, loc.getX(), loc.getY(), loc.getZ(), 25);
                                    SoundHandler.OPTION_UPGRADE.playSound(loc, 1.0F, 2.0F);
                                }

                                if (this.spawner.getDelay() > this.spawner.getDefaultDelay()) {
                                    this.spawner.setDelay(this.spawner.getDefaultDelay());
                                }

                                p.setLevel(p.getLevel() - (int)level.getCost());
                                p.sendMessage(this.prefix + Lang.GUI_UPGRADE_SUCCESS.toString().replace("%level%", String.valueOf(this.spawner.getLevel())));
                                this.exit();
                                return;
                        }
                    }

                    if (option.getType() == this.main.items.spawner(1).getType()) {
                        SoundHandler.OPTION_SELECT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.openMenu("SETTINGS");
                        return;
                    }
                    if (option.getType() == this.main.items.bedrock(1).getType()) {
                        SpawnerBreakEvent breakEvent = new SpawnerBreakEvent(this.spawner, this.player);
                        ItemStack toin = this.spawner.toItemStack();
                        p.getInventory().addItem(toin);
                        p.updateInventory();
                        Bukkit.getServer().getPluginManager().callEvent(breakEvent);
                        this.spawner.getBlock().setType(Material.AIR);
                        this.exit();
                        return;
                    }

                    if (option.getType() == this.main.items.wool(1, DyeColor.LIME).getType() || option.getType() == this.main.items.wool(1, DyeColor.RED).getType()) {
                        if (!this.main.hasHolographicDisplays()) {
                            p.sendMessage(this.prefix + Lang.GUI_SHOWDELAY_NOHOLO.toString());
                            if (this.spawner.getShowDelay()) {
                                this.spawner.setShowDelay(false);
                            }

                            this.exit();
                            return;
                        } else {
                            if (this.spawner.getShowDelay()) {
                                this.spawner.setShowDelay(false);
                                SoundHandler.OPTION_DISABLE.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                            } else {
                                this.spawner.setShowDelay(true);
                                SoundHandler.OPTION_ENABLE.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                            }

                            this.openMenu("MAIN");
                            return;
                        }
                    }

                    if (Util.isSpawnEgg(option) || option.equals(new ItemStack(Material.IRON_HELMET))) {
                        if (!Config.typeMenu.get()) {
                            p.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_EGG_DISABLED.toString());
                            this.exit();
                            return;
                        }
                        SoundHandler.OPTION_SELECT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.openMenu("TYPE");
                        return;
                    }

                    if (option.getType() == Material.BARRIER) {
                        SoundHandler.OPTION_EXIT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.exit();
                        return;
                    }
                }

                if (menu.getHolder() instanceof SettingsMenuHolder) {
                    if (e.getSlot() == 20) {

                        if (this.spawner.isEnabled()) {
                            this.spawner.setEnabled(false);
                            SoundHandler.OPTION_DISABLE.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                            if (this.spawner.getShowDelay()) {
                                this.spawner.setShowDelay(false);
                            }
                        } else {
                            this.spawner.setEnabled(true);
                            SoundHandler.OPTION_ENABLE.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        }

                        this.openMenu("SETTINGS");
                        return;
                    }

                    if (e.getSlot() == 24) {
                        if (!p.hasPermission((new Permissions()).menu_spawner_locked)) {
                            p.sendMessage(this.prefix + Lang.GUI_TOGGLE_NOPERM.toString());
                            this.exit();
                            return;
                        }

                        if (this.spawner.isLocked()) {
                            this.spawner.setLocked(false);
                            SoundHandler.OPTION_DISABLE.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        } else {
                            this.spawner.setLocked(true);
                            SoundHandler.OPTION_ENABLE.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        }

                        this.openMenu("SETTINGS");
                    }

                    if (option.getType() == Material.BARRIER) {
                        SoundHandler.OPTION_SELECT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.openMenu("MAIN");
                        return;
                    }
                }

                if (menu.getHolder() instanceof TypeMenuHolder) {
                    if (Util.isSpawnEgg(option)) {
                        EntityType type = null;
                        if (Util.isLegacyVersion()) {
                            SpawnEggLegacy egg = new SpawnEggLegacy(option);
                            type = egg.getSpawnedType();
                        } else {
                            SpawnEggNew egg = new SpawnEggNew(option);
                            type = egg.getSpawnedType();
                        }


                        if(this.spawner.getSpawnedType().equals(type)){
                            p.sendMessage("That seems pretty redundant...");
                            this.exit();
                            return;
                        }

                        ConfigType level = new ConfigType();
                        int levelreq = level.getLevelReq(type);
                        if (spawner.getLevel() < levelreq){
                            p.sendMessage("Spawner is not a high enough level.");
                            this.exit();
                            return;
                        }
                        Economy econ = this.main.getEconomy();
                        ConfigType name = new ConfigType();
                        if (!econ.has(p, name.getTypeCost(type))) {
                            p.sendMessage("Not enough money!");
                            this.exit();
                            return;
                        }

                        EconomyResponse res = econ.withdrawPlayer(p, name.getTypeCost(type));
                        if (res.transactionSuccess()) {
                            SpawnerTypeMenuEvent event = new SpawnerTypeMenuEvent(this.spawner, this.spawner.getSpawnedType(), type, p);
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            if (event.isCancelled()) {
                                return;
                            }
                        }

                        try {
                            this.spawner.setSpawnedType(type);
                            String debug;
                            debug = type.toString().toLowerCase();
                          //  p.sendMessage("FROSTY DEBUGGER: TYPE: " + debug + " COST: " + name.getTypeCost(type).toString() + " LEVEL REQUIREMENT: " + levelreq);
                          //  p.sendMessage("CURRENT SPAWNER: TYPE: " + this.spawner.getSpawnedType().toString() + " LEVEL: " + this.spawner.getLevel() + " ENABLED: " + this.spawner.isEnabled() + " LOCKED: " + this.spawner.isLocked());
                        } catch (IllegalArgumentException var10) {
                            p.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_TYPE_FAIL.toString());
                            Logger.debug("Failed to change spawned type of spawner '" + this.spawner.getID() + "'. Reason: unknown or unregistered entity type.", var10);
                        } catch (SetTypeFailException var11) {
                            p.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_TYPE_FAIL.toString());
                            Logger.debug("Failed to change spawned type of spawner '" + this.spawner.getID() + "'. Reason: unknown.", var11);
                        }
                        if(!spawner.getSpawnedType().equals(type)){
                            try {
                                this.spawner.setSpawnedType(type);
                                String debug;
                                debug = type.toString().toLowerCase();
                                //  p.sendMessage("FROSTY DEBUGGER: TYPE: " + debug + " COST: " + name.getTypeCost(type).toString() + " LEVEL REQUIREMENT: " + levelreq);
                                //  p.sendMessage("CURRENT SPAWNER: TYPE: " + this.spawner.getSpawnedType().toString() + " LEVEL: " + this.spawner.getLevel() + " ENABLED: " + this.spawner.isEnabled() + " LOCKED: " + this.spawner.isLocked());
                            } catch (IllegalArgumentException var10) {
                                p.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_TYPE_FAIL.toString());
                                Logger.debug("Failed to change spawned type of spawner '" + this.spawner.getID() + "'. Reason: unknown or unregistered entity type.", var10);
                            } catch (SetTypeFailException var11) {
                                p.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_TYPE_FAIL.toString());
                                Logger.debug("Failed to change spawned type of spawner '" + this.spawner.getID() + "'. Reason: unknown.", var11);
                            }
                        }

                        SoundHandler.OPTION_CHANGETYPE.playSound(this.spawner.getLocation(), 0.3F, 5.0F);
                        p.sendMessage(this.prefix + Lang.SPAWNER_CHANGE_TYPE.toString().replace("%type%", Util.toString(type)));
                        this.exit();
                        return;
                    }

                    if (option.getType() == this.main.items.blaze_rod(1).getType()) {
                        SoundHandler.OPTION_SELECT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.typeNextPage();
                    }

                    if (option.getType() == this.main.items.stick(1).getType()) {
                        SoundHandler.OPTION_SELECT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.typePreviousPage();
                    }

                    if (option.getType() == this.main.items.barrier(1).getType()) {
                        SoundHandler.OPTION_SELECT.playSound(this.spawner.getLocation(), 0.3F, 0.0F);
                        this.openMenu("MAIN");
                        this.page = 1;
                        return;
                    }
                }

            }
        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onMenuClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof MainMenuHolder || e.getInventory().getHolder() instanceof SettingsMenuHolder || e.getInventory().getHolder() instanceof TypeMenuHolder) {
            SpawnerInteractListener.players.remove(this.player.getUniqueId());
        }

    }
}
