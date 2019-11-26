package me.frostdev.frostyspawners.util.config;

public class Config {
    public static ConfigBoolean debug;
    public static ConfigBoolean updater;
    public static ConfigBoolean upgradeSpawners;
    public static ConfigBoolean silkTouchMine;
    public static ConfigBoolean silkTouchProtect;
    public static ConfigBoolean maxLevelEffect;
    public static ConfigBoolean typeMenu;
    public static ConfigBoolean menuSounds;
    public static ConfigBoolean eggsEnable;
    public static ConfigInteger eggsAmount;
    public static ConfigString menuHeader;
    public static ConfigString menuBorderItemName;
    public static ConfigString menuCloseItemName;
    public static ConfigString menuCloseItemLore;
    public static ConfigString menuReturnItemName;
    public static ConfigString menuReturnItemLore;
    public static ConfigString menuInfoItemName;
    public static ConfigString menuInfoItemLore1;
    public static ConfigString menuInfoItemLore2;
    public static ConfigString menuInfoItemLore3;
    public static ConfigString menuInfoItemLore4;
    public static ConfigString menuInfoItemLore5;
    public static ConfigString menuUpgradeItemMaterial;
    public static ConfigString menuUpgradeItemNameUpgrade;
    public static ConfigString menuUpgradeItemNameMax;
    public static ConfigString menuUpgradeItemLore1;
    public static ConfigString menuUpgradeItemLore2;
    public static ConfigString menuUpgradeItemLore3;
    public static ConfigString menuTypeMenuItemName;
    public static ConfigString menuTypeMenuItemLore1;
    public static ConfigString menuTypeMenuItemLore2;
    public static ConfigString menuTypeMenuItemLore3;
    public static ConfigString menuDelayItemName;
    public static ConfigString menuDelayItemLore;
    public static ConfigString menuEnabledItem;
    public static ConfigString menuEnabledLore;
    public static ConfigString menuLockItemName;
    public static ConfigString menuLockItemLore;
    public static ConfigString typeHeader;
    public static ConfigString menuTypeEggItemName;
    public static ConfigString menuTypeEggItemLore;
    public static ConfigString menuTypeNextPageItemName;
    public static ConfigString menuTypePreviousPageItemName;
    public static ConfigInteger maxLevel;
    public static ConfigBoolean upgradeWithMoney;
    public static ConfigString menuTypeEggItemLore1;
    public static ConfigString spawnerpickupitemlore;
    public static ConfigInteger limit;
    public static ConfigString lvlreq;

    public Config() {
        debug = new ConfigBoolean("debug", false);
        updater = new ConfigBoolean("updater", false);
        upgradeSpawners = new ConfigBoolean("upgrade-spawners", true);
        silkTouchMine = new ConfigBoolean("silk-touch-mine", true);
        silkTouchProtect = new ConfigBoolean("silk-touch-protect", true);
        typeMenu = new ConfigBoolean("type-menu", true);
        maxLevelEffect = new ConfigBoolean("max-level-effect", true);
        menuSounds = new ConfigBoolean("menu-sounds", true);
        eggsEnable = new ConfigBoolean("right-click-egg", false);
        eggsAmount = new ConfigInteger("eggs-amount", 5);
        menuHeader = new ConfigString("main-menu-header", "&9&lAdvanced &f&lSpawner Menu");
        menuBorderItemName = new ConfigString("border-item-name", "&9&lAdvanced &f&lSpawners");
        menuCloseItemName = new ConfigString("close-item.name", "&c&lClose");
        menuCloseItemLore = new ConfigString("close-item.lore", "&6Close the menu.");
        menuReturnItemName = new ConfigString("return-item.name", "&c&lReturn");
        menuReturnItemLore = new ConfigString("return-item.lore", "&6Return to main menu.");
        menuInfoItemName = new ConfigString("info-item.name", "&a&lSpawner Info");
        menuInfoItemLore1 = new ConfigString("info-item.lore1", "&6Level: &b%level%&6.");
        menuInfoItemLore2 = new ConfigString("info-item.lore2", "&6Delay: &b%delay% &6seconds.");
        menuInfoItemLore3 = new ConfigString("info-item.lore3", "&6Enabled: &r%value%&6.");
        menuInfoItemLore4 = new ConfigString("info-item.lore4", "&6Locked: &r%value%&6.");
        menuInfoItemLore5 = new ConfigString("info-item.lore5", "&7Click to open spawner settings menu.");
        menuUpgradeItemNameUpgrade = new ConfigString("upgrade-item.name.upgrade", "&a&lUpgrade Spawner to lvl. &6&l%level%");
        menuUpgradeItemNameMax = new ConfigString("upgrade-item.name.max", "&a&lAt maximum level!");
        menuUpgradeItemLore1 = new ConfigString("upgrade-item.lore1", "&6Cost: &b%cost%&6.");
        menuUpgradeItemLore2 = new ConfigString("upgrade-item-lore2", "&6New delay: &b%delay% seconds&6.");
        menuUpgradeItemLore3 = new ConfigString("upgrade-item.lore3", "&7Click to upgrade.");
        menuTypeMenuItemName = new ConfigString("type-menu-item.name", "&6&lType Menu");
        menuTypeMenuItemLore1 = new ConfigString("type-menu-item.lore1", "&6Current type: &b%type%&6.");
        menuTypeMenuItemLore2 = new ConfigString("type-menu-item.lore2", "&7Click to open types menu.");
        menuTypeMenuItemLore3 = new ConfigString("type-menu-item.lore3", "&7Current Mob Cost: %cost%");
        menuDelayItemName = new ConfigString("delay-item.name", "&6&lShow delay: &r%value%&6&l.");
        menuDelayItemLore = new ConfigString("delay-item.lore", "&7Click to toggle option.");
        menuEnabledItem = new ConfigString("enabled-item.name", "&6&lEnabled: &r%value%&6&l.");
        menuEnabledLore = new ConfigString("enabled-item.lore", "&7Click to toggle option.");
        menuLockItemName = new ConfigString("lock-item.name", "&6&lLocked: &r%value%&6&l.");
        menuLockItemLore = new ConfigString("lock-item.lore", "&7Click to toggle option.");
        typeHeader = new ConfigString("type-menu-header", "&9&lSelect &f&la type");
        menuTypeEggItemName = new ConfigString("egg-item.name", "&6&l%type%");
        menuTypeEggItemLore = new ConfigString("egg-item.lore", "&7Click to change spawner type.");
        menuTypeEggItemLore1 = new ConfigString("egg-item.lore1", "&6Cost: &r%cost%");
        menuTypeNextPageItemName = new ConfigString("next-page-item-name", "&b&lNext Page");
        menuTypePreviousPageItemName = new ConfigString("previous-page-item-name", "&b&lPrevious Page");
        maxLevel = new ConfigInteger("max-level", 5);
        upgradeWithMoney = new ConfigBoolean("money-upgrades", false);
        spawnerpickupitemlore = new ConfigString("pickup-description", "&7click to pick up your spawner.");
        lvlreq = new ConfigString("egg-item.lore2", "Level Required: &6%lvlreq%");
        limit = new ConfigInteger("limit", 5);
    }

    public static final ConfigLevel getLevel(int level) {
        return new ConfigLevel(level);
    }

    public static void reload() {
        new Config();
    }
}
