package Exceptions;

public class InvalidCooldown extends RuntimeException {
    public InvalidCooldown() {}
    public InvalidCooldown(String message) {
        super(message);
    }
}
