package me.frostdev.frostyspawners.exception;

public class SetTypeFailException extends Exception {
    private static final long serialVersionUID = 4742030221056875766L;

    public SetTypeFailException(String message) {
        super(message);
    }

    public SetTypeFailException(String message, Exception cause) {
        super(message, cause);
    }
}
