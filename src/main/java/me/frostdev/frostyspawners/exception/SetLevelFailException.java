package me.frostdev.frostyspawners.exception;

public class SetLevelFailException extends Exception {
    private static final long serialVersionUID = -5009402766169980814L;

    public SetLevelFailException(String message) {
        super(message);
    }

    public SetLevelFailException(String message, Exception cause) {
        super(message, cause);
    }
}
