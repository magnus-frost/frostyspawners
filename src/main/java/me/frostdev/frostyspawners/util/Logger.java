package me.frostdev.frostyspawners.util;

import java.util.logging.Level;
import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.util.config.Config;

public class Logger {
    private static boolean debugLog;

    public Logger() {
        debugLog = Config.debug.get();
    }

    public static final void info(String message) {
        Frostyspawners.PLUGIN.getLogger().log(Level.INFO, message);
    }

    public static final void error(String message) {
        Frostyspawners.PLUGIN.getLogger().log(Level.SEVERE, message);
    }

    public static final void error(String message, Throwable thrown) {
        Frostyspawners.PLUGIN.getLogger().log(Level.SEVERE, message, thrown);
    }

    public static final void debug(String message) {
        if (debugLog) {
            Frostyspawners.PLUGIN.getLogger().log(Level.INFO, "DEBUG :: " + message);
        }

    }

    public static final void debug(String message, Throwable thrown) {
        if (debugLog) {
            Frostyspawners.PLUGIN.getLogger().log(Level.SEVERE, "DEBUG ERROR ::" + message, thrown);
        }

    }
}
