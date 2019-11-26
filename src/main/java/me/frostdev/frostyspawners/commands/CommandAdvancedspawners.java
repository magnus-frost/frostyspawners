
package me.frostdev.frostyspawners.commands;

import java.util.Set;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.Lang;
import me.frostdev.frostyspawners.Permissions;
import me.frostdev.frostyspawners.api.event.SpawnerChangeLevelEvent;
import me.frostdev.frostyspawners.api.event.SpawnerChangeSettingEvent;
import me.frostdev.frostyspawners.api.event.SpawnerChangeTypeEvent;
import me.frostdev.frostyspawners.api.event.SpawnerChangeLevelEvent.Cause;
import me.frostdev.frostyspawners.api.event.SpawnerChangeSettingEvent.Setting;
import me.frostdev.frostyspawners.exception.InvalidLevelException;
import me.frostdev.frostyspawners.exception.SetTypeFailException;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.Util;
import me.frostdev.frostyspawners.util.config.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandAdvancedspawners implements CommandExecutor {
    private Frostyspawners main;
    private String prefix;
    private String noperm;

    public CommandAdvancedspawners(Frostyspawners as) {
        this.main = Frostyspawners.INSTANCE;
        this.prefix = Lang.PREFIX.toString();
        this.noperm = this.prefix + Lang.NO_PERMISSION.toString();
        this.main = as;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission((new Permissions()).command_main)) {
            sender.sendMessage(this.noperm);
            return true;
        } else if (args.length == 0) {
            sender.sendMessage(this.prefix + Lang.COMMAND_NOARGS.toString().replace("%label%", label).replace("%versionid%", this.main.getVersionID()));
            return true;
        } else if (!args[0].equals("help") && !args[0].equals("?")) {
            if (args[0].equals("save")) {
                if (!sender.hasPermission((new Permissions()).command_save)) {
                    sender.sendMessage(this.noperm);
                    return true;
                } else if (args.length > 1) {
                    sender.sendMessage(this.prefix + Lang.COMMAND_SAVE_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                    return true;
                } else {
                    try {
                        this.main.getData().saveSpawners();
                    } catch (Exception var12) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_SAVE_FAIL.toString());
                        var12.printStackTrace();
                        return true;
                    }

                    sender.sendMessage(this.prefix + Lang.COMMAND_SAVE_SUCCESS.toString());
                    return true;
                }
            } else if (args[0].equals("load")) {
                if (!sender.hasPermission((new Permissions()).command_load)) {
                    sender.sendMessage(this.noperm);
                    return true;
                } else if (args.length > 1) {
                    sender.sendMessage(this.prefix + Lang.COMMAND_LOAD_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                    return true;
                } else {
                    try {
                        this.main.getData().reloadSpawners();
                    } catch (Exception var13) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_LOAD_FAIL.toString());
                        var13.printStackTrace();
                        return true;
                    }

                    sender.sendMessage(this.prefix + Lang.COMMAND_LOAD_SUCCESS.toString());
                    return true;
                }
            } else if (args[0].equals("clean")) {
                if (!sender.hasPermission((new Permissions()).command_clean)) {
                    sender.sendMessage(this.noperm);
                    return true;
                } else if (args.length > 1) {
                    sender.sendMessage(this.prefix + Lang.COMMAND_CLEAN_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                    return true;
                } else {
                    try {
                        this.main.getData().cleanSpawners();
                    } catch (Exception var14) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_CLEAN_FAIL.toString());
                        var14.printStackTrace();
                        return true;
                    }

                    this.main.getData().getSaver().save();
                    sender.sendMessage(this.prefix + Lang.COMMAND_CLEAN_SUCCESS.toString());
                    return true;
                }
            } else if (args[0].equals("clear")) {
                if (!sender.hasPermission((new Permissions()).command_clear)) {
                    sender.sendMessage(this.noperm);
                    return true;
                } else if (args.length > 1) {
                    sender.sendMessage(this.prefix + Lang.COMMAND_CLEAR_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                    return true;
                } else {
                    try {
                        this.main.getData().clearSpawners();
                    } catch (Exception var15) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_CLEAR_FAIL.toString());
                        var15.printStackTrace();
                        return true;
                    }

                    this.main.getData().getSaver().save();
                    sender.sendMessage(this.prefix + Lang.COMMAND_CLEAR_SUCCESS.toString());
                    return true;
                }
            } else if (args[0].equals("reload")) {
                if (!sender.hasPermission((new Permissions()).command_reload)) {
                    sender.sendMessage(this.noperm);
                    return true;
                } else if (args.length > 1) {
                    sender.sendMessage(this.prefix + Lang.COMMAND_RELOAD_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                    return true;
                } else {
                    try {
                        this.main.reloadConfig();
                        this.main.loadLang();
                    } catch (Exception var16) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_RELOAD_FAIL.toString());
                        var16.printStackTrace();
                        return true;
                    }

                    sender.sendMessage(this.prefix + Lang.COMMAND_RELOAD_SUCCESS.toString());
                    return true;
                }
            } else {
                Player owner;
                EntityType type;
                if (args[0].equals("give")) {
                    if (!sender.hasPermission((new Permissions()).command_give)) {
                        sender.sendMessage(this.noperm);
                        return true;
                    }

                    if (!(sender instanceof Player)) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_CONSOLE.toString());
                        return true;
                    }

                    if (args.length == 1) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TOOFEWARGS.toString().replace("%label%", label));
                        return true;
                    }

                    Player target;
                    if (args.length == 2) {
                        if (!this.isMobType(args[1])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TYPE_INVALID.toString().replace("%type%", args[1]));
                            return true;
                        }

                        EntityType entityType = Util.fromString(args[1]);
                        ItemStack mobspawner = Spawner.getAsItemStack(entityType);
                        target = (Player)sender;
                        target.getInventory().addItem(new ItemStack[]{mobspawner});
                        sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_SELF.toString().replace("%item%", "1x " + mobspawner.getItemMeta().getDisplayName()).replace("%level%", String.valueOf(0)));
                        return true;
                    }

                    int level;
                    if (args.length == 3) {
                        if (!this.isInt(args[2])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_NOINTEGER.toString().replace("%arg%", args[2]));
                            return false;
                        }

                        if (!this.isMobType(args[1])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TYPE_INVALID.toString().replace("%type%", args[1]));
                            return true;
                        }

                        if (!this.isInt(args[2])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_LEVEL_NOINTEGER.toString().replace("%arg%", args[2]));
                            return false;
                        }

                        level = Integer.parseInt(args[2]);
                        if (level >= 0 && level <= Config.maxLevel.get()) {
                            EntityType entityType = Util.fromString(args[1]);
                            ItemStack mobspawner = Spawner.getAsItemStack(entityType, level);
                            owner = (Player)sender;
                            owner.getInventory().addItem(new ItemStack[]{mobspawner});
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_SELF.toString().replace("%item%", "1x " + mobspawner.getItemMeta().getDisplayName()).replace("%level%", String.valueOf(level)));
                            return true;
                        }

                        sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_LEVEL_INVALID.toString().replace("%maxlvl%", String.valueOf(Config.maxLevel.get())));
                        return true;
                    }

                    int amount;
                    if (args.length == 4) {
                        if (!this.isInt(args[2])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_NOINTEGER.toString().replace("%arg%", args[2]));
                            return false;
                        }

                        if (!this.isMobType(args[1])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TYPE_INVALID.toString().replace("%type%", args[1]));
                            return true;
                        }

                        if (!this.isInt(args[2])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_LEVEL_NOINTEGER.toString().replace("%arg%", args[2]));
                            return false;
                        }

                        level = Integer.parseInt(args[2]);
                        if (level >= 0 && level <= Config.maxLevel.get()) {
                            if (!this.isInt(args[3])) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_NOINTEGER.toString().replace("%arg%", args[3]));
                                return false;
                            }

                            amount = Integer.parseInt(args[3]);
                            if (amount < 1) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_BELOW_1.toString());
                                return false;
                            }

                            type = Util.fromString(args[1]);
                            ItemStack mobspawner = Spawner.getAsItemStack(type, level, amount);
                            Player p = (Player)sender;
                            p.getInventory().addItem(new ItemStack[]{mobspawner});
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_SELF.toString().replace("%item%", amount + "x " + mobspawner.getItemMeta().getDisplayName()).replace("%level%", String.valueOf(level)));
                            return true;
                        }

                        sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_LEVEL_INVALID.toString().replace("%maxlvl%", String.valueOf(Config.maxLevel.get())));
                        return true;
                    }

                    if (args.length == 5) {
                        if (!this.isInt(args[2])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_NOINTEGER.toString().replace("%arg%", args[2]));
                            return false;
                        }

                        if (!this.isMobType(args[1])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TYPE_INVALID.toString().replace("%type%", args[1]));
                            return true;
                        }

                        if (!this.isInt(args[2])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_LEVEL_NOINTEGER.toString().replace("%arg%", args[2]));
                            return false;
                        }

                        level = Integer.parseInt(args[2]);
                        if (level >= 0 && level <= Config.maxLevel.get()) {
                            if (!this.isInt(args[3])) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_NOINTEGER.toString().replace("%arg%", args[3]));
                                return false;
                            }

                            amount = Integer.parseInt(args[3]);
                            if (amount < 1) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_AMOUNT_BELOW_1.toString());
                                return false;
                            }

                            if (!this.isOnline(args[4])) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TARGET_NULL.toString().replaceAll("%target%", args[4]));
                                return false;
                            }

                            target = Bukkit.getServer().getPlayerExact(args[4]);
                            EntityType entityType = Util.fromString(args[1]);
                            ItemStack mobspawner = Spawner.getAsItemStack(entityType, level, amount);
                            target.getInventory().addItem(new ItemStack[]{mobspawner});
                            if (target == (Player)sender) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_SELF.toString().replace("%item%", amount + "x " + mobspawner.getItemMeta().getDisplayName()).replace("%level%", String.valueOf(level)));
                            } else {
                                sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_OTHER.toString().replace("%item%", amount + "x " + mobspawner.getItemMeta().getDisplayName()).replace("%level%", String.valueOf(level)));
                            }

                            return true;
                        }

                        sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_LEVEL_INVALID.toString().replace("%maxlvl%", String.valueOf(Config.maxLevel.get())));
                        return true;
                    }
                }

                Player p;
                Block target;
                Spawner spawner;
                String type1;
                if (args[0].equals("id")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                        return true;
                    } else {
                        p = (Player)sender;
                        if (!sender.hasPermission((new Permissions()).command_id)) {
                            sender.sendMessage(this.noperm);
                            return true;
                        } else if (args.length > 1) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_INFO_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else {
                            target = p.getTargetBlock((Set)null, 10);
                            if (target == null) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_INFO_TARGET_NULL.toString());
                                return true;
                            } else if (target.getType() != this.main.items.spawner(1).getType()) {
                                type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                                sender.sendMessage(this.prefix + Lang.COMMAND_INFO_TARGET_INVALID.toString().replace("%entitytype%", type1));
                                return true;
                            } else {
                                spawner = new Spawner(target);
                                sender.sendMessage(this.prefix + Lang.COMMAND_INFO_ID.toString().replace("%id%", spawner.getID()));
                                return true;
                            }
                        }
                    }
                } else if (args[0].equals("setlevel")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                        return true;
                    } else {
                        p = (Player)sender;
                        if (!sender.hasPermission((new Permissions()).command_level)) {
                            sender.sendMessage(this.noperm);
                            return true;
                        } else if (args.length < 2) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_TOOFEWARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else if (args.length > 2) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else {
                            target = p.getTargetBlock((Set)null, 10);
                            if (target == null) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_NULL.toString());
                                return true;
                            } else if (target.getType() != this.main.items.spawner(1).getType()) {
                                type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                                sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_INVALID.toString().replace("%type%", type1));
                                return true;
                            } else if (!StringUtils.isNumericSpace(args[1])) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_NOINTEGER.toString().replace("%string%", args[1]));
                                return true;
                            } else {
                                int level = Integer.valueOf(args[1]);
                                String id = target.getWorld().getName() + "@" + target.getX() + ";" + target.getY() + ";" + target.getZ();
                                if (this.main.getData().getInt("spawners." + id + ".level") == level) {
                                    sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_SAME.toString().replace("%level%", String.valueOf(level)));
                                    return true;
                                } else {
                                    Spawner sp = this.main.getData().getSpawner(target);
                                    SpawnerChangeLevelEvent levelEvent = new SpawnerChangeLevelEvent(sp, sp.getLevel(), level, Cause.COMMAND);
                                    Bukkit.getServer().getPluginManager().callEvent(levelEvent);
                                    if (levelEvent.isCancelled()) {
                                        return false;
                                    } else {
                                        try {
                                            sp.setLevel(level);
                                        } catch (InvalidLevelException var17) {
                                            sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_INVALID.toString().replace("%maxlevel%", String.valueOf(Config.maxLevel.get())));
                                            return true;
                                        } catch (Exception var18) {
                                            sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_FAIL.toString());
                                            var18.printStackTrace();
                                            return true;
                                        }

                                        this.main.getData().getSaver().save();
                                        this.main.getData().reloadSpawners();
                                        sender.sendMessage(this.prefix + Lang.COMMAND_SETLEVEL_SUCCESS.toString().replace("%id%", id).replace("%level%", args[1]));
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else if (args[0].equals("type")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                        return true;
                    } else if (!sender.hasPermission((new Permissions()).command_type)) {
                        sender.sendMessage(this.noperm);
                        return true;
                    } else if (args.length == 1) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_NULL.toString());
                        return true;
                    } else if (args.length > 2) {
                        sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_TOOMANYARGS.toString());
                        return true;
                    } else {
                        p = (Player)sender;
                        target = p.getTargetBlock((Set)null, 10);
                        if (target == null) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_NULL.toString());
                            return true;
                        } else if (target.getType() != this.main.items.spawner(1).getType()) {
                            type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                            sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_TARGET_INVALID.toString().replace("%type%", type1));
                            return true;
                        } else if (!this.isMobType(args[1])) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_GIVE_TYPE_INVALID.toString().replace("%type%", args[1]));
                            return true;
                        } else {
                            type = Util.fromString(args[1]);
                            Spawner sp = this.main.getData().getSpawner(target);
                            SpawnerChangeTypeEvent typeEvent = new SpawnerChangeTypeEvent(sp, sp.getSpawnedType(), type, me.frostdev.frostyspawners.api.event.SpawnerChangeTypeEvent.Cause.COMMAND);
                            Bukkit.getServer().getPluginManager().callEvent(typeEvent);
                            if (typeEvent.isCancelled()) {
                                return false;
                            } else {
                                try {
                                    sp.setSpawnedType(type);
                                } catch (IllegalArgumentException var19) {
                                    Logger.error(args[1] + "' is not a valid entity type.", var19);
                                    return true;
                                } catch (SetTypeFailException var20) {
                                    Logger.error("Failed to set entity type to '" + args[1] + "' for sp '" + sp.getID() + "'.", var20);
                                    return true;
                                }

                                String name = Util.toString(type);
                                sp.update();
                                p.sendMessage(this.prefix + Lang.COMMAND_TYPE_SUCCESS.toString().replace("%type1%", name));
                                return true;
                            }
                        }
                    }
                } else {
                    boolean setting;
                    boolean value;
                    if (args[0].equals("enable")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                            return true;
                        } else if (!sender.hasPermission((new Permissions()).command_enable)) {
                            sender.sendMessage(this.noperm);
                            return true;
                        } else if (args.length > 2) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_ENABLE_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else {
                            p = (Player)sender;
                            target = p.getTargetBlock((Set)null, 10);
                            if (target == null) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_NULL.toString());
                                return true;
                            } else if (target.getType() != this.main.items.spawner(1).getType()) {
                                type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                                sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_TARGET_INVALID.toString().replace("%type%", type1));
                                return true;
                            } else {
                                spawner = new Spawner(target);
                                if (args.length == 1) {
                                    SpawnerChangeSettingEvent settingEvent;
                                    if (spawner.isEnabled()) {
                                        settingEvent = new SpawnerChangeSettingEvent(spawner, true, false, Setting.ENABLED);
                                        Bukkit.getServer().getPluginManager().callEvent(settingEvent);
                                        if (settingEvent.isCancelled()) {
                                            return false;
                                        }

                                        spawner.setEnabled(false);
                                        p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_DISABLE.toString());
                                    } else {
                                        settingEvent = new SpawnerChangeSettingEvent(spawner, false, true, Setting.ENABLED);
                                        Bukkit.getServer().getPluginManager().callEvent(settingEvent);
                                        if (settingEvent.isCancelled()) {
                                            return false;
                                        }

                                        spawner.setEnabled(true);
                                        p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_ENABLE.toString());
                                    }

                                    return true;
                                } else {
                                    value = false;

                                    try {
                                        value = Boolean.valueOf(args[1]);
                                    } catch (IllegalArgumentException var21) {
                                        p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_INVALID_VALUE.toString());
                                        return true;
                                    }

                                    setting = this.main.getData().getBoolean("spawners." + spawner.getID() + ".enabled");
                                    if (value == setting) {
                                        if (value) {
                                            p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_ALREADY_ENABLED.toString());
                                        } else {
                                            p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_ALREADY_DISABLED.toString());
                                        }

                                        return true;
                                    } else {
                                        SpawnerChangeSettingEvent settingEvent = new SpawnerChangeSettingEvent(spawner, spawner.isEnabled(), value, Setting.ENABLED);
                                        Bukkit.getServer().getPluginManager().callEvent(settingEvent);
                                        if (settingEvent.isCancelled()) {
                                            return false;
                                        } else {
                                            spawner.setEnabled(value);
                                            if (value) {
                                                p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_ENABLE.toString());
                                            } else {
                                                p.sendMessage(this.prefix + Lang.COMMAND_ENABLE_DISABLE.toString());
                                            }

                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (args[0].equals("lock")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                            return true;
                        } else if (!sender.hasPermission((new Permissions()).command_lock)) {
                            sender.sendMessage(this.noperm);
                            return true;
                        } else if (args.length > 2) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_LOCK_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else {
                            p = (Player)sender;
                            target = p.getTargetBlock((Set)null, 10);
                            if (target == null) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_NULL.toString());
                                return true;
                            } else if (target.getType() != this.main.items.spawner(1).getType()) {
                                type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                                sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_TARGET_INVALID.toString().replace("%type%", type1));
                                return true;
                            } else {
                                spawner = new Spawner(target);
                                if (args.length == 1) {
                                    if (spawner.isLocked()) {
                                        spawner.setLocked(false);
                                        p.sendMessage(this.prefix + Lang.COMMAND_LOCK_DISABLE.toString());
                                    } else {
                                        spawner.setLocked(true);
                                        p.sendMessage(this.prefix + Lang.COMMAND_LOCK_ENABLE.toString());
                                    }

                                    return true;
                                } else {
                                    value = false;

                                    try {
                                        value = Boolean.valueOf(args[1]);
                                    } catch (IllegalArgumentException var22) {
                                        p.sendMessage(this.prefix + Lang.COMMAND_LOCK_INVALID_VALUE.toString());
                                        return true;
                                    }

                                    setting = this.main.getData().getBoolean("spawners." + spawner.getID() + ".lock");
                                    if (value == setting) {
                                        if (value) {
                                            p.sendMessage(this.prefix + Lang.COMMAND_LOCK_ALREADY_ENABLED.toString());
                                        } else {
                                            p.sendMessage(this.prefix + Lang.COMMAND_LOCK_ALREADY_DISABLED.toString());
                                        }

                                        return true;
                                    } else {
                                        spawner.setLocked(value);
                                        if (value) {
                                            p.sendMessage(this.prefix + Lang.COMMAND_LOCK_ENABLE.toString());
                                        } else {
                                            p.sendMessage(this.prefix + Lang.COMMAND_LOCK_DISABLE.toString());
                                        }

                                        return true;
                                    }
                                }
                            }
                        }
                    } else if (args[0].equals("showdelay")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                            return true;
                        } else if (!sender.hasPermission((new Permissions()).command_showdelay)) {
                            sender.sendMessage(this.noperm);
                            return true;
                        } else if (args.length > 2) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_LOCK_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else {
                            p = (Player)sender;
                            target = p.getTargetBlock((Set)null, 10);
                            if (target == null) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_NULL.toString());
                                return true;
                            } else if (target.getType() != this.main.items.spawner(1).getType()) {
                                type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                                sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_TARGET_INVALID.toString().replace("%type%", type1));
                                return true;
                            } else {
                                spawner = new Spawner(target);
                                if (args.length == 1) {
                                    if (spawner.getShowDelay()) {
                                        spawner.setShowDelay(false);
                                    } else {
                                        spawner.setShowDelay(true);
                                    }
                                }

                                value = false;

                                try {
                                    value = Boolean.valueOf(args[1]);
                                } catch (IllegalArgumentException var23) {
                                    return true;
                                }

                                spawner.setShowDelay(value);
                                p.sendMessage(String.valueOf(value));
                                return true;
                            }
                        }
                    } else if (args[0].equals("owner")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_PLAYER_ONLY.toString());
                            return true;
                        } else if (!sender.hasPermission((new Permissions()).command_owner)) {
                            sender.sendMessage(this.noperm);
                            return true;
                        } else if (args.length > 2) {
                            sender.sendMessage(this.prefix + Lang.COMMAND_OWNER_TOOMANYARGS.toString().replace("%label%", label).replace("%length%", String.valueOf(args.length)));
                            return true;
                        } else {
                            p = (Player)sender;
                            target = p.getTargetBlock((Set)null, 10);
                            if (target == null) {
                                sender.sendMessage(this.prefix + Lang.COMMAND_TARGET_NULL.toString());
                                return true;
                            } else if (target.getType() != this.main.items.spawner(1).getType()) {
                                type1 = String.valueOf(target.getType()).replace("_", " ").toLowerCase();
                                sender.sendMessage(this.prefix + Lang.COMMAND_TYPE_TARGET_INVALID.toString().replace("%type%", type1));
                                return true;
                            } else {
                                spawner = new Spawner(target);
                                if (args.length == 1) {
                                    p.sendMessage(this.prefix + Lang.COMMAND_OWNER_SHOWOWNER.toString().replace("%target%", spawner.getOwner().getName()));
                                    return true;
                                } else {
                                    owner = null;

                                    try {
                                        owner = this.main.getServer().getPlayerExact(args[1]);
                                    } catch (NullPointerException var24) {
                                        p.sendMessage(this.prefix + Lang.COMMAND_OWNER_SETOWNER_INVALID.toString().replace("%target%", args[1]));
                                        return true;
                                    }

                                    spawner.setOwner(owner);
                                    p.sendMessage(this.prefix + Lang.COMMAND_OWNER_SETOWNER_SUCCESS.toString().replace("%target%", spawner.getOwner().getName()));
                                    return true;
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(this.prefix + Lang.COMMAND_UNKNOWN_SUBCOMMAND.toString().replace("%label%", label).replace("%args%", args[0]));
                        return false;
                    }
                }
            }
        } else {
            sender.sendMessage(Lang.COMMAND_HELP_HEADER.toString());
            sender.sendMessage(Lang.COMMAND_HELP_NOTE.toString());
            sender.sendMessage(Lang.COMMAND_HELP_SAVE_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_LOAD_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_RELOAD_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_CLEAN_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_CLEAR_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_GIVE_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_ID_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_SETLEVEL_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_TYPE_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_ENABLE_DESC.toString().replace("%label%", label));
            sender.sendMessage(Lang.COMMAND_HELP_LOCK_DESC.toString().replace("%label%", label));
            return true;
        }
    }

    private boolean isOnline(String s) {
        Player target = Bukkit.getServer().getPlayerExact(s);
        return target != null;
    }

    private boolean isMobType(String s) {
        EntityType type = Util.fromString(s);
        return type != null;
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }
}
