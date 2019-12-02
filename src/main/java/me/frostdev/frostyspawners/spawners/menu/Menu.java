package me.frostdev.frostyspawners.spawners.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.Logger;
import me.frostdev.frostyspawners.util.Util;
import me.frostdev.frostyspawners.util.config.Config;
import me.frostdev.frostyspawners.util.config.ConfigType;
import me.frostdev.frostyspawners.util.items.TypeMenuEggItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
    private Frostyspawners main;
    private Inventory menu_main;
    private Inventory menu_settings;
    private Map<Integer, Inventory> menu_type;

    public Menu(Frostyspawners as) {
        this.main = as;
        this.setup_menu_main();
        this.setup_menu_settings();
        this.setup_menu_type();
    }

    public Inventory createMainMenu(Spawner spawner) {
        Inventory menu = this.menu_main;
        this.spawner_info_item(menu, spawner);
        this.upgrade_item(menu, spawner);
        this.type_menu_item(menu, spawner);
        this.show_delay_item(menu, spawner);
        return menu;
    }

    public Inventory createSettingsMenu(Spawner spawner) {
        Inventory menu = this.menu_settings;
        this.enabled_item(menu, spawner);
        this.locked_item(menu, spawner);
        return menu;
    }

    public Map<Integer, Inventory> createTypeMenu(Player player) {
        this.setup_menu_type();
        this.loadEggs(player);
        return this.menu_type;
    }

    private void setup_menu_main() {
        this.menu_main = Bukkit.createInventory(new MainMenuHolder(), 45, Config.menuHeader.color());
        this.close_item(this.menu_main);
        this.pickup_item(this.menu_main);
        this.border_item(this.menu_main, 0, DyeColor.YELLOW);
        this.border_item(this.menu_main, 2, DyeColor.YELLOW);
        this.border_item(this.menu_main, 4, DyeColor.YELLOW);
        this.border_item(this.menu_main, 6, DyeColor.YELLOW);
        this.border_item(this.menu_main, 8, DyeColor.YELLOW);
        this.border_item(this.menu_main, 18, DyeColor.YELLOW);
        this.border_item(this.menu_main, 26, DyeColor.YELLOW);
        this.border_item(this.menu_main, 36, DyeColor.YELLOW);
        this.border_item(this.menu_main, 38, DyeColor.YELLOW);
        this.border_item(this.menu_main, 40, DyeColor.YELLOW);
        this.border_item(this.menu_main, 42, DyeColor.YELLOW);
        this.border_item(this.menu_main, 44, DyeColor.YELLOW);
        this.border_item(this.menu_main, 0, DyeColor.YELLOW);
        this.border_item(this.menu_main, 1, DyeColor.BLACK);
        this.border_item(this.menu_main, 3, DyeColor.BLACK);
        this.border_item(this.menu_main, 5, DyeColor.BLACK);
        this.border_item(this.menu_main, 7, DyeColor.BLACK);
        this.border_item(this.menu_main, 9, DyeColor.BLACK);
        this.border_item(this.menu_main, 17, DyeColor.BLACK);
        this.border_item(this.menu_main, 27, DyeColor.BLACK);
        this.border_item(this.menu_main, 35, DyeColor.BLACK);
        this.border_item(this.menu_main, 37, DyeColor.BLACK);
        this.border_item(this.menu_main, 39, DyeColor.BLACK);
        this.border_item(this.menu_main, 41, DyeColor.BLACK);
        this.border_item(this.menu_main, 43, DyeColor.BLACK);
    }

    private void setup_menu_settings() {
        this.menu_settings = Bukkit.createInventory(new SettingsMenuHolder(), 45, Config.menuHeader.color());
        this.return_item(this.menu_settings, 22);
        this.border_item(this.menu_settings, 0, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 2, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 4, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 6, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 8, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 18, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 26, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 36, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 38, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 40, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 42, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 44, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 0, DyeColor.LIGHT_BLUE);
        this.border_item(this.menu_settings, 1, DyeColor.WHITE);
        this.border_item(this.menu_settings, 3, DyeColor.WHITE);
        this.border_item(this.menu_settings, 5, DyeColor.WHITE);
        this.border_item(this.menu_settings, 7, DyeColor.WHITE);
        this.border_item(this.menu_settings, 9, DyeColor.WHITE);
        this.border_item(this.menu_settings, 17, DyeColor.WHITE);
        this.border_item(this.menu_settings, 27, DyeColor.WHITE);
        this.border_item(this.menu_settings, 35, DyeColor.WHITE);
        this.border_item(this.menu_settings, 37, DyeColor.WHITE);
        this.border_item(this.menu_settings, 39, DyeColor.WHITE);
        this.border_item(this.menu_settings, 41, DyeColor.WHITE);
        this.border_item(this.menu_settings, 43, DyeColor.WHITE);
    }

    private void setup_menu_type() {
        this.menu_type = new HashMap();
    }

    private void next_page_item(Inventory menu) {
        ItemStack item = this.main.items.blaze_rod(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.menuTypeNextPageItemName.color());
        item.setItemMeta(meta);
        menu.setItem(50, item);
    }

    private void previous_page_item(Inventory menu) {
        ItemStack item = this.main.items.stick(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.menuTypePreviousPageItemName.color());
        item.setItemMeta(meta);
        menu.setItem(48, item);
    }

    private void spawner_info_item(Inventory menu, Spawner spawner) {
        ItemStack item = this.main.items.spawner(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.menuInfoItemName.color());
        List<String> lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuInfoItemLore1.get().replace("%level%", String.valueOf(spawner.getLevel()))));
        lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuInfoItemLore2.get().replace("%delay%", String.valueOf(Config.getLevel(spawner.getLevel()).getDelay() / 20))));
        if (spawner.isEnabled()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuInfoItemLore3.get().replace("%value%", "&atrue")));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuInfoItemLore3.get().replace("%value%", "&cfalse")));
        }

        if (spawner.isLocked()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuInfoItemLore4.get().replace("%value%", "&atrue")));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuInfoItemLore4.get().replace("%value%", "&cfalse")));
        }

        lore.add(Config.menuInfoItemLore5.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(22, item);
    }

    private void upgrade_item(Inventory menu, Spawner spawner) {
        ItemStack item = this.main.items.exp_bottle(1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList();
        if (spawner.getLevel() == Config.maxLevel.get()) {
            meta.setDisplayName(Config.menuUpgradeItemNameMax.color());
        } else {
            meta.setDisplayName(Config.menuUpgradeItemNameUpgrade.color().replace("%level%", String.valueOf(spawner.getLevel() + 1)));
            if (Config.upgradeWithMoney.get()) {
                if (this.main.getEconomy() != null) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuUpgradeItemLore1.get().replace("%cost%", this.main.getEconomy().format(Config.getLevel(spawner.getLevel() + 1).getCost()))));
                } else {
                    lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuUpgradeItemLore1.get().replace("%cost%", String.valueOf((int)Config.getLevel(spawner.getLevel() + 1).getCost() + " EXP Levels"))));
                    Logger.debug("Setting 'upgradeWithMoney' is set to true, yet no economy plugin is found.");
                }
            } else {
                lore.add(Config.menuUpgradeItemLore1.color().replace("%cost%", String.valueOf((int)Config.getLevel(spawner.getLevel() + 1).getCost() + " EXP Levels")));
            }

            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuUpgradeItemLore2.get().replace("%delay%", String.valueOf(Config.getLevel(spawner.getLevel() + 1).getDelay() / 20))));
            lore.add(Config.menuUpgradeItemLore3.color());
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(13, item);
    }

    private void type_menu_item(Inventory menu, Spawner spawner) {
        if (spawner.getSpawnedType().equals(EntityType.IRON_GOLEM)) {
            ItemStack item = new ItemStack(Material.IRON_HELMET);
            ItemMeta meta = item.getItemMeta();
            meta.removeAttributeModifier(Attribute.GENERIC_ARMOR);
            meta.setDisplayName("Advanced Entity: Iron Golem");
            List<String> lore = new ArrayList();
            lore.add("Iron Golem Spawner");
            meta.setLore(lore);
            item.setItemMeta(meta);
            menu.setItem(20, item);
        } else {
            ItemStack item = this.main.items.spawn_egg(spawner.getSpawnedType()).toItemStack();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Config.menuTypeMenuItemName.color());
            List<String> lore = new ArrayList();
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuTypeMenuItemLore1.get().replace("%type%", spawner.getSpawnedEntity())));
            if (Config.typeMenu.get()) {
                lore.add(Config.menuTypeMenuItemLore2.color());
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            menu.setItem(20, item);
        }
    }
   // private  void type_menu_item_advanced(Inventory menu, Spawner spawner){
   //     ItemStack item = t
   // }

    private void show_delay_item(Inventory menu, Spawner spawner) {
        ItemStack item = this.main.items.wool(1, DyeColor.WHITE);
        ItemMeta meta = item.getItemMeta();
        if (spawner.getShowDelay()) {
            item = this.main.items.wool(1, DyeColor.LIME);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuDelayItemName.get().replace("%value%", "&a&ltrue")));
        } else {
            item = this.main.items.wool(1, DyeColor.RED);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuDelayItemName.get().replace("%value%", "&c&lfalse")));
        }

        List<String> lore = new ArrayList();
        lore.add(Config.menuDelayItemLore.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(24, item);
    }

    private void close_item(Inventory menu) {
        ItemStack item = this.main.items.barrier(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.menuCloseItemName.color());
        List<String> lore = new ArrayList();
        lore.add(Config.menuCloseItemLore.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(31, item);
    }
    private void pickup_item(Inventory menu) {
        ItemStack item = this.main.items.bedrock(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED.toString() + "Pick up your spawner.");
        List<String> lore = new ArrayList();
        lore.add(Config.spawnerpickupitemlore.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(34, item);
    }

    private void border_item(Inventory menu, int location, DyeColor color) {
        ItemStack item = this.main.items.stained_glass_pane(1, color);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.menuBorderItemName.color());
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        item.setItemMeta(meta);
        menu.setItem(location, item);
    }

    private void enabled_item(Inventory menu, Spawner spawner) {
        ItemStack item = this.main.items.wool(1, DyeColor.WHITE);
        ItemMeta meta = item.getItemMeta();
        if (spawner.isEnabled()) {
            item = this.main.items.wool(1, DyeColor.LIME);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuEnabledItem.get().replace("%value%", "&a&ltrue")));
        } else {
            item = this.main.items.wool(1, DyeColor.RED);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuEnabledItem.get().replace("%value%", "&c&lfalse")));
        }

        List<String> lore = new ArrayList();
        lore.add(Config.menuEnabledLore.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(20, item);
    }

    private void locked_item(Inventory menu, Spawner spawner) {
        ItemStack item = this.main.items.wool(1, DyeColor.WHITE);
        ItemMeta meta = item.getItemMeta();
        if (spawner.isLocked()) {
            item = this.main.items.wool(1, DyeColor.LIME);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuLockItemName.get().replace("%value%", "&a&ltrue")));
        } else {
            item = this.main.items.wool(1, DyeColor.RED);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuLockItemName.get().replace("%value%", "&c&lfalse")));
        }

        List<String> lore = new ArrayList();
        lore.add(Config.menuLockItemLore.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(24, item);
    }

    private void return_item(Inventory menu, int location) {
        ItemStack item = this.main.items.barrier(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Config.menuReturnItemName.color());
        ArrayList<String> lore = new ArrayList();
        lore.add(Config.menuReturnItemLore.color());
        meta.setLore(lore);
        item.setItemMeta(meta);
        menu.setItem(location, item);
    }

    private void loadEggs(Player player) {
        int currentPage = 1;
        int currentSlot = 0;
        Iterator var5 = Util.getEggs().keySet().iterator();

        while(true) {
            TypeMenuEggItem eggItem;
            Inventory menu;
            do {
                if (!var5.hasNext()) {
                    var5 = this.menu_type.entrySet().iterator();

                    while(var5.hasNext()) {
                        Entry<Integer, Inventory> entry = (Entry)var5.next();
                        int page = (Integer)entry.getKey();
                        menu = (Inventory)entry.getValue();
                        this.return_item(menu, 49);
                        if (page == 1) {
                            this.next_page_item(menu);
                        } else if (page == this.menu_type.size()) {
                            this.previous_page_item(menu);
                        } else {
                            this.next_page_item(menu);
                            this.previous_page_item(menu);
                        }
                    }

                    return;
                }

                EntityType type = (EntityType)var5.next();
                eggItem = Util.getEgg(type);
            } while(!player.hasPermission(eggItem.getPermission()));

            menu = (Inventory)this.menu_type.get(currentPage);
            if (menu == null) {
                menu = Bukkit.createInventory(new TypeMenuHolder(), 54, Config.typeHeader.color());
                this.menu_type.put(currentPage, menu);
            }

            if (currentSlot > 44) {
                ++currentPage;
                currentSlot = 0;
                Inventory newMenu = Bukkit.createInventory(new TypeMenuHolder(), 54, Config.typeHeader.color());
                this.menu_type.put(currentPage, newMenu);
            }

            try {
                this.egg_item((Inventory)this.menu_type.get(currentPage), currentSlot, eggItem);
            } catch (NullPointerException var9) {
                continue;
            }

            ++currentSlot;
        }
    }

    private void egg_item(Inventory menu, int location, TypeMenuEggItem egg) {
        if (egg.getEntityType().equals(EntityType.IRON_GOLEM)){
            EntityType etype = egg.getEntityType();
            ConfigType name = new ConfigType();
            Double cost = name.getTypeCost(etype);
            int lvlreq = name.getLevelReq(etype);
            ItemStack golem = main.items.helmet(1);
            ItemMeta mgolem = golem.getItemMeta();
            Material igolem = golem.getType();
            golem.setType(igolem);
            mgolem.removeAttributeModifier(EquipmentSlot.HEAD);
            mgolem.removeAttributeModifier(Attribute.GENERIC_ARMOR);
            mgolem.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            List<String> lore = new ArrayList();
            mgolem.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuTypeEggItemName.get().replaceAll("%type%", egg.getDisplayName())));
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuTypeEggItemLore.get()));
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuTypeEggItemLore1.get().replace("%cost%", cost.toString())));
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.lvlreq.get().replaceAll("%lvlreq%", Integer.toString(lvlreq))));
            mgolem.setLore(lore);
            golem.setItemMeta(mgolem);
            golem.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            golem.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            golem.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            menu.setItem(location, golem);
        }else {
            EntityType etype = egg.getEntityType();
            ConfigType name = new ConfigType();
            Double cost = name.getTypeCost(etype);
            int lvlreq = name.getLevelReq(etype);
            ItemStack item = new ItemStack(egg.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.menuTypeEggItemName.get().replaceAll("%type%", egg.getDisplayName())));
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuTypeEggItemLore.get()));
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.menuTypeEggItemLore1.get().replace("%cost%", cost.toString())));
            lore.add(ChatColor.translateAlternateColorCodes('&', Config.lvlreq.get().replaceAll("%lvlreq%", Integer.toString(lvlreq))));
            meta.setLore(lore);
            item.setItemMeta(meta);
            menu.setItem(location, item);
        }
    }
}
