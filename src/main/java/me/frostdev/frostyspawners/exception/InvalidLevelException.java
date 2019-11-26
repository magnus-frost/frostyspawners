package me.frostdev.frostyspawners.exception;

public class InvalidLevelException extends Exception {
    private static final long serialVersionUID = -9045154181564819113L;

    public InvalidLevelException(String message) {
        super(message);
    }

    public InvalidLevelException(String message, Exception cause) {
        super(message, cause);
    }
}
