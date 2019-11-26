package me.frostdev.frostyspawners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    PREFIX("prefix", "&9[FrostySpawners]"),
    NO_PERMISSION("no-permission", "&cYou have no permission to preform this action."),
    COMMAND_UNKNOWN_SUBCOMMAND("command-unknown-subcommand", "&cUnknown subcommand '%args%'. Use &6/%label% help &cor &6/%label% ? &cfor a list of commands."),
    COMMAND_NOARGS("command-no-args", "&fFrostySpawners plugin version %versionid% made by MagnusFrost. Type &7/%label% help or &7/%label% ? &ffor a list of commands."),
    COMMAND_TOO_MANY_ARGUMENTS("command-too-many-arguments", "&cToo many arguments. Usage: &6/%label% [subcommand]&c."),
    COMMAND_PLAYER_ONLY("command-player-only", "&cThis command can only be executed by players."),
    COMMAND_TARGET_INVALID("command-target-invalid", "&cNo spawner detected. Found &6%type%&c instead."),
    COMMAND_TARGET_NULL("command-target-null", "&cNo block detected. Look at a spawner."),
    COMMAND_HELP_TOOMANYARGS("command-help-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% help&c."),
    COMMAND_HELP_HEADER("command-help-header", "&9&m-->&r &fFrostySpawners Help &9&m<--"),
    COMMAND_HELP_NOTE("command-help-note", "&8NOTE: [] = required, <> = optional."),
    COMMAND_HELP_SAVE_DESC("command-help-save-desc", "&7/%label% save - &fSave spawners to file."),
    COMMAND_HELP_LOAD_DESC("command-help-load-desc", "&7/%label% load - &fLoad spawners from file."),
    COMMAND_HELP_RELOAD_DESC("command-help-reload-desc", "&7/%label% reload - &fReload plugin configuration file."),
    COMMAND_HELP_CLEAN_DESC("command-help-clean-desc", "&7/%label% clean - &fRemove invalid spawners from the save file."),
    COMMAND_HELP_CLEAR_DESC("command-help-clear-desc", "&7/%label% clear - &fRemove all spawners from save file."),
    COMMAND_HELP_GIVE_DESC("command-help-give-desc", "&7/%label% give [mobtype] <level> <amount> <player> - &fGive a player an amount of spawners of the specified type and level."),
    COMMAND_HELP_ID_DESC("command-help-id-desc", "&7/%label% id - &fShows the unique ID of the spawner you are looking at."),
    COMMAND_HELP_SETLEVEL_DESC("command-help-setlevel-desc", "&7/%label% setlevel [level] - &fSet the level of the spawner you are looking at."),
    COMMAND_HELP_TYPE_DESC("command-help-type-desc", "&7/%label% type [type] - &fSet the type of the spawner you are looking at."),
    COMMAND_HELP_ENABLE_DESC("command-help-enabled-desc", "&7/%label% enable <true/false>|toggle - &fEnable or disable the spawner you are looking at."),
    COMMAND_HELP_LOCK_DESC("command-help-lock-desc", "&7/%label% lock <true/false>|toggle - &fLock or unlock the spawner you are looking at."),
    COMMAND_SAVE_TOOMANYARGS("command-save-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% save&c."),
    COMMAND_SAVE_SUCCESS("command-save-success", "&fSaved spawners to file!"),
    COMMAND_SAVE_FAIL("command-save-fail", "&cFailed to save spawners to file. See console for error report."),
    COMMAND_LOAD_TOOMANYARGS("command-load-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% load&c."),
    COMMAND_LOAD_SUCCESS("command-load-success", "&fLoaded spawners from file!"),
    COMMAND_LOAD_FAIL("command-load-fail", "&cFailed to load spawners from file. See console for error report."),
    COMMAND_RELOAD_TOOMANYARGS("command-reload-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% reload&c."),
    COMMAND_RELOAD_SUCCESS("command-reload-success", "&fSuccessfuly reloaded plugin!"),
    COMMAND_RELOAD_FAIL("command-reload-fail", "&cFailed to reload plugin. See console for error report."),
    COMMAND_CLEAN_TOOMANYARGS("command-clean-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% clean&c."),
    COMMAND_CLEAN_SUCCESS("command-clean-success", "&fCleaned up save file."),
    COMMAND_CLEAN_FAIL("command-clean-fail", "&cFailed to clean up save file."),
    COMMAND_CLEAR_TOOMANYARGS("command-clear-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% clear&c."),
    COMMAND_CLEAR_SUCCESS("command-clear-success", "&fCleared save file."),
    COMMAND_CLEAR_FAIL("command-clear-fail", "&cFailed to clear save file."),
    COMMAND_INFO_TOOMANYARGS("command-info-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% info&c."),
    COMMAND_INFO_TARGET_NULL("command-info-target-null", "&cNo target block found. Look at the spawner you want information from."),
    COMMAND_INFO_TARGET_INVALID("command-info-target-invalid", "&cNo spawner detected. Found &6%type%&c instead."),
    COMMAND_INFO_TARGET_NOTSET("command-info-target-notset", "&cTarget spawner has no data set in spawners.yml, therefore data could not be retrieved."),
    COMMAND_INFO_HEADER("command-info-header", "&9&m-->&r &fFrosty Spawners &7-&f Spawner Info &9&m<--"),
    COMMAND_INFO_ID("command-info-id", "&fSpawner ID: &6%id%&f."),
    COMMAND_INFO_LEVEL("command-info-level", "&fLevel: &6%level%&f."),
    COMMAND_INFO_DELAY("command-info-delay", "&fDelay: &6%delay%&f."),
    COMMAND_GIVE_TOOFEWARGS("command-give-toofewargs", "&cToo few arguments. Usage: &6/%label% give [mobtype] <level> <amount> <player>&c."),
    COMMAND_GIVE_TOOMANYARGS("command-give-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% give [type] <amount> <player>&c."),
    COMMAND_GIVE_CONSOLE("command-give-console", "&cUsage: Usage: &6/%label% give [type] [level] [amount] [player]&c."),
    COMMAND_GIVE_TYPE_INVALID("command-give-type-invalid", "&cEntity type '%type%' is invalid."),
    COMMAND_GIVE_TARGET_NULL("command-give-target-null", "&cPlayer &6%target% &cnot found."),
    COMMAND_GIVE_LEVEL_NOINTEGER("command-give-level-integer", "&cLevel has to be a number, but got '%arg%' instead."),
    COMMAND_GIVE_LEVEL_INVALID("command-give-level-invalid", "&cLevel can not be lower than 0, and not higher than %maxlvl%."),
    COMMAND_GIVE_AMOUNT_NOINTEGER("command-give-amount-nointeger", "&cAmount has to be a number, but got '%arg%' instead."),
    COMMAND_GIVE_AMOUNT_BELOW_1("command-give-amount-below-1", "&cAmount has to be higher than 0."),
    COMMAND_GIVE_SELF("command-give-self", "&fGiven yourself &r%item%&f level %level%."),
    COMMAND_GIVE_OTHER("command-give-other", "&fGiven &6%target% &r%item%&f level %level%."),
    COMMAND_SETLEVEL_TOOMANYARGS("command-setlevel-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% level [level]&c."),
    COMMAND_SETLEVEL_TOOFEWARGS("command-setlevel-toofewargs", "&cToo few arguments (%length%). Usage: &6/%label% level [level]&c."),
    COMMAND_SETLEVEL_NOINTEGER("command-setlevel-nointeger", "&cNumber expected, got '&6%string%&c'."),
    COMMAND_SETLEVEL_INVALID("command-setlevel-invalid", "&cLevel can not be lower than 0, and not higher than %maxlevel%."),
    COMMAND_SETLEVEL_SAME("command-setlevel-same", "&cThis spawner is already level &6%level%&c."),
    COMMAND_SETLEVEL_SUCCESS("command-setlevel-success", "&fLevel of spawner with id '&6%id%&f' has been set to &6%level%&f."),
    COMMAND_SETLEVEL_FAIL("command-setlevel-fail", "&cFailed to set level of spawner with id '&6%id%&c'. See console for error report."),
    COMMAND_TYPE_TOOMANYARGS("command-type-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% type [type]&c."),
    COMMAND_TYPE_NULL("command-type-null", "&cPlease define a type."),
    COMMAND_TYPE_INVALID("command-type-invalid", "&cType &6%type%&c is not a valid type."),
    COMMAND_TYPE_TARGET_INVALID("command-type-target-invalid", "&cNo spawner detected. Found &6%type%&c instead."),
    COMMAND_TYPE_SUCCESS("command-type-success", "&fChanged spawner type to &6%type%&f."),
    COMMAND_ENABLE_TOOMANYARGS("command-enable-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% enable [true/false|toggle]&c."),
    COMMAND_ENABLE_INVALID_VALUE("command-enable-invalid-value", "&fValue has to be either 'false' or 'true'."),
    COMMAND_ENABLE_ALREADY_ENABLED("command-enable-already-enabled", "&fThis spawner is already enabled."),
    COMMAND_ENABLE_ALREADY_DISABLED("command-enable-already-disabled", "&fThis spawner is already disabled."),
    COMMAND_ENABLE_ENABLE("command-enable-enable", "&fThe spawner has been enabled."),
    COMMAND_ENABLE_DISABLE("command-enable-disable", "&fThe spawner has been disabled."),
    COMMAND_LOCK_TOOMANYARGS("command-lock-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% lock [true/false|toggle]&c."),
    COMMAND_LOCK_INVALID_VALUE("command-lock-invalid-value", "&fValue has to be either 'false' or 'true'."),
    COMMAND_LOCK_ALREADY_ENABLED("command-lock-already-enabled", "&fThis spawner is already locked."),
    COMMAND_LOCK_ALREADY_DISABLED("command-lock-already-disabled", "&fThis spawner isn't locked."),
    COMMAND_LOCK_ENABLE("command-lock-enable", "&fThe spawner has been locked."),
    COMMAND_LOCK_DISABLE("command-lock-disable", "&fThe spawner has been unlocked."),
    COMMAND_OWNER_TOOMANYARGS("command-owner-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label% owner <owner>&c."),
    COMMAND_OWNER_SHOWOWNER("command-owner-showowner", "&fThis spawner's owner is &6%target%&f."),
    COMMAND_OWNER_SETOWNER_SUCCESS("command-owner-setowner-success", "&fThe spawner's owner is now &6%target%&f."),
    COMMAND_OWNER_SETOWNER_INVALID("command-owner-setowner-invalid", "&cPlayer &6%target% &cnot found."),
    GUI_UPGRADE_NO_PERMISSION("gui-upgrade-no-permission", "&cYou have no permission to upgrade a spawner to level &6%level%&c."),
    GUI_UPGRADE_ECO_NOT_ENOUGH("gui-upgrade-eco-not-enough", "&fYou need at least %cost% to upgrade the spawner to level &6%level%&f. Current balance: %balance%."),
    GUI_UPGRADE_EXP_NOT_ENOUGH("gui-upgrade-exp-not-enough", "&fYou need at least EXP level &b%explevel%&f to upgrade the spawner to level &6%level%&f."),
    GUI_UPGRADE_MAXLEVEL("gui-upgrade-maxlevel", "&fThis spawner is already at maximum level."),
    GUI_UPGRADE_MAXEDOUT("gui-upgrade-maxedout", "&6Congratulations&f! Your spawner has reached max level!"),
    GUI_UPGRADE_DISABLED("gui-upgrade-disabled", "&fUpgrading spawners is disabled."),
    GUI_UPGRADE_FAIL("gui-upgrade-exp-fail", "&cFailed to upgrade spawner."),
    GUI_UPGRADE_ECON_FAIL_NULL("gui-upgrade-econ-fail-null", "&cFailed to upgrade spawner. Reason: no economy plugin found."),
    GUI_UPGRADE_SUCCESS("gui-upgrade-success", "&fSpawner upgraded to level &6%level%&f!"),
    GUI_SHOWDELAY_NOHOLO("gui-showdelay", "&fShow delay is not enabled."),
    GUI_TOGGLE_NOPERM("gui-toggle-noperm", "&cNo permission permission to toggle this option."),
    GUI_TYPEMENU_NOPERM("gui-typemenu-noperm", "&cNo permission to open the type menu."),
    SPAWNER_LOCKED("spawner-locked", "&cThis spawner is locked by player &6%owner%&c."),
    SPAWNER_BREAK_DESTROY("spawner-break-destroy", "&6%type% &fSpawner level %level% broken."),
    SPAWNER_BREAK_MINE("spawner-break-mine", "&6%type% &fSpawner level %level% mined."),
    SPAWNER_BREAK_NOSILKTOUCH("spawner-break-nosilktouch", "&fNotice: breaking this spawner without a pickaxe enchanted with Silk Touch will cause the spawner to disappear. Break the spawner again to destroy it regardless."),
    SPAWNER_CHANGE_EGG_DISABLED("spawner-change-egg-disabled", "&fThis feature is disabled."),
    SPAWNER_CHANGE_EGG_NO_PERMISSION("spawner-change-egg-no-permission", "&cNo permission to change a spawner's type to &6%type%&c."),
    SPAWNER_CHANGE_EGG_NOT_ENOUGH_EGGS("spawner-change-egg-not-enough-eggs", "&cYou need at least &6%amount%&c eggs to change a spawner's type."),
    SPAWNER_CHANGE_TYPE("spawner-change-type", "&fChanged spawner's type to &6%type%&f."),
    SPAWNER_CHANGE_TYPE_FAIL("spawner-change-type-fail", "&cFailed to change spawned type. See console debug logging for more details (enable debug logging in config if not yet enabled)."),
    SPAWNER_CHANGE_TYPE_NULL("spawner-change-type-null", "&fYou have no access to any types."),
    SPAWNER_CHANGE_TYPE_NOPERM("spawner-change-type-noperm", "&cNo permission to set the type to &6%type%&c."),
    SPAWNER_ANVIL_CHANGETYPE("spawner-anvil-changetype", "&cChanging the name of a spawner is not allowed.");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    private Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    public String toString() {
        return this == PREFIX ? ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, this.def)) + " " : ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, this.def));
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }
}
