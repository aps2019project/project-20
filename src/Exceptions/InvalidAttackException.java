package Exceptions;

public class InvalidAttackException extends RuntimeException {
    public InvalidAttackException() {
    }

    public InvalidAttackException(String message) {
        super(message);
    }
}